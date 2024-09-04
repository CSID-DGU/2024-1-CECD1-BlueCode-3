FROM python:3.9-slim

# 작업 디렉토리 설정
WORKDIR /app

# requirements.txt를 컨테이너에 복사
COPY requirements.txt .

# requirements.txt에 명시된 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# 애플리케이션 소스 코드 복사
COPY . .

# Flask 애플리케이션 실행
CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]
