Backend
=====

<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=Mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/H2 Database-4479A1?style=flat-square&logoColor=white"/>

---

**GPT API Testing 방법**

1. "API_KEY" 명으로 환경변수 설정

 
    시스템 환경 변수 편집 >> 고급 >> 환경 변수 >> 사용자 변수 또는 시스템 변수 새로 만들기 

    >> 변수이름: API_KEY, 변수 값: "자신의 GPT API key 문자열 전체" >> 저장


2. Spring application 실행


3. http://localhost:8080/chat 으로 post 요청

body **(Content-Type: application/json)**:

    "prompt" : "GPT에 보내고 싶은 프롬프트 메시지"

---

**주의 사항**

* API_KEY 설정 후 컴퓨터를 재부팅을 진행해주세요.
* GPT API를 실행하기 전 토큰 비용을 충분히 충전해 주세요.
