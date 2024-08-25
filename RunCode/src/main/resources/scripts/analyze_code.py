import json
import sys

input_count = 0  # 전역 변수로 input_count 선언

def mock_input(prompt):
    global input_count
    input_count += 1  # input() 호출 시마다 카운트 증가
    print(prompt + "Mocked input provided")
    return "1"  # 모든 input() 호출에 대해 "1" 반환

def simulate_inputs(code):
    global input_count
    input_count = 0  # 코드 실행 전에 카운트 초기화
    exec(code.replace('input(', 'mock_input('))  # input()을 mock_input()으로 대체
    return input_count

if __name__ == "__main__":
    user_code = sys.stdin.read()
    simulate_inputs(user_code)  # 결과를 input_count에 저장
    # JSON 결과만 별도로 출력
    print("###JSON###" + json.dumps({"input_count": input_count}) + "###JSON###")