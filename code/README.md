# 프로젝트 설명

## 설정 방법

1. 가상 환경 설정:
    
    Windows
    ```sh
    python -m venv venv
    source venv\Scripts\activate
    ```

    Linux
    ```sh
    python -m venv venv
    source venv/bin/activate
    ```

2. 필요 패키지 설치:
    ```sh
    pip install -r requirements.txt
    ```

3. 데이터베이스 설정:
    - `config.py` 파일에서 데이터베이스 URI를 설정합니다.

4. 서버 실행:
    ```sh
    set FLASK_APP=app.py
    flask run
    ```

## 실행 방법

서버를 실행한 후, POST 요청을 `/code/run_code` 엔드포인트로 보내 코드를 실행할 수 있습니다.

* Request
```json
{
    user_id: long,
    quiz_id: long,
    code: String,
    language: String
}
```

* response
```json
{
    result: String
}
```

## **Language Keyword Table**

| 대상 언어 | keyword |
|-----------|---------|
| 파이썬    | python  |
| 자바      | java    |
| C++       | cpp     |

## **Result Keyword Table**

| result         | 설명                                                                 |
|----------------|----------------------------------------------------------------------|
| **“정답”**     | 코드 실행 결과가 문제의 예상 output과 일치                           |
| **“오답”**     | 코드 실행 결과가 문제의 예상 output과 불일치                         |
| **“시간 초과”** | 코드 실행이 오래 걸리는 경우(5초 이상, 무한 루프)                    |
| **“컴파일 오류”** | 컴파일 대상 언어의 경우, 컴파일 오류가 발생했을 때                  |
| **“실행 오류”** | 코드 실행 중 오류가 발생했을 때                                      |


## 테스트

테스트를 실행하려면:
```sh
pytest tests/
```