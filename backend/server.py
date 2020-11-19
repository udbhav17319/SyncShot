from flask import Flask, request, jsonify
import mysql.connector
import datetime as dt

mydb = mysql.connector.connect(
    host = "localhost",
    user = "root",
    passwd = "bhunesh@123",
    database = "logindata"
    )
mydbcursor = mydb.cursor()
# global mydb, mydbcursor
# query=f"{}"
# mydbcursor.execute(query)
# mydb.commit()

# one time from cmd - 
# CREATE TABLE IF NOT EXISTS `accounts` (
#     `id` int(11) NOT NULL AUTO_INCREMENT,
#     `username` varchar(50) NOT NULL,
#     `password` varchar(255) NOT NULL,
#     PRIMARY KEY (`id`)
# ) AUTO_INCREMENT=2;

# INSERT INTO `accounts` (`id`, `username`, `password`) VALUES (1, 'om', 'first@user');
# INSERT INTO `accounts` (`id`, `username`, `password`) VALUES (1, 'udbhav', 'second@user');
# INSERT INTO `accounts` (`id`, `username`, `password`) VALUES (1, 'bhunesh', 'third@user');

app = Flask(__name__)

# root
@app.route("/")
def home():
    return "Homepage !!!!"

# GET
@app.route('/users/<user>')
def hello_user(user):
    """
    this serves as a demo purpose
    :param user:
    :return: str
    """
    return "Hello %s!" % user

#POST
@app.route('/api/login/<loginid>/<passwd>', methods=['GET'])
def check_login(loginid,passwd):
    username=loginid
    password=passwd
    mydbcursor.execute('SELECT * FROM accounts WHERE username = %s AND password = %s', (username, password,))
    account=mydbcursor.fetchone()
    if account:
        return "Logged in successfully!"
    else: 
        return "Incorrect username/password!"

# running web app in local machine
if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)