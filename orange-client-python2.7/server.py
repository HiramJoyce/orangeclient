# server
import socket
import json
import os

address = ('0.0.0.0', 10202)
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(address)
s.listen(5)


def execute(message):
    data = json.loads(message)
    print(json.dumps(data, indent=4))
    # data = json.loads(request.get_data())
    # print(data)
    res = []
    for cmd in data['cmds']:
        cmd_lines = cmd.split('\n')
        for line in cmd_lines:
            line_res = os.popen(line)
            res.append(line_res.read().encode('utf-8'))
            line_res.close()
    print(json.dumps(res, indent=4))
    return json.dumps(res, indent=4)


def onMessage(message, ss):
    print message
    ss.send(execute(message))


while True:
    try:
        # print 'Wait conn...'
        ss, addr = s.accept()
        print 'got connected from', addr
        read = ss.recv(4096)
        if read.strip() == 'quit':
            ss.send('node closing.')
            break
        else:
            onMessage(read, ss)
            # print 'end'
    except Exception as e:
        print e
    finally:
        ss.close()
s.close()
