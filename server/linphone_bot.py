import zmq
import time
import mechanize

port = "5556"
context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect("tcp://localhost:%s" % port)

while True:
	account = socket.recv_json()

	request = mechanize.Request('http://www.linphone.org/free-sip-service.html')
	response = mechanize.urlopen(request)	     
	forms = mechanize.ParseResponse(response, backwards_compat=False)	     
	response.close()

	form = forms[0]

	form['desired-login'] = account['user'].lower()
	form['password2'] = str(account['passwd'])[0:10]
	form['confirm'] = str(account['passwd'])[0:10]
	form['email'] = account['email'].lower()
	form['firstname'] = 'Fulano'
	form['name'] ='de tal'

	request2 = form.click() # mechanize.Request object
	try:
	   response2 = mechanize.urlopen(request2)
	except mechanize.HTTPError, response2:
	   pass
	
	r = response2.read()
	
	response2.close()

	if "A confirmation link has been sent to your email address" in r:
		socket.send_string("submitted")
	elif "This username already exists." in r:
		socket.send_string("duplicated")
	else:
		socket.send_string("submitted")
