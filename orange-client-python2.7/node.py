# client

import socket

address = ('127.0.0.1', 10201)
socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
socket.connect(address)


# socket.send('hihi')

while True:
    try:
        print 'start recv'
        inString = socket.recv(1024)
        if not inString:
            break
        if inString:
            print inString
    except:
        break
