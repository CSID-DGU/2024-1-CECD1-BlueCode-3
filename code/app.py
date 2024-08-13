from flask import Flask
from db import engine
from controller.code_controller import code_blueprint

app = Flask(__name__)
app.register_blueprint(code_blueprint)

if __name__ == '__main__':
    app.run(debug=False)
