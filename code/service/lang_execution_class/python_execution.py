import subprocess
import os
import time
from service.lang_execution_class.code_execution import CodeExecution

class PythonExecution(CodeExecution):
    def __init__(self, user_id, quiz_id):
        self.user_id = user_id
        self.quiz_id = quiz_id
        self.filename = None

    def compile_code(self, source_code: str, unique_id: str) -> bool:
        # 파일 경로 생성
        directory = "python_files"
        self.filename = os.path.join(directory, f"code_python_{self.user_id}_{self.quiz_id}_{unique_id}.py")
        
        # 디렉터리가 존재하지 않으면 생성
        if not os.path.exists(directory):
            os.makedirs(directory)
        
        # 파일에 소스 코드 작성
        with open(self.filename, 'w', encoding='utf-8') as f:
            f.write(source_code)
        
        return True  # Python은 별도의 컴파일 필요 없음

    def run_code(self, inputs):
        # 입력이 문자열인 경우에만 인코딩 수행
        if isinstance(inputs, str):
            input_data = inputs.encode('utf-8')
        elif isinstance(inputs, bytes):
            input_data = inputs
        else:
            raise TypeError("Input must be a string or bytes")
        process = subprocess.Popen(f"python {self.filename}", stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
        stdout, stderr = process.communicate(input=input_data, timeout=5)
        return stdout.decode('utf-8').strip(), stderr.decode('utf-8').strip()

    def cleanup(self):
        if self.filename and os.path.exists(self.filename):
            os.remove(self.filename)
