#https://blog.miguelgrinberg.com/post/designing-a-restful-api-with-python-and-flask

from flask import Flask, jsonify, request, make_response, abort
import os
from scipy import misc

app = Flask(__name__)

@app.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({'error': 'bad request'}), 400)

@app.errorhandler(401)
def invalid(error):
    return make_response(jsonify({'error': 'invalid'}), 401)

shoplist = [
	{
		'name': 'Jellyfish',
		'image': 'jellyfish',
		'description': "It stings",
		'cost': 1
	},
	{
		'name': 'Kitten',
		'image': 'kitten',
		'description': "It purrs",
		'cost': 1
	}
]

registeredUsers = [
    {
        "login": "admin",
        "password" : "admin1"
    },
    {
        "login": "username",
        "password" : "password"
    }

]

@app.route('/login', methods=['POST'])
def login():
    loginVal = ""
    passVal = ""
    if 'login' in request.json:
        print("login found!: " + request.json.get('login', '?'))
        loginVal = request.json.get('login', '?')
    if 'password' in request.json:
        print("password found!: " + request.json.get('password', '?'))
        passVal = request.json.get('password', '?')

    for each in registeredUsers:
        if (each.get('login', '') == loginVal) and (each.get('password', '') == passVal):
            print("user is registered!\nlogin: " + loginVal + ", password: " + passVal)
            return jsonify({"success" : True})

    return jsonify({"success" : False})
	
@app.route('/list', methods=['GET'])
def get_list():
    print("list")
    return jsonify({'list': shoplist})

@app.route('/buy', methods=['POST'])
def post_buy():
    print("buy")

    if not request.json:
        abort(400)

    if 'name' in request.json:
        print("buy: " + request.json.get('name', '?'))
    else:
        print("?")

    return jsonify({'spend': 0})

app.run(port='5005',debug=True)