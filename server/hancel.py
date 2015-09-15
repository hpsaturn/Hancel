import redis
import json
import uuid
import time
import hashlib
import random
import mechanize

from flask import Flask, session, request, render_template
import smtplib
from email.mime.text import MIMEText


app = Flask(__name__)
app.secret_key = 'F12Zr47j\3yX R~X@H!jmM]Lwf/,?KT'

r = redis.StrictRedis(host='localhost', port=6379, db=0)


def contains(x, y):
    for z in y:
        if z not in x:
            return False
    return True

def linphone_sip_register(account):
        request = mechanize.Request('http://www.linphone.org/free-sip-service.html')
        response = mechanize.urlopen(request)
        forms = mechanize.ParseResponse(response, backwards_compat=False)
        response.close()

        form = forms[0]

        form['desired-login'] = account['user'].lower()
        form['password2'] = str(account['passwd'])[0:10]
        form['confirm'] = str(account['passwd'])[0:10]
        form['email'] = account['email'].lower()
        form['firstname'] = ''
        form['name'] =''

        request2 = form.click() # mechanize.Request object
        try:
           response2 = mechanize.urlopen(request2)
        except mechanize.HTTPError, response2:
           pass

        r = response2.read()

        response2.close()

        if "A confirmation link has been sent to your email address" in r:
                res="submitted"
        elif "This username already exists." in r:
                res="duplicated"
        else:
                res="undefined error"

	return res


def register(args):
    if not contains(args, ['usuario', 'password', 'email']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    user = args['usuario']
    password = args['password']

    print(" register : %s " % (user))

    sip_account = {'user':user, 'passwd': password, 'email':args['email']} 
    
    res = linphone_sip_register(sip_account)

    if res is "submitted":
        description = {"usr-id":str(r.incr('sesion-seq'))}
        return {'resultado': 'ok','descripcion': description}
    else:
        return {'resultado': 'error',
               'descripcion': {'msg': res}}


def login(args):
#    if not contains(args, ['strUsr', 'strPass']):
#        return {'resultado': 'error',
#                'descripcion': 'argumentos insuficientes'}

    description ={"usr-id":str(r.incr('sesion-seq'))}

    return {'resultado': 'ok',
            'descripcion': description}


def tracking(args):
    if not contains(args, ['androidId', 'idDevice', 'idUsuario', 'latitude', 'longitude', 'bateria']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    trackinfo = {'time': str(time.time()), 'lat': str(args['latitude']), 'lon':str(args['longitude']) }
    print("trackinfo %s" % str(trackinfo))
    trace_id = hashlib.md5(args['idUsuario'].encode()).hexdigest()
    print("trace_id %s " % trace_id)

    r.sadd('trace_'+ trace_id, trackinfo)
    return {'resultado': 'ok',
            'descripcion': 'usuario valido'}


def panic(args):
    if not contains(args, ['idDevice', 'idUsuario', 'latitude', 'longitude', 'bateria', 'emailsIds', 'ongsIds']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    if 'Login' not in session or session['Login'] == False:
        return {'resultado': 'error',
                'descripcion': 'sesion no valida'}

    user = args['idUsuario']
    if r.hget('users', user) is None:
        return {'resultado': 'error',
                'descripcion': 'usuario no valido'}

    msg = MIMEText("Alerta generada por " + user )
    msg['Subject'] = 'Alerta hancel'
    msg['From'] = "alertas@hancel.flip.org.co"
    msg['To'] = "andresfcalderon@gmail.com"


    s = smtplib.SMTP('localhost')
    s.sendmail(msg['From'], ["andresfcalderon@gmail.com"], msg.as_string())
    s.quit()

    #send alerts
    return {'resultado': 'ok'}

def activation(args):
    if not contains(args, ['activation_code']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    if args['activation_code'] in r.smembers('flip_codes'):
        r.srem('flip_codes', args['activation_code'])
        description ={"code":"ok",
                      "numbers":["3002753666","3015036470"]}
                       
        return {'resultado': 'ok',
                'descripcion': description}

    description = {"code":"invalid"}

    return {'resultado': 'error'}

def gen_code(args):
    code = uuid.uuid4().hex[0:6]
    r.sadd('flip_codes',code)
    res = {'activation_code':code}
    return res

def center(v):
    cx=0.0;
    cy=0.0;

    if len(v):
      for p in v:
         cx=cx+p[0]
         cy=cy+p[1]
      cx = cx / float(len(v))
      cy = cy / float(len(v))
    return [cx,cy]

    
   

def show_trace(args):
    if not contains(args, ['hash']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    trace_id  = args['hash']
    trace = []
    for p in r.smembers('trace_'+trace_id):
      p=json.loads(str(p.decode()).replace("'",'"'))
      trace.append( [float(p['lon']), float(p['lat']), float(p['time']) ] )

    trace = sorted(trace, key=lambda trace: trace[2])

    return render_template('trace.html', trace=str(trace), center=center(trace)  )


hansel_methods = {
    'registro': register,
    'login': login,
    'tracking': tracking,
    'trace': show_trace,
    'panico': panic,
    'activation': activation,
    'code':gen_code
}


@app.route('/', methods=['GET'])
def hansel():
    if 'f' in request.args:
        f = request.args['f']
    else:
        f = 'undefined'

    if f in hansel_methods.keys():
        res = hansel_methods[f](request.args)
        print("res=%s" % str(res))
        if isinstance(res, dict):
            return json.dumps(res)
 
        return res
    else:
        return json.dumps({'resultado': 'error',
                           'descripcion': 'usuario duplicado'})



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)

