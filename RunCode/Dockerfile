# 베이스 이미지: 리눅스 알파인
FROM openjdk:17-alpine

# 필요한 소프트웨어 패키지 설치
RUN apk add --no-cache \
    curl \
    build-base \
    python3 \
    py3-pip \
    gcc \
    g++ \
    python3-dev

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드 복사
COPY . /app/

# Gradle wrapper 실행권한 설정
RUN chmod +x ./gradlew

# Gradle을 사용하여 bootJar 실행
RUN ./gradlew bootJar

# /app/build/libs 디렉토리 내용 확인
RUN ls -a /app/build/libs

# 자바 환경 변수 설정
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$PATH:$JAVA_HOME/bin

# 파이썬 가상환경 설정
RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# 파이썬 라이브러리 설치
RUN pip install --no-cache-dir numpy pandas

# 컨테이너 실행 명령 설정
CMD ["java", "-jar", "build/libs/RunCode-0.0.1-SNAPSHOT.jar"]
