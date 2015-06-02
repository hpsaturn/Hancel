import zmq
import redis
import json
import uuid
import time
import hashlib
import random
from flask import Flask, session, request, render_template
import smtplib
from email.mime.text import MIMEText


linphone_prefix = "hancel_lp_dummy_"

app = Flask(__name__)
app.secret_key = 'F12Zr47j\3yX R~X@H!jmM]Lwf/,?KT'

r = redis.StrictRedis(host='localhost', port=6379, db=0)


def contains(x, y):
    for z in y:
        if z not in x:
            return False
    return True


def register(args):
    if not contains(args, ['idDevice', 'usuario', 'password', 'email', 'mailsAmigos', 'imei', 'verDroid']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    user = args['usuario']
    password = args['password']
    print("register.... %s" % str(args))

    if r.hget('users', user) is None:
        r.hset('users', user, password)
        r.hset('idDevice', user, args['idDevice'])
        r.hset('email', user, args['email'])
        r.hset('mailsAmigos', user, args['mailsAmigos'])
        r.hset('imei', user, args['imei'])
        r.hset('verDroid', user, args['verDroid'])
    
        port = "5556"
        context = zmq.Context()
        socket = context.socket(zmq.PAIR)
        socket.bind("tcp://*:%s" % port)

        sip_account = {'user':str(linphone_prefix+user), 'passwd': password} 
        socket.send_json(sip_account)
        msg = socket.recv()

        print(msg)
        socket.close()

        description ={"email":"ok",
                      "usuario":"usuario no existe",
                      "usr-id":str(r.incr('sesion-seq')),
                      "envio-mail":"se envio un mail con sus datos de registro"}

        return {'resultado': 'ok','descripcion': description}

    return {'resultado': 'error',
            'descripcion': {'usuario': 'usuario duplicado'}}


def login(args):
    if not contains(args, ['strUsr', 'strPass', 'id_device']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    user = args['strUsr']
    password = args['strPass']
    if r.hget('users', user) is None:
        return {'resultado': 'error',
                'descripcion': 'usuario no valido'}
    else:
        print( 'password = %s %s' % (r.hget('users', user), password) )
        if r.hget('users', user).decode("utf-8") != password:
            return {'resultado': 'error',
                    'descripcion': 'password invalido'}
        else:
            session['Login'] = True
            session['id-tracking'] = str(r.incr('track-seq'))
            description ={"usuario":"ok",
                          "usr-id":str(r.incr('sesion-seq')),
                          "id-tracking":"0"}

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
                      "numbers":"3002753666"}
                       
        return {'resultado': 'ok',
                'descripcion': description}

    description = {"code":"invalid"}

    return {'resultado': 'error'}

def gen_code(args):
    code = uuid.uuid4().hex[0:6]
    r.sadd('flip_codes',code)
    res = {'activation_code':code}
    return res

def show_trace(args):
    if not contains(args, ['hash']):
        return {'resultado': 'error',
                'descripcion': 'argumentos insuficientes'}

    trace_id  = args['hash']
    trace = []
    for p in r.smembers('trace_'+trace_id):
      p=json.loads(str(p.decode()).replace("'",'"'))
      trace.append( [float(p['lon']), float(p['lat']) ] )

#      trace.append( [float(p['lon'])+random.uniform(0, 0.1),float(p['lat'])+random.uniform(0, 0.1) ] )
    
    return render_template('trace.html', trace=str(trace)  )


hansel_methods = {
    'registro': register,
    'login': login,
    'tracking': tracking,
    'trace': show_trace,
    'panico': panic,
    'activation': activation,
    'code':gen_code
}


@app.route('/hansel/', methods=['GET'])
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
