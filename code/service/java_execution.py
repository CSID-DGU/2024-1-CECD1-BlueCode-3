import subprocess
import os
import time
from service.code_execution import CodeExecution

class JavaExecution(CodeExecution):
    def __init__(self, user_id, quiz_id):
        self.user_id = user_id
        self.quiz_id = quiz_id
        self.java_dir = None

    def compile_code(self, source_code: str, unique_id: str) -> bool:
        self.java_dir = f"java_files/java_files_{unique_id}"
        os.makedirs(self.java_dir, exist_ok=True)
        java_filename = f"{self.java_dir}/Main.java"
        with open(java_filename, 'w') as f:
            f.write(source_code)
        compile_process = subprocess.Popen(f"javac {java_filename}", stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
        _, compile_stderr = compile_process.communicate()
        return not compile_stderr  # 컴파일 에러가 없으면 True 반환

    def run_code(self, inputs: str) -> tuple[str, str]:
        process = subprocess.Popen(f"java -cp {self.java_dir} Main", stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
        stdout, stderr = process.communicate(input=inputs.encode('utf-8'), timeout=5)
        return stdout.decode().strip(), stderr.decode().strip()

    def cleanup(self):
        if self.java_dir and os.path.exists(self.java_dir):
            for file in os.listdir(self.java_dir):
                os.remove(os.path.join(self.java_dir, file))
            os.rmdir(self.java_dir)