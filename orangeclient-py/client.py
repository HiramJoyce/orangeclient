from flask import Flask, render_template, request, jsonify

import json
import os

app = Flask(__name__)


@app.route('/hello')
def hello():
    return 'Hello Orange'


@app.route('/execute', methods=['POST'])
def execute():
    data = json.loads(request.form.get('command'))
    print(json.dumps(data, indent=4))
    # data = json.loads(request.get_data())
    # print(data)
    res = []
    for cmd in data['cmds']:
        cmd_lines = cmd.split('\n')
        for line in cmd_lines:
            line_res = os.popen(line)
            res.append(line_res.read())
    print(res)
    return jsonify(res)


if __name__ == '__main__':
    app.run('0.0.0.0', 5000, True)
