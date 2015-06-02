import zmq
import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys

port = "5556"
context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect("tcp://localhost:%s" % port)

driver = webdriver.Chrome()

while True:
	driver.get("http://www.linphone.org/free-sip-service.html")
	account = socket.recv_json()
	print(str(account))


	values = {'desired-login': account['user'].lower(),
        	'password2':str(account['passwd'])[0:10],
	        'confirm': str(account['passwd'])[0:10],
	        'email': 'xxx@xxxx.xyz', 
		'firstname':'X',
		'name':'X',
	}
	print("values")
	print(str(values))

	for k,v in values.items():
		elem = driver.find_element_by_name(k)
		elem.send_keys(v)
		time.sleep(0.1)
		
	driver.find_element_by_name("validate").click()

	socket.send_string("submitted")

