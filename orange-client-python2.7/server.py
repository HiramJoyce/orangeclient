# server
import socket

address = ('0.0.0.0', 10202)
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(address)
s.listen(5)


def onMessage(message, ss):
    print message
    ss.send('srv -> '+message)


while True:
    try:
        ss, addr = s.accept()
        print 'got connected from', addr
        read = ss.recv(4096)
        if read.strip() == 'quit':
            ss.send('node closing.')
            break
        else:
            onMessage(read, ss)
    except Exception as e:
        print e
    finally:
        ss.close()
s.close()
