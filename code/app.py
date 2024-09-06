from flask import Flask
from controller.code_controller import code_blueprint

app = Flask(__name__)
app.register_blueprint(code_blueprint)

@app.route('/health')
def health_check():
    return 'OK'

if __name__ == '__main__':
    app.run(debug=False, port=7000)
