FROM python:3.9-slim

# 작업 디렉토리 설정
WORKDIR /app

# Install Java
RUN apt-get update && apt-get install -y default-jdk

# Install C++
RUN apt-get update && apt-get install -y g++

# requirements.txt를 컨테이너에 복사
COPY requirements.txt .

# requirements.txt에 명시된 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# 애플리케이션 소스 코드 복사
COPY . .

# Expose port 7000
EXPOSE 7000

# Run the Flask application on port 7000
CMD ["flask", "run", "--host=0.0.0.0", "--port=7000"]