import re
import time
import curl
import getpass, poplib

C = curl.Curl()

while 1:
  M = poplib.POP3('localhost')
  M.user("user")
  M.pass_("pass")
  numMessages = len(M.list()[1])

  for i in range(1,numMessages+1):
    for msj in M.retr(i)[1]:
      urls = re.findall('http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+', str(msj.decode()))
      
      for url in urls:
           print( "visit URL %s" % url )
           try:
              C.get(url)
           except:
              pass

    r = M.dele(i)
    print("delete result %s" % r)

  M.quit()
  time.sleep(1)

