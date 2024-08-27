from flask import Blueprint, request, jsonify
from service.code_service import CodeService
from domain.models import Quiz, QuizCase
from sqlalchemy.orm import sessionmaker
from contextlib import contextmanager
from db import engine

code_blueprint = Blueprint('code', __name__)
Session = sessionmaker(bind=engine)

@contextmanager
def session_scope():
    session = Session()
    try:
        yield session
        session.commit()
    except Exception:
        session.rollback()
        raise
    finally:
        session.close()

@code_blueprint.route('/code/run_code', methods=['POST'])
def run_user_code():
    data = request.json
    user_id = data['user_id']
    code = data['code']
    language = data['language']
    quiz_id = data['quiz_id']

    with session_scope() as session:
        quiz_cases = session.query(QuizCase).filter_by(quiz_id=quiz_id).all()
        if not quiz_cases:
            return jsonify({'error': 'Quiz cases not found'}), 404

        service = CodeService(user_id=user_id, quiz_id=quiz_id, input=None)
        result = service.run_code(language=language, code=code, quiz_cases=quiz_cases)
        return jsonify(result), 200

@code_blueprint.route('/code/execute_code', methods=['POST'])
def execute_user_code():
    data = request.json
    user_id = data['user_id']
    code = data['code']
    language = data['language']
    input = data['input']
    
    service = CodeService(user_id=user_id, quiz_id=None, input=input)
    result = service.execute_code(language=language, code=code, input=input)
    return jsonify(result), 200