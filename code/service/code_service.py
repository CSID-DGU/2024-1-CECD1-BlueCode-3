from service.java_execution import JavaExecution
from service.python_execution import PythonExecution
import uuid
import subprocess
import uuid
import logging

# 로그 설정
logging.basicConfig(level=logging.INFO, 
                    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class CodeService:
    def __init__(self, user_id, quiz_id):
        self.user_id = user_id
        self.quiz_id = quiz_id

    def execute_code(self, language, code, quiz_cases):
        logger.info(f'코드 실행 시작 language={language}, quiz_id={self.quiz_id}')
        
        strategy = self.get_code_execution_class(language)
        if not strategy:
            logger.error(f'지원하지 않는 language: {language}')
            return {'error': 'Unsupported language'}

        unique_id = str(uuid.uuid4())
        if not strategy.compile_code(code, unique_id):
            logger.error('컴파일 오류, language: {language}')
            return {'result': '컴파일 오류'}

        for index, quiz_case in enumerate(quiz_cases):
            inputs = quiz_case.input
            expected_output = quiz_case.output
            logger.info(f'Running case {index + 1} with inputs={inputs}')

            try:
                stdout, stderr = strategy.run_code(inputs)
                if stderr:
                    logger.error(f'채점 중 사용자 코드 에러 발생: {stderr}')
                    strategy.cleanup()
                    return {'result': f'오류 발생 {stderr}'}

                output = stdout
                if output != expected_output:
                    logger.warning('오답 도출')
                    strategy.cleanup()
                    return {'result': '오답'}

            except subprocess.TimeoutExpired:
                logger.error('시간초과')
                strategy.cleanup()
                return {'result': '시간 초과'}

        strategy.cleanup()
        return {'result': '정답'}

    def get_code_execution_class(self, language):
        logger.debug(f'탐색 시작 language={language}')
        if language == 'python':
            return PythonExecution(self.user_id, self.quiz_id)
        elif language == 'java':
            return JavaExecution(self.user_id, self.quiz_id)
        logger.error(f'미발견 language={language}')
        return None
