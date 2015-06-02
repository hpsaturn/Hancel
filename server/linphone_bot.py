import zmq
import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys


port = "5556"
context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect("tcp://localhost:%s" % port)

driver = webdriver.Chrome()

def select_by_string(string, timeout):
        try:
            element = WebDriverWait(driver, timeout).until(
                EC.presence_of_element_located((By.XPATH, "//*[contains(text(), '" + string + "')]"))
            )
            if element:
                return element
            return None
        except:
            return None

while True:
	driver.get("http://www.linphone.org/free-sip-service.html")
	account = socket.recv_json()
	print(str(account))


	values = {'desired-login': account['user'].lower(),
        	'password2':str(account['passwd'])[0:10],
	        'confirm': str(account['passwd'])[0:10],
	        'email':  account['email'].lower(),
		'firstname':'Fulano',
		'name':'de tal',
	}
	print("values")
	print(str(values))


	for k,v in values.items():
		elem = driver.find_element_by_name(k)
		elem.send_keys(v)
		time.sleep(0.1)
		#element_text = elem.text
		#element_attribute_value = elem.get_attribute('value')
		#print("element_text=" % element_text )
		#print("element_attribute_value=" % element_attribute_value )

		
	driver.find_element_by_name("validate").click()

	if select_by_string("A confirmation link has been sent to your email address", 4):
		print("OK")
	else:
		print("ERROR")


	socket.send_string("submitted")
