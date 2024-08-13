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
        self.filename = f"python_files/code_python_{self.user_id}_{self.quiz_id}_{unique_id}.py"
        with open(self.filename, 'w') as f:
            f.write(source_code)
        return True  # Python은 별도의 컴파일 필요 없음

    def run_code(self, inputs: str) -> tuple[str, str]:
        process = subprocess.Popen(f"python {self.filename}", stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
        stdout, stderr = process.communicate(input=inputs.encode('utf-8'), timeout=5)
        return stdout.decode().strip(), stderr.decode().strip()

    def cleanup(self):
        if self.filename and os.path.exists(self.filename):
            os.remove(self.filename)