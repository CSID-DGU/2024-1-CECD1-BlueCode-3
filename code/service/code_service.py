from service.lang_execution_class.java_execution import JavaExecution
from service.lang_execution_class.python_execution import PythonExecution
import uuid
import subprocess
import uuid
import logging

# 로그 설정
logging.basicConfig(level=logging.INFO, 
                    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class CodeService:
    def __init__(self, user_id, quiz_id, input):
        self.user_id = user_id
        self.quiz_id = quiz_id
        self.input = input

    def run_code(self, language, code, quiz_cases):
        logger.info(f'코드 실행 시작 language={language}, user_id={self.user_id}, quiz_id={self.quiz_id}')
        
        strategy = self.get_code_execution_class(language)
        if not strategy:
            logger.error(f'지원하지 않는 language: {language} | {self.user_id}, {self.quiz_id}')
            return {'error': 'Unsupported language'}

        unique_id = str(uuid.uuid4())
        if not strategy.compile_code(code, unique_id):
            logger.error('컴파일 오류, language: {language}')
            return {'result': '컴파일 오류'}

        for index, quiz_case in enumerate(quiz_cases):
            inputs = quiz_case.input
            expected_output = quiz_case.output
            logger.info(f'{self.user_id}, {self.quiz_id} 실행 시작 case {index + 1} with inputs={inputs}')

            try:
                stdout, stderr = strategy.run_code(inputs)
                if stderr:
                    logger.error(f'채점 중 사용자 코드 에러 발생: {self.user_id}, {self.quiz_id}, {stderr}')
                    strategy.cleanup()
                    return {'result': f'실행 오류 {stderr}'}

                output = stdout
                if output != expected_output:
                    logger.warning(f'오답 도출 {self.user_id}, {self.quiz_id}')
                    strategy.cleanup()
                    return {'result': '오답'}

            except subprocess.TimeoutExpired:
                logger.error('시간초과')
                strategy.cleanup()
                return {'result': '시간 초과'}

        strategy.cleanup()
        return {'result': '정답'}
    
    def execute_code(self, language, code, input):
        logger.info(f'코드 실행 시작 language={language}, user_id={self.user_id}, input={input}')
        
        strategy = self.get_code_execution_class(language)
        if not strategy:
            logger.error(f'지원하지 않는 language: {language} | {self.user_id}, {self.quiz_id}')
            return {'error': 'Unsupported language'}

        unique_id = str(uuid.uuid4())
        if not strategy.compile_code(code, unique_id):
            logger.error('컴파일 오류, language: {language}')
            return {'result': '컴파일 오류'}

        logger.info(f'{self.user_id} 실행 시작 input={input}')

        try:
            stdout, stderr = strategy.run_code(input)
            if stderr:
                logger.error(f'코드 실행 중 사용자 코드 에러 발생: {self.user_id}, {self.quiz_id}, {stderr}')
                strategy.cleanup()
                return {'result': stderr}

        except subprocess.TimeoutExpired:
            logger.error('시간초과')
            strategy.cleanup()
            return {'result': '서버 호출 시간 초과: 무한루프가 발생하는 코드이거나 서버 처리 한도에 도달한 코드입니다.'}
    
        strategy.cleanup()
        return {'result': stdout}
        
    def get_code_execution_class(self, language):
        logger.debug(f'탐색 시작 language={language}')
        if language == 'python':
            return PythonExecution(self.user_id, self.quiz_id)
        elif language == 'java':
            return JavaExecution(self.user_id, self.quiz_id)
        logger.error(f'미발견 language={language}')
        return None
