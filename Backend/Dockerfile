FROM openjdk:17-jdk
ARG JAR_FILE=./build/libs/chatbot-0.0.1-SNAPSHOT.jar

# CHAT GPT API KEY
ENV API_KEY=(INPUT_GPT_API_KEY)
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]