-- 초기 데이터 삽입 sql 파일
-- curriculums
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (0, false, true, 0, false, 11, null, null, null, null, 'python', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, false, false, 0, true, 13, null, null, 1, 1, '변수와 자료형', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, false, false, 0, true, 9, 2, null, 1, 1, '문자열 처리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (3, false, false, 0, true, 2, 3, null, 1, 1, '조건문', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, false, false, 0, true, 5, 4, null, 1, 1, '반복문', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, false, false, 0, true, 12, 5, null, 1, 1, '함수', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, false, false, 0, true, 6, 6, null, 1, 1, '자료구조', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, false, false, 0, false, 6, 7, null, 1, 1, '파일 처리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (8, false, false, 0, true, 4, 8, null, 1, 1, '예외 처리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, false, false, 0, false, 10, 9, null, 1, 1, '클래스와 객체', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (10, false, false, 0, false, 2, 10, null, 1, 1, '모듈과 패키지', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 1, false, 1, null, null, 2, 1, '변수 선언과 활용', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 2, false, 1, null, null, 2, 1, '정수와 실수', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 3, false, 1, null, null, 2, 1, '문자와 문자열', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 4, false, 1, null, null, 2, 1, '불리언', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 5, false, 1, null, null, 2, 1, '입력 input()과 출력 print()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 6, false, 1, null, null, 2, 1, 'int(), float()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 7, false, 1, null, null, 2, 1, 'str(), bool()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 8, false, 1, null, null, 2, 1, 'list(), tuple()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 9, false, 1, null, null, 2, 1, 'set(), dict()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 10, false, 1, null, null, 2, 1, 'chr(), ord()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 11, false, 1, null, null, 2, 1, '산술 연산자 - \'+\', \'-\', \'*\', \'/\', \'//\', \'%\', \'**\'', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 12, false, 1, null, null, 2, 1, '비교 연산자 - \'==\', \'!=\', \'>\', \'<\', \'>=\', \'<=\'', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (1, true, false, 13, false, 1, null, null, 2, 1, '논리 연산자 - \'and\', \'or\', \'not\'', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 1, false, 1, null, null, 3, 1, '인덱싱 및 슬라이싱', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 2, false, 1, null, null, 3, 1, 'upper(), lower()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 3, false, 1, null, null, 3, 1, 'strip(), lstrip(), rstrip()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 4, false, 1, null, null, 3, 1, 'replace(), split(), join()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 5, false, 1, null, null, 3, 1, 'find(), count()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 6, false, 1, null, null, 3, 1, 'isalpha(), isdigit(), isalnum()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 7, false, 1, null, null, 3, 1, 'startswith(), endswith()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 8, false, 1, null, null, 3, 1, 'capitalize(), title(), swapcase()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (2, true, false, 9, false, 1, null, null, 3, 1, '포맷 - \'%\', format(), f-string, Template', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (3, true, false, 1, false, 1, null, null, 4, 1, 'if, elif, else', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (3, true, false, 2, false, 1, null, null, 4, 1, '조건문 중첩', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, true, false, 1, false, 1, null, null, 5, 1, 'for', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, true, false, 2, false, 1, null, null, 5, 1, 'while', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, true, false, 3, false, 1, null, null, 5, 1, 'break, continue, else', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, true, false, 4, false, 1, null, null, 5, 1, '반복문 중첩', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (4, true, false, 5, false, 1, null, null, 5, 1, '리스트 컴프리헨션', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 1, false, 1, null, null, 6, 1, 'def', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 2, false, 1, null, null, 6, 1, '매개변수와 반환값', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 3, false, 1, null, null, 6, 1, '위치 인자와 키워드 인자', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 4, false, 1, null, null, 6, 1, '가변 인자', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 5, false, 1, null, null, 6, 1, '재귀 함수', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 6, false, 1, null, null, 6, 1, '람다 함수', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 7, false, 1, null, null, 6, 1, 'len(), sum()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 8, false, 1, null, null, 6, 1, 'min(), max()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 9, false, 1, null, null, 6, 1, 'sorted(), zip()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 10, false, 1, null, null, 6, 1, 'range(), enumerate()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 11, false, 1, null, null, 6, 1, 'abs(), round()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (5, true, false, 12, false, 1, null, null, 6, 1, 'map(), filter(), reduce()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 1, false, 1, null, null, 7, 1, '리스트와 리스트 메서드', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 2, false, 1, null, null, 7, 1, '튜플과 튜플 불변성', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 3, false, 1, null, null, 7, 1, '딕셔너리와 딕셔너리 메서드', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 4, false, 1, null, null, 7, 1, '집합과 집합 연산', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 5, false, 1, null, null, 7, 1, '큐와 스택', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (6, true, false, 6, false, 1, null, null, 7, 1, '링크드 리스트', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 1, false, 1, null, null, 8, 1, '파일 열기 모드(\'r\', \'w\', \'a\', \'x\', 바이너리, 추가 옵션)', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 2, false, 1, null, null, 8, 1, '파일 읽기 - read(), readline(), readlines()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 3, false, 1, null, null, 8, 1, '파일 쓰기 - write(), writelines()', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 4, false, 1, null, null, 8, 1, 'csv 파일 처리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 5, false, 1, null, null, 8, 1, 'json 파일 처리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (7, true, false, 6, false, 1, null, null, 8, 1, 'json 데이터 파싱 및 생성', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (8, true, false, 1, false, 1, null, null, 9, 1, '예외 발생 원리', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (8, true, false, 2, false, 1, null, null, 9, 1, 'try, except, else, finally', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (8, true, false, 3, false, 1, null, null, 9, 1, '다양한 종류의 예외', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (8, true, false, 4, false, 1, null, null, 9, 1, '사용자 정의 예외', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 1, false, 1, null, null, 10, 1, '객체 지향 프로그래밍의 개념', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 2, false, 1, null, null, 10, 1, '클래스', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 3, false, 1, null, null, 10, 1, '객체 생성', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 4, false, 1, null, null, 10, 1, '생성자(__init__)', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 5, false, 1, null, null, 10, 1, '소멸자(__del__)', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 6, false, 1, null, null, 10, 1, '상속', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 7, false, 1, null, null, 10, 1, '메서드 오버라이딩(오버로딩과의 차이점)', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 8, false, 1, null, null, 10, 1, '다형성', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 9, false, 1, null, null, 10, 1, '접근 제어자', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (9, true, false, 10, false, 1, null, null, 10, 1, '게터와 세터', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (10, true, false, 1, false, 1, null, null, 11, 1, '모듈의 개념과 import', 'PYTHON');
INSERT INTO bluecode.curriculums (chapter_num, leaf_node, root_node, sub_chapter_num, testable, total_chapter_count, before_id, next_id, parent_id, root_id, curriculum_name, lang_type) VALUES (10, true, false, 2, false, 1, null, null, 11, 1, '표준 라이브러리 - math, datetime, os', 'PYTHON');

UPDATE bluecode.curriculums SET chapter_num = 1, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 13, before_id = null, next_id = 3, parent_id = 1, root_id = 1, curriculum_name = '변수와 자료형', lang_type = 'PYTHON' WHERE curriculum_id = 2;
UPDATE bluecode.curriculums SET chapter_num = 2, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 9, before_id = 2, next_id = 4, parent_id = 1, root_id = 1, curriculum_name = '문자열 처리', lang_type = 'PYTHON' WHERE curriculum_id = 3;
UPDATE bluecode.curriculums SET chapter_num = 3, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 2, before_id = 3, next_id = 5, parent_id = 1, root_id = 1, curriculum_name = '조건문', lang_type = 'PYTHON' WHERE curriculum_id = 4;
UPDATE bluecode.curriculums SET chapter_num = 4, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 5, before_id = 4, next_id = 6, parent_id = 1, root_id = 1, curriculum_name = '반복문', lang_type = 'PYTHON' WHERE curriculum_id = 5;
UPDATE bluecode.curriculums SET chapter_num = 5, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 12, before_id = 5, next_id = 7, parent_id = 1, root_id = 1, curriculum_name = '함수', lang_type = 'PYTHON' WHERE curriculum_id = 6;
UPDATE bluecode.curriculums SET chapter_num = 6, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 6, before_id = 6, next_id = 8, parent_id = 1, root_id = 1, curriculum_name = '자료구조', lang_type = 'PYTHON' WHERE curriculum_id = 7;
UPDATE bluecode.curriculums SET chapter_num = 7, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = false, total_chapter_count = 6, before_id = 7, next_id = 9, parent_id = 1, root_id = 1, curriculum_name = '파일 처리', lang_type = 'PYTHON' WHERE curriculum_id = 8;
UPDATE bluecode.curriculums SET chapter_num = 8, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = true, total_chapter_count = 4, before_id = 8, next_id = 10, parent_id = 1, root_id = 1, curriculum_name = '예외 처리', lang_type = 'PYTHON' WHERE curriculum_id = 9;
UPDATE bluecode.curriculums SET chapter_num = 9, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = false, total_chapter_count = 10, before_id = 9, next_id = 11, parent_id = 1, root_id = 1, curriculum_name = '클래스와 객체', lang_type = 'PYTHON' WHERE curriculum_id = 10;
UPDATE bluecode.curriculums SET chapter_num = 10, leaf_node = false, root_node = false, sub_chapter_num = 0, testable = false, total_chapter_count = 2, before_id = 10, next_id = null, parent_id = 1, root_id = 1, curriculum_name = '모듈과 패키지', lang_type = 'PYTHON' WHERE curriculum_id = 11;

-- missions
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_SUBMIT', '아무 난이도 문제 답안 1회 제출하기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'TEST_SUBMIT', '아무 난이도 문제 답안 2회 제출하기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'TEST_SUBMIT', '아무 난이도 문제 답안 3회 제출하기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_PASS', '아무 난이도 문제 1회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'TEST_PASS', '아무 난이도 문제 2회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'TEST_PASS', '아무 난이도 문제 3회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_EASY_PASS', '입문자 난이도 문제 1회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_NORMAL_PASS', '초급자 난이도 문제 1회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_HARD_PASS', '중급자 난이도 문제 1회 맞추기', null, 'DAILY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'TEST_SUBMIT', '아무 난이도 문제 답안 5회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 6, 'TEST_SUBMIT', '아무 난이도 문제 답안 6회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 7, 'TEST_SUBMIT', '아무 난이도 문제 답안 7회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 8, 'TEST_SUBMIT', '아무 난이도 문제 답안 8회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 9, 'TEST_SUBMIT', '아무 난이도 문제 답안 9회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 10, 'TEST_SUBMIT', '아무 난이도 문제 답안 10회 제출하기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 5, 'TEST_PASS', '아무 난이도 문제 5회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 6, 'TEST_PASS', '아무 난이도 문제 6회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 7, 'TEST_PASS', '아무 난이도 문제 7회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 8, 'TEST_PASS', '아무 난이도 문제 8회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 9, 'TEST_PASS', '아무 난이도 문제 9회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 10, 'TEST_PASS', '아무 난이도 문제 10회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 5, 'TEST_EASY_PASS', '입문자 난이도 문제 5회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 5, 'TEST_NORMAL_PASS', '초급자 난이도 문제 5회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 5, 'TEST_HARD_PASS', '중급자 난이도 문제 5회 맞추기', null, 'WEEKLY', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_SUBMIT', '아무 난이도 문제 답안 1회 제출하기', '정답 기계 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_SUBMIT', '아무 난이도 문제 답안 2회 제출하기', '정답 기계 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_SUBMIT', '아무 난이도 문제 답안 4회 제출하기', '정답 기계 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_SUBMIT', '아무 난이도 문제 답안 8회 제출하기', '정답 기계 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_SUBMIT', '아무 난이도 문제 답안 16회 제출하기', '정답 기계 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_SUBMIT', '아무 난이도 문제 답안 32회 제출하기', '정답 기계 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_SUBMIT', '아무 난이도 문제 답안 64회 제출하기', '정답 기계 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_SUBMIT', '아무 난이도 문제 답안 128회 제출하기', '정답 기계 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_SUBMIT', '아무 난이도 문제 답안 256회 제출하기', '정답 기계 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_SUBMIT', '아무 난이도 문제 답안 512회 제출하기', '정답 기계 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 1회 제출하기', '입문자 정복자 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 2회 제출하기', '입문자 정복자 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 4회 제출하기', '입문자 정복자 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 8회 제출하기', '입문자 정복자 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 16회 제출하기', '입문자 정복자 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 32회 제출하기', '입문자 정복자 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 64회 제출하기', '입문자 정복자 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 128회 제출하기', '입문자 정복자 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 256회 제출하기', '입문자 정복자 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_EASY_SUBMIT', '입문자 난이도 문제 답안 512회 제출하기', '입문자 정복자 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 1회 제출하기', '초급자 도전자 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 2회 제출하기', '초급자 도전자 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 4회 제출하기', '초급자 도전자 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 8회 제출하기', '초급자 도전자 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 16회 제출하기', '초급자 도전자 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 32회 제출하기', '초급자 도전자 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 64회 제출하기', '초급자 도전자 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 128회 제출하기', '초급자 도전자 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 256회 제출하기', '초급자 도전자 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_NORMAL_SUBMIT', '초급자 난이도 문제 답안 512회 제출하기', '초급자 도전자 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 1회 제출하기', '중급자 챔피언 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 2회 제출하기', '중급자 챔피언 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 4회 제출하기', '중급자 챔피언 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 8회 제출하기', '중급자 챔피언 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 16회 제출하기', '중급자 챔피언 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 32회 제출하기', '중급자 챔피언 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 64회 제출하기', '중급자 챔피언 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 128회 제출하기', '중급자 챔피언 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 256회 제출하기', '중급자 챔피언 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_HARD_SUBMIT', '중급자 난이도 문제 답안 512회 제출하기', '중급자 챔피언 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_PASS', '아무 난이도 문제 답안 1회 맞추기', '정답의 길 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_PASS', '아무 난이도 문제 답안 2회 맞추기', '정답의 길 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_PASS', '아무 난이도 문제 답안 4회 맞추기', '정답의 길 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_PASS', '아무 난이도 문제 답안 8회 맞추기', '정답의 길 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_PASS', '아무 난이도 문제 답안 16회 맞추기', '정답의 길 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_PASS', '아무 난이도 문제 답안 32회 맞추기', '정답의 길 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_PASS', '아무 난이도 문제 답안 64회 맞추기', '정답의 길 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_PASS', '아무 난이도 문제 답안 128회 맞추기', '정답의 길 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_PASS', '아무 난이도 문제 답안 256회 맞추기', '정답의 길 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_PASS', '아무 난이도 문제 답안 512회 맞추기', '정답의 길 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 1회 맞추기', '입문 마스터 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 2회 맞추기', '입문 마스터 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 4회 맞추기', '입문 마스터 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 8회 맞추기', '입문 마스터 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 16회 맞추기', '입문 마스터 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 32회 맞추기', '입문 마스터 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 64회 맞추기', '입문 마스터 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 128회 맞추기', '입문 마스터 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 256회 맞추기', '입문 마스터 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_EASY_PASS', '입문자 난이도 문제 답안 512회 맞추기', '입문 마스터 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 1회 맞추기', '초급자 영웅 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 2회 맞추기', '초급자 영웅 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 4회 맞추기', '초급자 영웅 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 8회 맞추기', '초급자 영웅 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 16회 맞추기', '초급자 영웅 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 32회 맞추기', '초급자 영웅 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 64회 맞추기', '초급자 영웅 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 128회 맞추기', '초급자 영웅 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 256회 맞추기', '초급자 영웅 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_NORMAL_PASS', '초급자 난이도 문제 답안 512회 맞추기', '초급자 영웅 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 1회 맞추기', '중급자 정복자 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 2회 맞추기', '중급자 정복자 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 4회 맞추기', '중급자 정복자 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 8회 맞추기', '중급자 정복자 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 16회 맞추기', '중급자 정복자 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 32회 맞추기', '중급자 정복자 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 64회 맞추기', '중급자 정복자 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 128회 맞추기', '중급자 정복자 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 256회 맞추기', '중급자 정복자 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_HARD_PASS', '중급자 난이도 문제 답안 512회 맞추기', '중급자 정복자 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_FAIL', '아무 난이도 문제 답안 1회 틀리기', '틀린 적 없다 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_FAIL', '아무 난이도 문제 답안 2회 틀리기', '틀린 적 없다 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_FAIL', '아무 난이도 문제 답안 4회 틀리기', '틀린 적 없다 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'TEST_FAIL', '아무 난이도 문제 답안 8회 틀리기', '틀린 적 없다 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'TEST_FAIL', '아무 난이도 문제 답안 16회 틀리기', '틀린 적 없다 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'TEST_FAIL', '아무 난이도 문제 답안 32회 틀리기', '틀린 적 없다 LV 6', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'TEST_FAIL', '아무 난이도 문제 답안 64회 틀리기', '틀린 적 없다 LV 7', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'TEST_FAIL', '아무 난이도 문제 답안 128회 틀리기', '틀린 적 없다 LV 8', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'TEST_FAIL', '아무 난이도 문제 답안 256회 틀리기', '틀린 적 없다 LV 9', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'TEST_FAIL', '아무 난이도 문제 답안 512회 틀리기', '틀린 적 없다 LV 10', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'TEST_INIT_COMPLETE', '초기 테스트 1회 수행 완료하기', '초기 테스트 탐험가 LV 1', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'TEST_INIT_COMPLETE', '초기 테스트 2회 수행 완료하기', '초기 테스트 탐험가 LV 2', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (30, 3, 'TEST_INIT_COMPLETE', '초기 테스트 3회 수행 완료하기', '초기 테스트 탐험가 LV 3', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'TEST_INIT_COMPLETE', '초기 테스트 4회 수행 완료하기', '초기 테스트 탐험가 LV 4', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (50, 5, 'TEST_INIT_COMPLETE', '초기 테스트 5회 수행 완료하기', '초기 테스트 탐험가 LV 5', 'CHALLENGE', 'TEST');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'STUDY_COMPLETE', '커리큘럼 챕터 1회 학습 완료하기', null, 'DAILY', 'STUDY');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (50, 5, 'STUDY_COMPLETE', '커리큘럼 챕터 5회 학습 완료하기', null, 'WEEKLY', 'STUDY');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_7_COMPLETE', '"python: str(), bool()" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자열과 불리언의 만남');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_8_COMPLETE', '"python: list(), tuple()" 학습 통과하기', 'CHALLENGE', 'STUDY', '리스트 vs 튜플, 변할 것인가 고정될 것인가?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_9_COMPLETE', '"python: set(), dict()" 학습 통과하기', 'CHALLENGE', 'STUDY', '집합과 딕셔너리의 세계로!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_10_COMPLETE', '"python: chr(), ord()" 학습 통과하기', 'CHALLENGE', 'STUDY', '숫자와 문자 사이를 넘나들다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_11_COMPLETE', '"python: 산술 연산자 - \'+\', \'-\', \'*\', \'/\', \'//\', \'%\', \'**\'" 학습 통과하기', 'CHALLENGE', 'STUDY', '연산의 무한 가능성');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_12_COMPLETE', '"python: 비교 연산자 - \'==\', \'!=\', \'>\', \'<\', \'>=\', \'<=\'" 학습 통과하기', 'CHALLENGE', 'STUDY', '비교의 미학');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_13_COMPLETE', '"python: 논리 연산자 - \'and\', \'or\', \'not\'" 학습 통과하기', 'CHALLENGE', 'STUDY', '진리의 삼총사');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_1_COMPLETE', '"python: 인덱싱 및 슬라이싱" 학습 통과하기', 'CHALLENGE', 'STUDY', '리스트 해부학');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_2_COMPLETE', '"python: upper(), lower()" 학습 통과하기', 'CHALLENGE', 'STUDY', '대문자냐 소문자냐, 그것이 문제로다
');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_3_COMPLETE', '"python: strip(), lstrip(), rstrip()" 학습 통과하기', 'CHALLENGE', 'STUDY', '완벽주의자! 공백은 싫어');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (10, 1, 'STUDY_COMPLETE', '커리큘럼 챕터 1회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 1');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (20, 2, 'STUDY_COMPLETE', '커리큘럼 챕터 2회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 2');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (40, 4, 'STUDY_COMPLETE', '커리큘럼 챕터 4회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 3');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (80, 8, 'STUDY_COMPLETE', '커리큘럼 챕터 8회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 4');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (160, 16, 'STUDY_COMPLETE', '커리큘럼 챕터 16회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 5');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (320, 32, 'STUDY_COMPLETE', '커리큘럼 챕터 32회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 6');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (640, 64, 'STUDY_COMPLETE', '커리큘럼 챕터 64회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 7');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1280, 128, 'STUDY_COMPLETE', '커리큘럼 챕터 128회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 8');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (2560, 256, 'STUDY_COMPLETE', '커리큘럼 챕터 256회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 9');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (5120, 512, 'STUDY_COMPLETE', '커리큘럼 챕터 512회 학습 완료하기', 'CHALLENGE', 'STUDY', '커리큘럼 정복자 LV 10');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_PYTHON_COMPLETE', '"python" 내 모든 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '파이썬');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_변수와 자료형_COMPLETE', '"변수와 자료형" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '데이터의 첫 발걸음, 변수를 선언하라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_문자열 처리_COMPLETE', '"문자열 처리" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '문자열, 그 끝없는 이야기');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_조건문_COMPLETE', '"조건문" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '조건에 따른 갈림길, 어느 쪽을 선택할 것인가?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_반복문_COMPLETE', '"반복문" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '끝없는 루프 속으로, 반복의 마법');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_함수_COMPLETE', '"함수" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '함수를 정의하라, 그리고 호출하라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_자료구조_COMPLETE', '"자료구조" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '데이터의 저장소, 구조를 설계하라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_파일 처리_COMPLETE', '"파일 처리" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '파일을 열고 데이터를 다루는 탐험가');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_예외 처리_COMPLETE', '"예외 처리" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '문제에 직면했을 때, 예외를 잡아라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_클래스와 객체_COMPLETE', '"클래스와 객체" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '객체를 창조하라, 클래스의 비밀을 밝혀라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (1000, 1, 'STUDY_모듈과 패키지_COMPLETE', '"모듈과 패키지" 내 모든 서브 챕터 학습 완료하기', 'CHALLENGE', 'STUDY', '모듈을 불러오고 패키지를 활용하라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_1_COMPLETE', '"python: 변수 선언과 활용" 학습 통과하기', 'CHALLENGE', 'STUDY', '변수여, 선언하라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_2_COMPLETE', '"python: 정수와 실수" 학습 통과하기', 'CHALLENGE', 'STUDY', '숫자들의 대결, 정수 vs 실수');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_3_COMPLETE', '"python: 문자와 문자열" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자의 힘, 문자열을 연결하라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_4_COMPLETE', '"python: 불리언" 학습 통과하기', 'CHALLENGE', 'STUDY', '진실이냐 거짓이냐');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_5_COMPLETE', '"python: 입력 input()과 출력 print()" 학습 통과하기', 'CHALLENGE', 'STUDY', '입력과 출력의 오케스트라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_변수와 자료형_CHAP_1_SUB_6_COMPLETE', '"python: int(), float()" 학습 통과하기', 'CHALLENGE', 'STUDY', '타입을 바꾸는 마술사');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_4_COMPLETE', '"python: replace(), split(), join()" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자열을 자르고 붙이고!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_5_COMPLETE', '"python: find(), count()" 학습 통과하기', 'CHALLENGE', 'STUDY', '숨어있는 문자 찾기');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_6_COMPLETE', '"python: isalpha(), isdigit(), isalnum()" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자냐 숫자냐, 그 외냐');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_7_COMPLETE', '"python: startswith(), endswith()" 학습 통과하기', 'CHALLENGE', 'STUDY', '시작과 끝을 잡아라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_8_COMPLETE', '"python: capitalize(), title(), swapcase()" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자의 변신');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_문자열 처리_CHAP_2_SUB_9_COMPLETE', '"python: 포맷 - \'%\', format(), f-string, Template" 학습 통과하기', 'CHALLENGE', 'STUDY', '문자열 포맷의 예술가');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_조건문_CHAP_3_SUB_1_COMPLETE', '"python: if, elif, else" 학습 통과하기', 'CHALLENGE', 'STUDY', '조건에 따른 길, 선택은 너의 몫!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_조건문_CHAP_3_SUB_2_COMPLETE', '"python: 조건문 중첩" 학습 통과하기', 'CHALLENGE', 'STUDY', '거 조건 진짜 까다롭네!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_반복문_CHAP_4_SUB_1_COMPLETE', '"python: for" 학습 통과하기', 'CHALLENGE', 'STUDY', '반복의 마에스트로');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_반복문_CHAP_4_SUB_2_COMPLETE', '"python: while" 학습 통과하기', 'CHALLENGE', 'STUDY', '끝없는 반복의 나선');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_반복문_CHAP_4_SUB_3_COMPLETE', '"python: break, continue, else" 학습 통과하기', 'CHALLENGE', 'STUDY', '흐름을 깨고 이어라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_반복문_CHAP_4_SUB_4_COMPLETE', '"python: 반복문 중첩" 학습 통과하기', 'CHALLENGE', 'STUDY', '반복 속 반복, 그 끝은 어디?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_반복문_CHAP_4_SUB_5_COMPLETE', '"python: 리스트 컴프리헨션" 학습 통과하기', 'CHALLENGE', 'STUDY', '한 줄로 해결하는 리스트 마법');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_1_COMPLETE', '"python: def" 학습 통과하기', 'CHALLENGE', 'STUDY', '이것이 함수다!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_2_COMPLETE', '"python: 매개변수와 반환값" 학습 통과하기', 'CHALLENGE', 'STUDY', '인풋과 아웃풋의 상관관계');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_3_COMPLETE', '"python: 위치 인자와 키워드 인자" 학습 통과하기', 'CHALLENGE', 'STUDY', '인자의 순서를 지켜라!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_4_COMPLETE', '"python: 가변 인자" 학습 통과하기', 'CHALLENGE', 'STUDY', '무한한 인자의 힘');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_5_COMPLETE', '"python: 재귀 함수" 학습 통과하기', 'CHALLENGE', 'STUDY', '우리 어디서 본 적 없니?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_6_COMPLETE', '"python: 람다 함수" 학습 통과하기', 'CHALLENGE', 'STUDY', '익명의 함수, 간결하게!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_7_COMPLETE', '"python: len(), sum()" 학습 통과하기', 'CHALLENGE', 'STUDY', '길이와 합의 조화');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_8_COMPLETE', '"python: min(), max()" 학습 통과하기', 'CHALLENGE', 'STUDY', '최소와 최대의 싸움');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_9_COMPLETE', '"python: sorted(), zip()" 학습 통과하기', 'CHALLENGE', 'STUDY', '정렬하고 압축하라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_10_COMPLETE', '"python: range(), enumerate()" 학습 통과하기', 'CHALLENGE', 'STUDY', '범위와 인덱스의 결합');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_11_COMPLETE', '"python: abs(), round()" 학습 통과하기', 'CHALLENGE', 'STUDY', '절대값과 반올림의 마스터');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_함수_CHAP_5_SUB_12_COMPLETE', '"python: map(), filter(), reduce()" 학습 통과하기', 'CHALLENGE', 'STUDY', '함수형 프로그래밍의 꽃');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_1_COMPLETE', '"python: 리스트와 리스트 메서드" 학습 통과하기', 'CHALLENGE', 'STUDY', '리스트의 모든 것');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_2_COMPLETE', '"python: 튜플과 튜플 불변성" 학습 통과하기', 'CHALLENGE', 'STUDY', '한 번 정하면 바꾸지 않는다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_3_COMPLETE', '"python: 딕셔너리와 딕셔너리 메서드" 학습 통과하기', 'CHALLENGE', 'STUDY', '키와 값의 완벽한 조화');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_4_COMPLETE', '"python: 집합과 집합 연산" 학습 통과하기', 'CHALLENGE', 'STUDY', '집합을 더하고 빼고');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_5_COMPLETE', '"python: 큐와 스택" 학습 통과하기', 'CHALLENGE', 'STUDY', '줄 서서 대기하시오, 아니면 쌓으시오!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_자료구조_CHAP_6_SUB_6_COMPLETE', '"python: 링크드 리스트" 학습 통과하기', 'CHALLENGE', 'STUDY', '노드들이 손을 맞잡다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_1_COMPLETE', '"python: 파일 열기 모드(\'r\', \'w\', \'a\', \'x\', 바이너리, 추가 옵션)" 학습 통과하기', 'CHALLENGE', 'STUDY', '파일을 여는 다양한 방식');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_2_COMPLETE', '"python: 파일 읽기 - read(), readline(), readlines()" 학습 통과하기', 'CHALLENGE', 'STUDY', '파일 속 문장들을 불러오다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_3_COMPLETE', '"python: 파일 쓰기 - write(), writelines()" 학습 통과하기', 'CHALLENGE', 'STUDY', '파일 속에 기록을 남기다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_4_COMPLETE', '"python: csv 파일 처리" 학습 통과하기', 'CHALLENGE', 'STUDY', '엑셀같은 CSV 다루기');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_5_COMPLETE', '"python: json 파일 처리" 학습 통과하기', 'CHALLENGE', 'STUDY', '데이터의 가벼운 친구, JSON');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_파일 처리_CHAP_7_SUB_6_COMPLETE', '"python: json 데이터 파싱 및 생성" 학습 통과하기', 'CHALLENGE', 'STUDY', 'JSON을 다루는 기술');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_예외 처리_CHAP_8_SUB_1_COMPLETE', '"python: 예외 발생 원리" 학습 통과하기', 'CHALLENGE', 'STUDY', '무슨 일이 일어났는가?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_예외 처리_CHAP_8_SUB_2_COMPLETE', '"python: try, except, else, finally" 학습 통과하기', 'CHALLENGE', 'STUDY', '예외도 여러 가지');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_예외 처리_CHAP_8_SUB_3_COMPLETE', '"python: 다양한 종류의 예외" 학습 통과하기', 'CHALLENGE', 'STUDY', '돌연변이가 이렇게 많아?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_예외 처리_CHAP_8_SUB_4_COMPLETE', '"python: 사용자 정의 예외" 학습 통과하기', 'CHALLENGE', 'STUDY', '나만의 예외 만들기');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_1_COMPLETE', '"python: 객체 지향 프로그래밍의 개념" 학습 통과하기', 'CHALLENGE', 'STUDY', '엔티티시여!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_2_COMPLETE', '"python: 클래스" 학습 통과하기', 'CHALLENGE', 'STUDY', '꿇어라! 이게 나의 클라스다!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_3_COMPLETE', '"python: 객체 생성" 학습 통과하기', 'CHALLENGE', 'STUDY', '객체를 창조하라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_4_COMPLETE', '"python: 생성자(__init__)" 학습 통과하기', 'CHALLENGE', 'STUDY', '탄생의 순간');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_5_COMPLETE', '"python: 소멸자(__del__)" 학습 통과하기', 'CHALLENGE', 'STUDY', 'RIP!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_6_COMPLETE', '"python: 상속" 학습 통과하기', 'CHALLENGE', 'STUDY', '엄빠의 찬스!');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_7_COMPLETE', '"python: 메서드 오버라이딩(오버로딩과의 차이점)" 학습 통과하기', 'CHALLENGE', 'STUDY', '오버로딩의 장인');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_8_COMPLETE', '"python: 다형성" 학습 통과하기', 'CHALLENGE', 'STUDY', '무궁무진');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_9_COMPLETE', '"python: 접근 제어자" 학습 통과하기', 'CHALLENGE', 'STUDY', '문을 열어줄 것인가 잠글 것인가?');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_클래스와 객체_CHAP_9_SUB_10_COMPLETE', '"python: 게터와 세터" 학습 통과하기', 'CHALLENGE', 'STUDY', '데이터를 주고 받다');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_모듈과 패키지_CHAP_10_SUB_1_COMPLETE', '"python: 모듈의 개념과 import" 학습 통과하기', 'CHALLENGE', 'STUDY', '코드를 가져와 재사용하라');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, mission_type, service_type, title) VALUES (100, 1, 'STUDY_모듈과 패키지_CHAP_10_SUB_2_COMPLETE', '"python: 표준 라이브러리 - math, datetime, os" 학습 통과하기', 'CHALLENGE', 'STUDY', '필수 라이브러리 탐험하기');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'USER_LOGIN', '1일 로그인하기', null, 'DAILY', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (50, 7, 'USER_LOGIN', '7일 로그인하기', null, 'WEEKLY', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'USER_STREAK_1', '1일 연속 로그인하기', '연속 로그인 달인 LV 1', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'USER_STREAK_2', '2일 연속 로그인하기', '연속 로그인 달인 LV 2', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'USER_STREAK_4', '4일 연속 로그인하기', '연속 로그인 달인 LV 3', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'USER_STREAK_8', '8일 연속 로그인하기', '연속 로그인 달인 LV 4', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'USER_STREAK_16', '16일 연속 로그인하기', '연속 로그인 달인 LV 5', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'USER_STREAK_32', '32일 연속 로그인하기', '연속 로그인 달인 LV 6', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'USER_STREAK_64', '64일 연속 로그인하기', '연속 로그인 달인 LV 7', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'USER_STREAK_128', '128일 연속 로그인하기', '연속 로그인 달인 LV 8', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'USER_STREAK_256', '256일 연속 로그인하기', '연속 로그인 달인 LV 9', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'USER_STREAK_512', '512일 연속 로그인하기', '연속 로그인 달인 LV 10', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 1회 완료하기', '일일 퀘스트 LV 1', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 2회 완료하기', '일일 퀘스트 LV 2', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 4회 완료하기', '일일 퀘스트 LV 3', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 8회 완료하기', '일일 퀘스트 LV 4', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 16회 완료하기', '일일 퀘스트 LV 5', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 32회 완료하기', '일일 퀘스트 LV 6', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 64회 완료하기', '일일 퀘스트 LV 7', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 128회 완료하기', '일일 퀘스트 LV 8', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 256회 완료하기', '일일 퀘스트 LV 9', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'MISSION_DAILY_COMPLETE', '일일 미션 전체 512회 완료하기', '일일 퀘스트 LV 10', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 1회 완료하기', '주간 챌린지 LV 1', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 2회 완료하기', '주간 챌린지 LV 2', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 4회 완료하기', '주간 챌린지 LV 3', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 8회 완료하기', '주간 챌린지 LV 4', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 16회 완료하기', '주간 챌린지 LV 5', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 32회 완료하기', '주간 챌린지 LV 6', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 64회 완료하기', '주간 챌린지 LV 7', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 128회 완료하기', '주간 챌린지 LV 8', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 256회 완료하기', '주간 챌린지 LV 9', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'MISSION_WEEKLY_COMPLETE', '주간 미션 전체 512회 완료하기', '주간 챌린지 LV 10', 'CHALLENGE', 'MISSION');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_QUESTION', '질문 1회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'CHAT_QUESTION', '질문 2회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'CHAT_QUESTION', '질문 3회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_DEF_QUESTION', '개념 태그 질문 1회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'CHAT_DEF_QUESTION', '개념 태그 질문 2회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'CHAT_DEF_QUESTION', '개념 태그 질문 3회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_QUESTION', '코드 태그 질문 1회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'CHAT_CODE_QUESTION', '코드 태그 질문 2회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'CHAT_CODE_QUESTION', '코드 태그 질문 3회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 1회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 2, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 2회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 3, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 3회 수행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 1회 진행하기', null, 'DAILY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 1, 'CHAT_QUESTION', '질문 1회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 2, 'CHAT_QUESTION', '질문 2회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 3, 'CHAT_QUESTION', '질문 3회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 4, 'CHAT_QUESTION', '질문 4회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_QUESTION', '질문 5회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_DEF_QUESTION', '개념 태그 질문 5회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 6, 'CHAT_DEF_QUESTION', '개념 태그 질문 6회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 7, 'CHAT_DEF_QUESTION', '개념 태그 질문 7회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 8, 'CHAT_DEF_QUESTION', '개념 태그 질문 8회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 9, 'CHAT_DEF_QUESTION', '개념 태그 질문 9회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 10, 'CHAT_DEF_QUESTION', '개념 태그 질문 10회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_CODE_QUESTION', '코드 태그 질문 5회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 6, 'CHAT_CODE_QUESTION', '코드 태그 질문 6회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 7, 'CHAT_CODE_QUESTION', '코드 태그 질문 7회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 8, 'CHAT_CODE_QUESTION', '코드 태그 질문 8회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 9, 'CHAT_CODE_QUESTION', '코드 태그 질문 9회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 10, 'CHAT_CODE_QUESTION', '코드 태그 질문 10회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 5회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 6, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 6회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 7, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 7회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 8, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 8회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 9, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 9회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 10, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 10회 수행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (100, 5, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 5회 진행하기', null, 'WEEKLY', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_QUESTION', '질문 1회 수행하기', '질문 마스터 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_QUESTION', '질문 2회 수행하기', '질문 마스터 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_QUESTION', '질문 4회 수행하기', '질문 마스터 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_QUESTION', '질문 8회 수행하기', '질문 마스터 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_QUESTION', '질문 16회 수행하기', '질문 마스터 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_QUESTION', '질문 32회 수행하기', '질문 마스터 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_QUESTION', '질문 64회 수행하기', '질문 마스터 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_QUESTION', '질문 128회 수행하기', '질문 마스터 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_QUESTION', '질문 256회 수행하기', '질문 마스터 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_QUESTION', '질문 512회 수행하기', '질문 마스터 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_DEF_QUESTION', '개념 태그 질문 1회 수행하기', '개념 해커 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_DEF_QUESTION', '개념 태그 질문 2회 수행하기', '개념 해커 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_DEF_QUESTION', '개념 태그 질문 4회 수행하기', '개념 해커 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_DEF_QUESTION', '개념 태그 질문 8회 수행하기', '개념 해커 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_DEF_QUESTION', '개념 태그 질문 16회 수행하기', '개념 해커 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_DEF_QUESTION', '개념 태그 질문 32회 수행하기', '개념 해커 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_DEF_QUESTION', '개념 태그 질문 64회 수행하기', '개념 해커 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_DEF_QUESTION', '개념 태그 질문 128회 수행하기', '개념 해커 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_DEF_QUESTION', '개념 태그 질문 256회 수행하기', '개념 해커 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_DEF_QUESTION', '개념 태그 질문 512회 수행하기', '개념 해커 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_QUESTION', '코드 태그 질문 1회 수행하기', '코드 정복자 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_CODE_QUESTION', '코드 태그 질문 2회 수행하기', '코드 정복자 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_CODE_QUESTION', '코드 태그 질문 4회 수행하기', '코드 정복자 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_CODE_QUESTION', '코드 태그 질문 8회 수행하기', '코드 정복자 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_CODE_QUESTION', '코드 태그 질문 16회 수행하기', '코드 정복자 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_CODE_QUESTION', '코드 태그 질문 32회 수행하기', '코드 정복자 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_CODE_QUESTION', '코드 태그 질문 64회 수행하기', '코드 정복자 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_CODE_QUESTION', '코드 태그 질문 128회 수행하기', '코드 정복자 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_CODE_QUESTION', '코드 태그 질문 256회 수행하기', '코드 정복자 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_CODE_QUESTION', '코드 태그 질문 512회 수행하기', '코드 정복자 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 1회 수행하기', '오류 사냥꾼 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 2회 수행하기', '오류 사냥꾼 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 4회 수행하기', '오류 사냥꾼 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 8회 수행하기', '오류 사냥꾼 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 16회 수행하기', '오류 사냥꾼 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 32회 수행하기', '오류 사냥꾼 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 64회 수행하기', '오류 사냥꾼 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 128회 수행하기', '오류 사냥꾼 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 256회 수행하기', '오류 사냥꾼 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_ERRORS_QUESTION', '오류 태그 질문 512회 수행하기', '오류 사냥꾼 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 1회 진행하기', '코드 마스터 1 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 5회 진행하기', '코드 마스터 1 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 5회 진행하기', '코드 마스터 1 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 8회 진행하기', '코드 마스터 1 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 16회 진행하기', '코드 마스터 1 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 32회 진행하기', '코드 마스터 1 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 64회 진행하기', '코드 마스터 1 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 128회 진행하기', '코드 마스터 1 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 256회 진행하기', '코드 마스터 1 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_CODE_UNTIL_2', '코드 태그 질문 2단계까지 512회 진행하기', '코드 마스터 1 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 1회 진행하기', '코드 마스터 2 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 5회 진행하기', '코드 마스터 2 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 5회 진행하기', '코드 마스터 2 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 8회 진행하기', '코드 마스터 2 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 16회 진행하기', '코드 마스터 2 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 32회 진행하기', '코드 마스터 2 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 64회 진행하기', '코드 마스터 2 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 128회 진행하기', '코드 마스터 2 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 256회 진행하기', '코드 마스터 2 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_CODE_UNTIL_3', '코드 태그 질문 3단계까지 512회 진행하기', '코드 마스터 2 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 1회 진행하기', '코드 마스터 3 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 5회 진행하기', '코드 마스터 3 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 5회 진행하기', '코드 마스터 3 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 8회 진행하기', '코드 마스터 3 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 16회 진행하기', '코드 마스터 3 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 32회 진행하기', '코드 마스터 3 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 64회 진행하기', '코드 마스터 3 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 128회 진행하기', '코드 마스터 3 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 256회 진행하기', '코드 마스터 3 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_CODE_UNTIL_4', '코드 태그 질문 4단계까지 512회 진행하기', '코드 마스터 3 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 1회 진행하기', '오류 해결사 1 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 2회 진행하기', '오류 해결사 1 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 4회 진행하기', '오류 해결사 1 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 8회 진행하기', '오류 해결사 1 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 16회 진행하기', '오류 해결사 1 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 32회 진행하기', '오류 해결사 1 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 64회 진행하기', '오류 해결사 1 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 128회 진행하기', '오류 해결사 1 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 256회 진행하기', '오류 해결사 1 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_ERRORS_UNTIL_2', '오류 태그 질문 2단계까지 512회 진행하기', '오류 해결사 1 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 1회 진행하기', '오류 해결사 2 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 2회 진행하기', '오류 해결사 2 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 4회 진행하기', '오류 해결사 2 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 8회 진행하기', '오류 해결사 2 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 16회 진행하기', '오류 해결사 2 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 32회 진행하기', '오류 해결사 2 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 64회 진행하기', '오류 해결사 2 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 128회 진행하기', '오류 해결사 2 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 256회 진행하기', '오류 해결사 2 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_ERRORS_UNTIL_3', '오류 태그 질문 3단계까지 512회 진행하기', '오류 해결사 2 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 1회 진행하기', '오류 해결사 3 LV 1', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 2회 진행하기', '오류 해결사 3 LV 2', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 4회 진행하기', '오류 해결사 3 LV 3', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 8회 진행하기', '오류 해결사 3 LV 4', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 16회 진행하기', '오류 해결사 3 LV 5', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 32회 진행하기', '오류 해결사 3 LV 6', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 64회 진행하기', '오류 해결사 3 LV 7', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 128회 진행하기', '오류 해결사 3 LV 8', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 256회 진행하기', '오류 해결사 3 LV 9', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'CHAT_ERRORS_UNTIL_4', '오류 태그 질문 4단계까지 512회 진행하기', '오류 해결사 3 LV 10', 'CHALLENGE', 'CHAT');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (10, 1, 'USER_TOTAL_1', '1일 누적 로그인하기', '로그인 레전드 LV 1', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (20, 2, 'USER_TOTAL_2', '2일 누적 로그인하기', '로그인 레전드 LV 2', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (40, 4, 'USER_TOTAL_4', '4일 누적 로그인하기', '로그인 레전드 LV 3', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (80, 8, 'USER_TOTAL_8', '8일 누적 로그인하기', '로그인 레전드 LV 4', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (160, 16, 'USER_TOTAL_16', '16일 누적 로그인하기', '로그인 레전드 LV 5', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (320, 32, 'USER_TOTAL_32', '32일 누적 로그인하기', '로그인 레전드 LV 6', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (640, 64, 'USER_TOTAL_64', '64일 누적 로그인하기', '로그인 레전드 LV 7', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (1280, 128, 'USER_TOTAL_128', '128일 누적 로그인하기', '로그인 레전드 LV 8', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (2560, 256, 'USER_TOTAL_256', '256일 누적 로그인하기', '로그인 레전드 LV 9', 'CHALLENGE', 'USER');
INSERT INTO bluecode.missions (exp, mission_count, action_type, text, title, mission_type, service_type) VALUES (5120, 512, 'USER_TOTAL_512', '512일 누적 로그인하기', '로그인 레전드 LV 10', 'CHALLENGE', 'USER');

-- quiz
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 3, 1, '리스트', '튜플', '리스트', '딕셔너리', '집합', '다음 설명에 해당하는 자료형은 무엇일까요?

- 순서가 보장되고, 중복되는 값을 허용하며, 요소를 추가, 삭제, 수정할 수 있는 자료형입니다.', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 3, 2, '3', '3.333333333', '3', '1', '오류 발생', '```
x = 10
y = 3
print(x // y)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 3, 3, 'Hello World', 'HelloWorld', 'Hello World', '"Hello World"', '오류 발생', '```
a = "Hello"
b = "World"
c = a + " " + b
print(c)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 4, '튜플', '리스트', '튜플', '딕셔너리', '집합', '다음 설명에 해당하는 자료형은 무엇일까요?

- 변하지 않는 값들의 순서 있는 집합으로, 한 번 생성되면 요소를 추가하거나 삭제할 수 없습니다.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 5, 'find()', 'find()', 'index()', 'count()', 'replace()', '다음 설명에 해당하는 문자열 메서드는 무엇일까요?

- 주어진 문자열에서 특정 문자열이 처음으로 나타나는 인덱스를 반환합니다.
- 만약 찾는 문자열이 없으면 -1을 반환합니다.', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 6, 'split() 메서드', '% 포맷팅', 'format() 메서드', 'f-string', 'split() 메서드', '다음 중 문자열 포맷팅 방식이 아닌 것은?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 7, '[\'Hello\', \'World!\']', '[\'Hello\', \'World!\']', '[\'Hello\', \' World!\']', '[\'Hello, World!\']', '오류 발생', '```
text = "  Hello, World!  "
result = text.strip().split(\',\')
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 8, '안녕하세요, 제 이름은 홍길동이고, 나이는 30살입니다.', '안녕하세요, 제 이름은 {name}이고, 나이는 {age}살입니다.', '안녕하세요, 제 이름은 홍길동이고, 나이는 30살입니다.', '오류 발생', '안녕하세요, 제 이름은 name이고, 나이는 age살입니다.', '```
name = "홍길동"
age = 30
result = f"안녕하세요, 제 이름은 {name}이고, 나이는 {age}살입니다."
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 9, '조건을 검사하기 위한 표현식', '조건을 검사하기 위한 표현식', '반복문', '함수 호출', '리스트 선언', '다음 중 Python의 조건문에서 if 문을 사용할 때 반드시 필요한 요소는 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 10, 'if 문은 첫 번째 조건을 검사하고, elif 문은 이전 if 문이나 elif 문이 거짓(False)일 때 다음 조건을 검사한다.', 'if 문은 조건을 검사하고, elif 문은 항상 참(True)으로 간주된다.', 'if 문은 첫 번째 조건을 검사하고, elif 문은 이전 if 문이나 elif 문이 거짓(False)일 때 다음 조건을 검사한다.', 'elif 문은 if 문 다음에만 사용할 수 있으며, if 문은 여러 번 반복될 수 있다.', 'elif 문은 else 문 다음에 사용된다.', '다음 중 Python의 조건문에서 if 문과 elif 문을 사용할 때, 두 문장의 차이에 대한 설명으로 올바른 것은 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 11, 'Divisible by 3', 'Even', 'Divisible by 3', 'Divisible by 5', 'Other', '```
y = 15
if y % 2 == 0:
    print("Even")
elif y % 3 == 0:
    print("Divisible by 3")
elif y % 5 == 0:
    print("Divisible by 5")
else:
    print("Other")
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 12, 'Y is not large', 'X is greater and large', 'X equals Y', 'Y is large', 'Y is not large', '```
x = 7
y = 10

if x > y:
    if x > 5:
        print("X is greater and large")
    else:
        print("X is greater but small")
elif x == y:
    print("X equals Y")
else:
    if y > 15:
        print("Y is large")
    else:
        print("Y is not large")
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 13, '조건식이 거짓(False)이 될 때', '조건식이 거짓(False)이 될 때', 'break 문을 만나지 않을 때', '반복 횟수가 10회에 도달할 때', 'continue 문을 사용할 때', '다음 중 Python에서 while 반복문이 종료되는 조건으로 올바른 것은 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 14, 'for 반복문은 주어진 시퀀스(예: 리스트, 문자열 등)의 각 항목을 순차적으로 순회하며 실행된다.', 'for 반복문은 주어진 조건이 참일 때까지 계속 반복된다.', 'for 반복문은 주어진 시퀀스(예: 리스트, 문자열 등)의 각 항목을 순차적으로 순회하며 실행된다.', 'for 반복문은 조건 없이 무한히 반복된다.', 'for 반복문은 오직 숫자 시퀀스에 대해서만 사용할 수 있다.', '다음 중 Python의 for 반복문에 대한 설명으로 올바른 것은 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 15, '[1, 3, "Done"]', '[1, 3, "Done"]', '[0, 2, 4, "Done"]', '[1, 3]', '[1, 3, 5]', '```
result = []
for i in range(5):
    if i % 2 == 0:
        continue
    result.append(i)
else:
    result.append("Done")

print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 16, 'Count is: 0
Count is: 1
Count is: 2
Loop ended.', 'Count is: 0
Count is: 1
Count is: 2', 'Count is: 0
Count is: 1
Count is: 2
Loop ended.', 'Loop ended.
Count is: 0
Count is: 1
Count is: 2', 'Count is: 1
Count is: 2
Loop ended.', '```
count = 0
while count < 3:
    print("Count is:", count)
    count += 1
else:
    print("Loop ended.")
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 17, '기본 인자를 사용하는 경우, 해당 인자에 값을 전달하지 않으면 기본값이 사용된다.', '기본 인자는 함수 호출 시 반드시 전달해야 한다.', '기본 인자는 항상 마지막 매개변수로만 설정할 수 있다.', '기본 인자를 사용하는 경우, 해당 인자에 값을 전달하지 않으면 기본값이 사용된다.', '기본 인자는 함수 내부에서만 사용할 수 있다.', '다음 중 Python에서 함수의 기본 인자(Default Argument)에 대한 설명으로 올바른 것은 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 18, '람다 함수는 익명 함수로, 이름이 없으며 단일 표현식으로 정의된다.', '람다 함수는 def 키워드를 사용하여 정의된다.', '람다 함수는 여러 줄의 코드를 포함할 수 있다.', '람다 함수는 익명 함수로, 이름이 없으며 단일 표현식으로 정의된다.', '람다 함수는 항상 기본 인자를 사용해야 한다.', '다음 중 Python에서 람다 함수(lambda function) 에 대한 설명으로 올바른 것은 무엇입니까?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 19, '28', '18', '28', '38', '오류 발생', '```
def func(a, b=5, c=10):
    return a + b + c

result = func(3, c=20)
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 20, '15', '6', '10', '15', '오류 발생', '```
def multiply(a, b=2):
    return a * b

result = multiply(b=3, a=5)
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 21, '튜플', '딕셔너리', '튜플', '집합', '큐', '다음 설명에 해당하는 자료구조는 무엇일까요?

- 순서가 보장되고, 중복된 값을 허용하며, 요소에 대한 접근과 수정이 용이합니다.
- 리스트와 유사하지만, 일단 생성된 후에는 요소를 추가하거나 삭제할 수 없습니다.', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 22, '해시 가능해야 한다.', '반드시 문자열 형태여야 한다.', '중복될 수 있다.', '변경 가능한 자료형이어야 한다.', '해시 가능해야 한다.', '딕셔너리에서 키(key)는 어떤 특징을 가져야 할까요?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 23, '[4, 16]', '[1, 4, 9, 16, 25]', '[4, 16]', '[2, 4]', '오류 발생', '```
numbers = [1, 2, 3, 4, 5]
result = [x**2 for x in numbers if x % 2 == 0]
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 24, '[0, 2, 6]', '[\'a1\', \'b2\', \'c3\']', '[1, 2, 3]', '[\'a\', \'b\', \'c\']', '[0, 2, 6]', '```
my_dict = {\'a\': 1, \'b\': 2, \'c\': 3}
result = list(map(lambda x: x[0] * x[1], my_dict.items()))
print(result)
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 25, '(try, except, else, finally)', '(try, except, else, finally)', '(except, try, else, finally)', '(try, else, except, finally)', '(try, finally, except, else)', '아래 try-except-else-finally의 역할을 바르게 나열한 것은?

- 예외가 발생할 가능성이 있는 코드 블록
- 예외가 발생했을 때 실행되는 코드 블록
- 예외 없이 try 블록이 정상적으로 실행되었을 때 실행되는 코드 블록
- 예외 발생 여부와 상관없이 항상 실행되는 코드 블록', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 26, '특정 상황에서 발생하는 오류를 명확하게 구분하고 처리하기 위해', '내장 예외를 사용하는 것보다 코드 실행 속도를 빠르게 하기 위해', '특정 상황에서 발생하는 오류를 명확하게 구분하고 처리하기 위해', '예외 처리를 생략하고 프로그램을 더 간결하게 만들기 위해', '파이썬의 기본 예외 처리 메커니즘을 수정하기 위해', '사용자 정의 예외를 만드는 주된 이유는 무엇일까요?', 'EASY', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 27, '0으로 나눌 수 없습니다.', '0으로 나눌 수 없습니다.', '타입 오류가 발생했습니다.', '예외가 발생하지 않았습니다.', '프로그램이 종료됩니다.', '```
try:
    result = 10 / 0
except ZeroDivisionError:
    print("0으로 나눌 수 없습니다.")
except TypeError:
    print("타입 오류가 발생했습니다.")
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 28, '사용자 정의 예외 발생
프로그램 종료', '사용자 정의 예외 발생', '프로그램 종료', '사용자 정의 예외 발생
프로그램 종료', '오류가 발생하여 프로그램이 종료됩니다.', '```
class CustomError(Exception):
    pass

try:
    raise CustomError("사용자 정의 예외 발생")
except CustomError as e:
    print(e)
finally:
    print("프로그램 종료")
```

위 파이썬 코드를 실행했을 때 출력 결과로 알맞은 것을 고르세요.', 'NORMAL', 'NUM');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (4, 3, 29, 'type', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 파이썬에서 변수의 자료형을 확인하기 위해 사용하는 함수는 (         )이다. 이 함수는 변수나 값을 입력받아 그 자료형을 반환한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (5, 3, 30, 'float', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 정수형을 실수형으로 변환하기 위해서는 (          ) 함수를 사용한다. 이 함수는 입력된 정수를 실수로 변환하여 반환한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (1, 3, 31, '7', null, null, null, null, '```
x = 7
y = 3
result = x // y + x % y
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (4, 3, 32, '55.5', null, null, null, null, '```
a = "15"
b = "3.5"
result = float(a) * float(b) + int(float(b))
print(result)n```

위 파이썬 코드의 실행 결과를 적으시오.
', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (7, 4, 33, 'replace', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 문자열에서 특정 부분을 다른 문자열로 교체하기 위해 사용하는 메서드는 (         )이다. 이 메서드는 첫 번째 인수로 교체할 대상 문자열을, 두 번째 인수로 새로운 문자열을 입력받는다.
', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (5, 4, 34, 'strip', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 문자열의 양쪽 끝에서 특정 문자를 제거하기 위해 사용하는 메서드는 (           )이다. 이 메서드는 기본적으로 공백을 제거하지만, 특정 문자를 인수로 전달하면 그 문자를 제거할 수도 있다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (5, 4, 35, 'RGAMN', null, null, null, null, '```
s = "Python Programming"
result = s[7:18:2].upper()
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (15, 4, 36, 'Python-Powerful', null, null, null, null, '"```
sentence = "Python is easy and powerful."
words = sentence.split(" ")
result = "-".join([word.capitalize() for word in words if word.startswith("p")])
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오."', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (4, 5, 37, 'else', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- if 문에서 조건이 참일 때 수행할 코드 블록이 실행되지 않는 경우, 해당 조건의 반대되는 조건이 참일 때 실행할 코드를 지정하기 위해 (          ) 키워드를 사용한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (4, 5, 38, 'elif', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 여러 개의 조건을 순차적으로 검사하여 각각의 조건에 따라 다른 코드를 실행하고 싶을 때, if 문과 else 문 사이에 (           ) 키워드를 사용하여 조건을 추가할 수 있다.
', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (1, 5, 39, 'C', null, null, null, null, '```
x = 15
y = 25
if x > 10:
    if y < 20:
        result = "A"
    elif y > 30:
        result = "B"
    else:
        result = "C"
else:
    result = "D"
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.
', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (1, 5, 40, 'Y', null, null, null, null, '```
x = 15
y = 25
if x > 10:
    if y < 20:
        result = "A"
    elif y > 30:
        result = "B"
    else:
        result = "C"
else:
    result = "D"
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.
', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (5, 6, 41, 'break', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- while 문이나 for 문에서 반복을 중단하고 빠져나가기 위해 사용하는 키워드는 (           )이다. 이 키워드가 실행되면 반복문의 남은 부분이 실행되지 않고 반복문 밖으로 빠져나온다.
', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (4, 6, 42, 'else', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- for 문이나 while 문이 정상적으로 끝까지 실행되었을 때, 특정 코드를 실행하고 싶다면 반복문에 (           )절을 추가할 수 있다.
', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (2, 6, 43, '15', null, null, null, null, '```
result = 0
for i in range(10):
    if i % 3 == 0:
        continue
    result += i
    if i == 7:
        break
else:
    result += 10
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.
', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (14, 6, 44, '[49, 121, 169]', null, null, null, null, '```
lst = [2, 3, 5, 7, 11, 13]
result = [x**2 for x in lst if x % 2 == 1 and x > 5]
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.
', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (3, 7, 45, 'def', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 함수를 정의할 때 사용되는 키워드는 (           )이다. 이 키워드 뒤에 함수 이름과 매개변수를 작성한 후, 함수 본문을 작성하여 원하는 작업을 수행한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (3, 7, 46, 'len', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.\\n\\n- (           ) 함수는 전달된 시퀀스(리스트, 튜플 등)의 길이를 반환한다. 이 함수는 문자열의 길이도 구할 수 있다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (2, 7, 47, '15', null, null, null, null, '```
def recursive_sum(n):
    if n == 1:
        return 1
    return n + recursive_sum(n - 1)

result = recursive_sum(5)
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (11, 7, 48, '[4, 64, 16]', null, null, null, null, '```
nums = [3, 7, 2, 8, 4]
result = list(map(lambda x: x**2, filter(lambda x: x % 2 == 0, nums)))
print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (6, 8, 49, 'append', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 리스트에 새로운 요소를 추가할 때 사용하는 메서드는 (           )이다. 이 메서드는 리스트의 끝에 요소를 하나 추가한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (9, 8, 50, 'TypeError', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 튜플은 불변의 자료형으로, 생성된 이후에는 요소를 변경할 수 없다. 만약 튜플 내의 요소를 변경하려고 시도하면 (           )이라는 에러가 발생한다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (6, 8, 51, '[5, 7]', null, null, null, null, '```
stack = []
stack.append(5)
stack.append(3)
stack.pop()
stack.append(7)
stack.append(8)
stack.pop()
print(stack)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (12, 8, 52, '[1, 2, 5, 6]', null, null, null, null, '```
a = {1, 2, 3, 4}
b = {3, 4, 5, 6}
result = a.symmetric_difference(b)
print(sorted(result))
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (6, 10, 53, 'except', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- 파이썬에서 코드 실행 중 오류가 발생하면 프로그램이 비정상 종료될 수 있다. 이를 방지하고자 오류를 처리하기 위해 사용하는 블록은 try이다. 이 블록 안에서 발생한 오류는 해당 블록에 이어지는 (            ) 블록에서 처리할 수 있다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (7, 10, 54, 'finally', null, null, null, null, '다음 괄호 안에 들어갈 알맞은 답을 적으세요.

- (            ) 블록은 예외가 발생하든 발생하지 않든 상관없이 반드시 실행되어야 하는 코드를 포함할 때 사용된다. 이 블록은 자원을 정리하거나 파일을 닫는 작업 등을 수행하는 데 유용하다.', 'EASY', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (41, 10, 55, 'Cannot divide by zero! Process completed.', null, null, null, null, '```
try:
    x = 10 / 0
except ZeroDivisionError:
    result = "Cannot divide by zero!"
except Exception as e:
    result = f"Error occurred: {e}"
else:
    result = "Division successful!"
finally:
    result += " Process completed."

print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (39, 10, 56, 'THIS IS A CUSTOM ERROR - Error handled.', null, null, null, null, '```
class CustomError(Exception):
    pass

try:
    raise CustomError("This is a custom error")
except CustomError as e:
    result = str(e).upper()
finally:
    result += " - Error handled."

print(result)
```

위 파이썬 코드의 실행 결과를 적으시오.', 'NORMAL', 'WORD');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 3, 57, null, null, null, null, null, '간단한 계산기

사용자가 두 개의 정수를 입력하면, 다음의 연산을 수행하는 간단한 계산기 프로그램을 작성하시오.

1. 두 정수의 합
2. 두 정수의 차
3. 두 정수의 곱
4. 두 정수의 몫과 나머지

입력:
- 두 개의 정수를 각각 한 줄에 입력받습니다.

출력:
- 입력된 두 정수의 합, 차, 곱, 몫과 나머지를 순서대로 출력합니다.

예제 입력 1:
```
10
3
```

예제 출력 1:
```
합: 13
차: 7
곱: 30
몫: 3, 나머지: 1
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 3, 58, null, null, null, null, null, '논리 연산자와 비교 연산자

사용자가 입력한 두 개의 값을 비교하고, 다음의 논리 연산을 수행하는 프로그램을 작성하시오.

1. 첫 번째 값이 두 번째 값보다 큰지 여부를 출력합니다.
2. 첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은지 여부를 출력합니다.
3. 두 값이 같지 않은지 여부를 출력합니다.

입력:
- 두 개의 값을 각각 한 줄에 입력받습니다.

출력:
- 첫 번째 값이 두 번째 값보다 큰지 여부 (True 또는 False)
- 첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은지 여부 (True 또는 False)
- 두 값이 같지 않은지 여부 (True 또는 False)

예제 입력 1:
```
5
-3
```

예제 출력 1:
```
첫 번째 값이 두 번째 값보다 큰가? True
첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? True
두 값이 같지 않은가? True
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 59, null, null, null, null, null, '문자열 조작 및 데이터 형 변환

사용자에게 영문 문자열과 숫자를 입력받아 다음과 같은 작업을 수행하는 프로그램을 작성하세요.

1. 입력받은 문자열의 첫 글자를 대문자로 변경하고, 나머지 글자는 소문자로 변경합니다.
2. 입력받은 숫자에 3을 더한 후, 실수로 변환하여 소수점 아래 2자리까지 출력합니다.
3. 변경된 문자열과 실수를 이용하여 다음과 같은 형식의 문자열을 만들어 출력합니다.

입력:
- 하나의 문자열
- 하나의 숫자

출력:
- "변경된 문자열은 {변경된 문자열}이고, 숫자는 {실수}입니다."
단, f-string을 사용하여 문자열을 포맷팅

예제 입력 1:
```
문자열: python
숫자: 5
```

예제 출력 1:
```
변경된 문자열은 Python이고, 숫자는 8.00입니다.
```

힌트:
- capitalize() 함수를 사용하여 문자열의 첫 글자를 대문자로 변경할 수 있습니다.
- float() 함수를 사용하여 정수를 실수로 변환할 수 있습니다.
- f-string을 사용하여 문자열 안에 변수의 값을 삽입할 수 있습니다.', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 4, 60, null, null, null, null, null, '대소문자 변환

사용자에게 영문 문자열을 입럭받아, \'a\'를 \'4\'로 바꾸고, \'e\'를 \'3\'으로 바꾸는 프로그램을 작성하시오.

입력:
- 사용자에게 영문 문자열을 입력받습니다.

출력:
- 입력받은 문자열의 모든 문자를 대문자로 변환하여 출력합니다.
- 이어서, 모든 문자를 소문자로 변환하여 출력합니다.

예제 입력 1:
```
Hello, World!
```

예제 출력 1:
```
HELLO, WORLD! hello, world!
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 61, null, null, null, null, null, '점수에 따른 학점 계산기

사용자가 입력한 점수에 따라 학점을 출력하는 프로그램을 작성하세요. 점수는 0에서 100 사이의 정수로 입력됩니다. 다음 규칙에 따라 학점을 출력하세요.

1. 90점 이상: A
2. 80점 이상 90점 미만: B
3. 70점 이상 80점 미만: C
4. 60점 이상 70점 미만: D
5. 60점 미만: F
6. 또한, 점수가 0 미만이거나 100을 초과하면 "잘못된 점수입니다."를 출력

입력 예제 1:
```
85
```

출력 예제 1:
```
B
```

입력 예제 2:
```
105
```

출력 예제 2:
```
잘못된 점수입니다.
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 5, 62, null, null, null, null, null, '세 수 중 가장 큰 수 찾기

사용자로부터 세 개의 정수를 입력받아, 이 중 가장 큰 수를 출력하는 프로그램을 작성하세요.

입력 예제 1:
```
입력된 세 정수: 10 20 15
```

출력 예제 1:
```
가장 큰 수: 20
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 63, null, null, null, null, null, '숫자 패턴 생성기

사용자가 입력한 정수 n에 따라 다음과 같은 패턴을 출력하는 함수를 작성하시오. 이 패턴은 숫자가 좌우 대칭을 이루며 출력됩니다.
예를 들어, n = 5일 때 다음과 같은 패턴이 출력되어야 합니다:

```
1
121
12321
1234321
123454321
```

입력:
- 하나의 정수 n을 입력받습니다. (1 ≤ n ≤ 9)

출력:
- n 줄에 걸쳐 위의 패턴을 출력합니다.

예제 입력 1:
```
5
```

예제 출력 1:
```
1
121
12321
1234321
123454321
```

예제 입력 2:
```
3
```

예제 출력 2:
```
1
121
12321
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 6, 64, null, null, null, null, null, '특정 숫자 제거하기

정수 리스트와 제거할 숫자를 입력받아, 입력받은 숫자를 리스트에서 모두 제거하고 남은 숫자를 오름차순으로 정렬하여 출력하는 프로그램을 작성하세요.

입력:
- 첫 번째 줄에 정수 리스트의 요소 개수 N이 주어진다.
두 번째 줄에 N개의 정수가 공백으로 구분되어 주어진다.
세 번째 줄에 제거할 숫자 X가 주어진다.

출력:
- 입력받은 숫자를 제거하고 정렬된 결과를 한 줄에 공백으로 구분하여 출력한다.

예제 입력 1:
```
5
10 20 30 40 50
30
```

예제 출력 1:
```
10 20 40 50
```

예제 입력 2:
```
4
1 2 3 4
5
```

예제 출력 2:
```
1 2 3 4
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 65, null, null, null, null, null, '문자열 암호화/복호화 함수 만들기

사용자로부터 문자열과 암호화 키를 입력받아, 각 문자를 키만큼 오른쪽으로 이동시켜 암호화하고, 다시 복호화하는 프로그램을 작성하세요.

1. 암호화: 각 문자의 아스키 코드 값에 키 값을 더하고, 255를 초과하면 255를 뺀 나머지 값으로 변환합니다.
2. 복호화: 암호화된 문자의 아스키 코드 값에서 키 값을 빼고, 0 미만이면 255를 더한 값으로 변환합니다.
3. 영문 대소문자와 공백만 처리합니다.

입력:
- 문자열: 암호화할 문자열
- 암호화 키: 정수 (1 이상)

출력:
- 암호화된 문자열
- 복호화된 문자열

입력 예제 1:
```
Hello, World!
3
```

출력 예제 1:
```
암호화된 문자열: Khoor, Zruog!
복호화된 문자열: Hello, World!
```

힌트:
- ord() 함수를 사용하여 문자를 아스키 코드 값으로 변환합니다.
- chr() 함수를 사용하여 아스키 코드 값을 문자로 변환합니다.
- for 문을 사용하여 문자열의 각 문자를 순회하며 암호화/복호화합니다.', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 7, 66, null, null, null, null, null, '문자열 조작 함수 만들기

사용자가 입력한 문자열에서 가장 자주 등장하는 문자와 그 빈도를 출력하는 함수를 작성하시오. 이때, 공백은 무시하고, 대소문자는 구분하지 않습니다.

입력:
- 하나의 문자열을 입력받습니다.

출력:
- 가장 자주 등장하는 문자와 그 빈도를 출력합니다.

제한 사항:
- 함수 내에서 문자열의 모든 문자에 대해 빈도를 계산할 것.
- 가장 자주 등장하는 문자가 여러 개일 경우, 알파벳 순서로 가장 앞에 있는 문자를 출력할 것.

입력 예제 1:
```
Hello World
```

출력 예제 1:
```
가장 자주 등장하는 문자: l
빈도: 3
```

입력 예제 2:
```
This is a Test
```

출력 예제 2:
```
가장 자주 등장하는 문자: s
빈도: 3
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 67, null, null, null, null, null, '큐와 스택을 이용한 문자열 처리

입력된 문자열에서 중복된 문자를 제거한 후, 남은 문자를 큐와 스택에 각각 넣고, 큐와 스택의 최종 상태를 반환하는 함수를 작성하시오. 큐는 처음부터 끝까지, 스택은 끝부터 처음까지 순서대로 문자를 넣습니다.

입력:
- 하나의 문자열 s를 입력 (1 ≤ 문자열 길이 ≤ 100)

출력:
- 중복된 문자를 제거한 후, 큐와 스택의 최종 상태를 리스트로 반환

입력 예제 1:
```
abacabad
```

출력 예제 1:
```
큐: [\'a\', \'b\', \'c\', \'d\']
스택: [\'d\', \'c\', \'b\', \'a\']
```

입력 예제 2:
```
abcabcabc
```

출력 예제 2:
```
큐: [\'a\', \'b\', \'c\']
스택: [\'c\', \'b\', \'a\']
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 8, 68, null, null, null, null, null, '튜플과 딕셔너리를 이용한 단어 카운트

입력된 문자열에서 각 단어의 빈도를 계산하고, 이를 (단어, 빈도) 형식의 튜플로 저장한 뒤, 단어의 빈도가 높은 순서대로 출력하는 프로그램을 작성하시오. 단, 빈도가 같은 단어는 알파벳 순으로 정렬합니다.

입력:
- 하나의 문자열 s를 입력 (1 ≤ 문자열 길이 ≤ 200)

출력:
- (단어, 빈도) 형식의 튜플을 빈도가 높은 순서대로 출력

입력 예제 1:
```
The quick brown fox jumps over the lazy dog The dog
```

출력 예제 1:
```
[(\'the\', 3), (\'dog\', 2), (\'brown\', 1), (\'fox\', 1), (\'jumps\', 1), (\'lazy\', 1), (\'over\', 1), (\'quick\', 1)]
```

입력 예제 2:
```
Python is great and Python is fun
```

출력 예제 2:
```
[(\'python\', 2), (\'is\', 2), (\'and\', 1), (\'fun\', 1), (\'great\', 1)]
```

제한 사항:
- 단어의 구분은 공백을 기준으로 합니다.
- 대소문자는 구분하지 않으며, 출력할 때는 모두 소문자로 변환합니다.', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 69, null, null, null, null, null, '사용자 정의 예외 처리

온라인 쇼핑몰의 결제 시스템을 개발하는 중입니다. 결제 금액을 입력받아 결제를 처리하는 프로그램을 작성하세요. 프로그램은 다음 조건을 만족해야 합니다.

1. 결제 금액이 0 이하일 경우 "결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요."라는 메시지를 출력하고 프로그램을 종료합니다.
2. 입력된 금액이 숫자가 아닌 경우 ValueError를 처리하여 "잘못된 입력입니다. 숫자를 입력하세요."라는 메시지를 출력합니다.
3. 결제 금액이 올바르면 "결제가 완료되었습니다."와 남은 잔액을 출력합니다.
4. 모든 결제 과정이 끝나면 "결제 시스템을 종료합니다."라는 메시지를 출력합니다.
5. 초기 금액은 10000으로 설정합니다.

입력 예제 1:
```
1000
```

출력 예제 1:
```
결제가 완료되었습니다.
남은 잔액은 9000원입니다.
결제 시스템을 종료합니다.
```

입력 예제 2:
```
-500
```

출력 예제 2:
```
결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요.
결제 시스템을 종료합니다.
```

입력 예제 3:
```
ABC
```

출력 예제 3:
```
잘못된 입력입니다. 숫자를 입력하세요.
결제 시스템을 종료합니다.
```', 'HARD', 'CODE');
INSERT INTO bluecode.quiz (word_count, curriculum_id, quiz_id, answer, q1, q2, q3, q4, text, level, quiz_type) VALUES (0, 10, 70, null, null, null, null, null, '예외 처리와 사용자 정의 예외 구현하기

당신은 예외 처리를 이용해 안정적인 프로그램을 작성해야 하는 개발자입니다. 주어진 함수 \'process_data\'는 두 개의 정수를 입력받아 다음의 규칙에 따라 처리합니다:

1. 첫 번째 정수를 두 번째 정수로 나누어 그 결과를 반환합니다.
2. 두 번째 정수가 0일 경우, \'ZeroDivisionError\' 예외를 발생시킵니다.
3. 두 번째 정수가 음수일 경우, \'NegativeValueError\'라는 사용자 정의 예외를 발생시킵니다.
4. 이 외의 예외가 발생하면, 이를 처리하여 ""Error occurred"" 메시지를 출력합니다.
5. 나눗셈이 성공적으로 완료되면, 결과를 출력하고, 마지막으로 ""Process complete""라는 메시지를 출력합니다.

사용자 정의 예외 \'NegativeValueError\'를 직접 구현하고, 위의 요구 사항을 만족하는 \'process_data\' 함수를 작성하세요.

입력:

- 두 개의 정수 \'a\'와 \'b\'가 공백으로 구분되어 한 줄로 주어집니다.

출력:

- 나눗셈 결과가 성공적으로 계산되면 그 결과를 출력합니다.
- 예외가 발생할 경우, 해당 예외에 맞는 메시지를 출력합니다.
- 항상 마지막에는 ""Process complete"" 메시지를 출력합니다.

입력 예제 1:
```
10 2
```

출력 예제 1:
```
5.0
Process complete
```

입력 예제 2:
```
10 0
```

출력 예제 2:
```
division by zero
Process complete
```', 'HARD', 'CODE');

-- quiz_case
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (57, '-5
2
', '합: -3
차: -7
곱: -10
몫: -2, 나머지: -1');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (57, '100
0
', '합: 100
차: 100
곱: 0
몫: 오류: 0으로 나눌 수 없습니다.
나머지: 오류: 0으로 나눌 수 없습니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (58, '0
10
', '첫 번째 값이 두 번째 값보다 큰가? False
첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? False
두 값이 같지 않은가? True');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (58, '5
5
', '첫 번째 값이 두 번째 값보다 큰가? False
첫 번째 값이 0이 아니고, 두 번째 값이 0보다 작은가? False
두 값이 같지 않은가? False');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (59, 'HELLO WORLD
-2
', '변경된 문자열은 Hello world이고, 숫자는 1.00입니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (59, '123abc
0
', '변경된 문자열은 123abc이고, 숫자는 3.00입니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (60, 'Programming is fun!
', 'PROGRAMMING IS FUN! programming is fun!
');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (60, 'a quick brown fox jumps over the lazy dog
', 'A QUICK BROWN FOX JUMPS OVER THE LAZY DOG a quick brown fox jumps over the lazy dog');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (61, '59
', 'F');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (61, '-10
', '잘못된 점수입니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (62, '5 5 10
', '가장 큰 수: 10');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (62, '-3 -7 -2
', '가장 큰 수: -2
');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (63, '2
', '1
121
');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (63, '9
', '1
121
12321
1234321
123454321
12345654321
1234567654321
123456787654321
12345678987654321
');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (64, '3
10 20 10
10
', '20');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (64, '7
3 1 4 1 5 9 2
1
', '2 3 4 5 9
');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (65, 'This is a secret message.
13
', 'Guvf vf n frperg zrnag.
복호화된 문자열: This is a secret message.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (65, 'A-Z a-z 0123456789
1
', 'B-A b-y 1234567890
복호화된 문자열:  A-Z a-z 0123456789');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (66, 'aabbccddeeff
', '가장 자주 등장하는 문자: a, 빈도: 2');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (66, 'Mississipi
', '가장 자주 등장하는 문자: i, 빈도: 4');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (67, 'google', '큐: [\'g\', \'o\', \'l\', \'e\']
스택: [\'e\', \'l\', \'o\', \'g\']');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (67, 'mississippi', '큐: [\'m\', \'i\', \'s\', \'p\']
스택: [\'p\', \'s\', \'i\', \'m\']');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (68, 'Python is a programming language. Python is fun! Isn\'t it?', '[(\'python\', 2), (\'is\', 3), (\'a\', 1), (\'programming\', 1), (\'language\', 1), (\'fun\', 1), (\'isn\', 1), (\'it\', 1)]');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (68, 'To be or not to be, that is the question. Whether \'tis nobler in the mind to suffer The slings and arrows of outrageous fortune, Or to take arms against a sea of troubles, And by opposing end them? To die: to sleep; No more; and by a sleep to say we end The heart-ache and the thousand natural shocks That flesh is heir to, \'tis a consummation Devoutly to be wish\'d. To die, to sleep; To sleep: perchance to dream: ay, there\'s the rub; For in that sleep of death what dreams may come When we have shuffled off this mortal coil, Must give us pause: there\'s the respect That makes calamity of so long life', '[(\'to\', 6), (\'the\', 3), (\'and\', 3), (\'be\', 3), (\'of\', 3), (\'a\', 2), (\'in\', 2), (\'sleep\', 2), (\'that\', 2), (\'is\', 2), (\'or\', 2), (\'for\', 1), (\'not\', 1), (\'whether\', 1), (\'tis\', 1), (\'nobler\', 1), (\'mind\', 1), (\'suffer\', 1), (\'slings\', 1), (\'arrows\', 1), (\'outrageous\', 1), (\'fortune\', 1), (\'take\', 1), (\'arms\', 1), (\'against\', 1), (\'sea\', 1), (\'troubles\', 1), (\'by\', 1), (\'opposing\', 1), (\'end\', 1), (\'them\', 1), (\'die\', 2), (\'more\', 1), (\'say\', 1), (\'we\', 1), (\'heart\', 1), (\'ache\', 1), (\'thousand\', 1), (\'natural\', 1), (\'shocks\', 1), (\'flesh\', 1), (\'heir\', 1), (\'consummation\', 1), (\'devoutly\', 1), (\'wishd\', 1), (\'perchance\', 1), (\'dream\', 1), (\'ay\', 1), (\'theres\', 2), (\'rub\', 1), (\'when\', 1), (\'have\', 1), (\'shuffled\', 1), (\'off\', 1), (\'this\', 1), (\'mortal\', 1), (\'coil\', 1), (\'must\', 1), (\'give\', 1), (\'us\', 1), (\'pause\', 1), (\'respect\', 1), (\'makes\', 1), (\'calamity\', 1), (\'so\', 1), (\'long\', 1), (\'life\', 1)]');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (69, '15000
', '잔액이 부족합니다.
결제 시스템을 종료합니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (69, '0
', '결제 금액이 잘못되었습니다. 0 이상의 금액을 입력하세요.
결제 시스템을 종료합니다.');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (70, '10 -5
', 'Negative value encountered
Process complete');
INSERT INTO bluecode.quiz_case (quiz_id, input, output) VALUES (70, 'ten 5
', 'Error occurred
Process complete');

-- user
INSERT INTO bluecode.users (exp, init_test, streak_count, tier, recent_access, register_date_time, user_id, birth, email, id, password, username) VALUES (0, false, 0, 0, null, '2024-09-02 18:30:57.132946', 1, '20000101', 'testEmail', 'testId', '$2a$10$MaeuD5HlZ7SjM1vrn/dZS.6PC4wlvm71AYJiytTE/N5PipEv5a.nO', 'testName');
INSERT INTO bluecode.users (exp, init_test, streak_count, tier, recent_access, register_date_time, user_id, birth, email, id, password, username) VALUES (0, false, 0, 0, null, '2024-09-02 18:30:57.135948', 2, '20201212', 'testEmail2', 'testId2', '$2a$10$MaeuD5HlZ7SjM1vrn/dZS.6PC4wlvm71AYJiytTE/N5PipEv5a.nO', 'testName2');

-- chat
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.523834', 12, 2, '챕터 1 - 서브챕터 1 에서의 단일 답변', '챕터 1 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.525832', 12, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.526838', 12, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.527834', 12, 2, '챕터 1 - 서브챕터 1 에서의 단일 답변', '챕터 1 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.528834', 12, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.529887', 12, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.530393', 13, 2, '챕터 1 - 서브챕터 2 에서의 단일 답변', '챕터 1 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.530393', 13, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.531402', 13, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.532402', 13, 2, '챕터 1 - 서브챕터 2 에서의 단일 답변', '챕터 1 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.532402', 13, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.533401', 13, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.533401', 14, 2, '챕터 1 - 서브챕터 3 에서의 단일 답변', '챕터 1 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.534401', 14, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.535403', 14, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.535403', 14, 2, '챕터 1 - 서브챕터 3 에서의 단일 답변', '챕터 1 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.536404', 14, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.537405', 14, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.538403', 15, 2, '챕터 1 - 서브챕터 4 에서의 단일 답변', '챕터 1 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.538403', 15, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.539402', 15, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.540402', 15, 2, '챕터 1 - 서브챕터 4 에서의 단일 답변', '챕터 1 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.540402', 15, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.541403', 15, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.541403', 16, 2, '챕터 1 - 서브챕터 5 에서의 단일 답변', '챕터 1 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.542404', 16, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.543402', 16, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.543402', 16, 2, '챕터 1 - 서브챕터 5 에서의 단일 답변', '챕터 1 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.544402', 16, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.545407', 16, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.545407', 17, 2, '챕터 1 - 서브챕터 6 에서의 단일 답변', '챕터 1 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.545407', 17, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.546831', 17, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.547840', 17, 2, '챕터 1 - 서브챕터 6 에서의 단일 답변', '챕터 1 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.548849', 17, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.549841', 17, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.550845', 18, 2, '챕터 1 - 서브챕터 7 에서의 단일 답변', '챕터 1 - 서브챕터 7 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.550845', 18, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 7 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.552069', 18, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 7 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.553081', 18, 2, '챕터 1 - 서브챕터 7 에서의 단일 답변', '챕터 1 - 서브챕터 7 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.554084', 18, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 7 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.554084', 18, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 7 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.555152', 19, 2, '챕터 1 - 서브챕터 8 에서의 단일 답변', '챕터 1 - 서브챕터 8 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.556214', 19, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 8 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.556727', 19, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 8 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.557738', 19, 2, '챕터 1 - 서브챕터 8 에서의 단일 답변', '챕터 1 - 서브챕터 8 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.558736', 19, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 8 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.558736', 19, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 8 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.559894', 20, 2, '챕터 1 - 서브챕터 9 에서의 단일 답변', '챕터 1 - 서브챕터 9 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.559894', 20, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 9 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.560948', 20, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 9 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.560948', 20, 2, '챕터 1 - 서브챕터 9 에서의 단일 답변', '챕터 1 - 서브챕터 9 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.561959', 20, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 9 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.562962', 20, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 9 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.563965', 21, 2, '챕터 1 - 서브챕터 10 에서의 단일 답변', '챕터 1 - 서브챕터 10 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.563965', 21, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 10 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.564958', 21, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 10 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.564958', 21, 2, '챕터 1 - 서브챕터 10 에서의 단일 답변', '챕터 1 - 서브챕터 10 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.565961', 21, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 10 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.566964', 21, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 10 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.568375', 22, 2, '챕터 1 - 서브챕터 11 에서의 단일 답변', '챕터 1 - 서브챕터 11 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.569448', 22, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 11 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.570445', 22, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 11 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.571445', 22, 2, '챕터 1 - 서브챕터 11 에서의 단일 답변', '챕터 1 - 서브챕터 11 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.572443', 22, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 11 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.572443', 22, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 11 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.573440', 23, 2, '챕터 1 - 서브챕터 12 에서의 단일 답변', '챕터 1 - 서브챕터 12 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.574452', 23, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 12 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.575444', 23, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 12 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.575444', 23, 2, '챕터 1 - 서브챕터 12 에서의 단일 답변', '챕터 1 - 서브챕터 12 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.576442', 23, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 12 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.577442', 23, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 12 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.581443', 24, 2, '챕터 1 - 서브챕터 13 에서의 단일 답변', '챕터 1 - 서브챕터 13 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.582442', 24, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 13 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.583446', 24, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 13 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.584613', 24, 2, '챕터 1 - 서브챕터 13 에서의 단일 답변', '챕터 1 - 서브챕터 13 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.584613', 24, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 1 - 서브챕터 13 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.585622', 24, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 1 - 서브챕터 13 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.586624', 25, 2, '챕터 2 - 서브챕터 1 에서의 단일 답변', '챕터 2 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.587627', 25, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.587627', 25, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.588624', 25, 2, '챕터 2 - 서브챕터 1 에서의 단일 답변', '챕터 2 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.589625', 25, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.591237', 25, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.592252', 26, 2, '챕터 2 - 서브챕터 2 에서의 단일 답변', '챕터 2 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.593253', 26, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.594254', 26, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.595247', 26, 2, '챕터 2 - 서브챕터 2 에서의 단일 답변', '챕터 2 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.595247', 26, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.596245', 26, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.597316', 27, 2, '챕터 2 - 서브챕터 3 에서의 단일 답변', '챕터 2 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.597316', 27, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.598326', 27, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.599445', 27, 2, '챕터 2 - 서브챕터 3 에서의 단일 답변', '챕터 2 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.600461', 27, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.601455', 27, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.602453', 28, 2, '챕터 2 - 서브챕터 4 에서의 단일 답변', '챕터 2 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.602453', 28, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.603462', 28, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.604456', 28, 2, '챕터 2 - 서브챕터 4 에서의 단일 답변', '챕터 2 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.604456', 28, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.605562', 28, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.605562', 29, 2, '챕터 2 - 서브챕터 5 에서의 단일 답변', '챕터 2 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.606574', 29, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.607577', 29, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.607577', 29, 2, '챕터 2 - 서브챕터 5 에서의 단일 답변', '챕터 2 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.608585', 29, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.612737', 29, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.613738', 30, 2, '챕터 2 - 서브챕터 6 에서의 단일 답변', '챕터 2 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.614742', 30, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.615737', 30, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.616737', 30, 2, '챕터 2 - 서브챕터 6 에서의 단일 답변', '챕터 2 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.617781', 30, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.617781', 30, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.618793', 31, 2, '챕터 2 - 서브챕터 7 에서의 단일 답변', '챕터 2 - 서브챕터 7 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.619798', 31, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 7 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.625347', 31, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 7 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.628437', 31, 2, '챕터 2 - 서브챕터 7 에서의 단일 답변', '챕터 2 - 서브챕터 7 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.630737', 31, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 7 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.631755', 31, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 7 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.632883', 32, 2, '챕터 2 - 서브챕터 8 에서의 단일 답변', '챕터 2 - 서브챕터 8 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.632883', 32, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 8 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.633964', 32, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 8 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.634960', 32, 2, '챕터 2 - 서브챕터 8 에서의 단일 답변', '챕터 2 - 서브챕터 8 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.635963', 32, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 8 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.636956', 32, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 8 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.638048', 33, 2, '챕터 2 - 서브챕터 9 에서의 단일 답변', '챕터 2 - 서브챕터 9 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.640068', 33, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 9 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.642067', 33, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 9 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.643071', 33, 2, '챕터 2 - 서브챕터 9 에서의 단일 답변', '챕터 2 - 서브챕터 9 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.643071', 33, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 2 - 서브챕터 9 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.644067', 33, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 2 - 서브챕터 9 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.645066', 34, 2, '챕터 3 - 서브챕터 1 에서의 단일 답변', '챕터 3 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.645066', 34, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 3 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.646069', 34, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 3 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.647068', 34, 2, '챕터 3 - 서브챕터 1 에서의 단일 답변', '챕터 3 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.647068', 34, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 3 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.648069', 34, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 3 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.652473', 35, 2, '챕터 3 - 서브챕터 2 에서의 단일 답변', '챕터 3 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.653508', 35, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 3 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.654590', 35, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 3 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.654590', 35, 2, '챕터 3 - 서브챕터 2 에서의 단일 답변', '챕터 3 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.655602', 35, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 3 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.656597', 35, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 3 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.656597', 36, 2, '챕터 4 - 서브챕터 1 에서의 단일 답변', '챕터 4 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.657772', 36, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.658282', 36, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.658282', 36, 2, '챕터 4 - 서브챕터 1 에서의 단일 답변', '챕터 4 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.658282', 36, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.659292', 36, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.660298', 37, 2, '챕터 4 - 서브챕터 2 에서의 단일 답변', '챕터 4 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.660298', 37, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.661291', 37, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.661291', 37, 2, '챕터 4 - 서브챕터 2 에서의 단일 답변', '챕터 4 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.662296', 37, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.662296', 37, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.663297', 38, 2, '챕터 4 - 서브챕터 3 에서의 단일 답변', '챕터 4 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.663297', 38, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.664293', 38, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.664293', 38, 2, '챕터 4 - 서브챕터 3 에서의 단일 답변', '챕터 4 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.665289', 38, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.665289', 38, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.666294', 39, 2, '챕터 4 - 서브챕터 4 에서의 단일 답변', '챕터 4 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.666294', 39, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.667292', 39, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.667292', 39, 2, '챕터 4 - 서브챕터 4 에서의 단일 답변', '챕터 4 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.668293', 39, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.668293', 39, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.669292', 40, 2, '챕터 4 - 서브챕터 5 에서의 단일 답변', '챕터 4 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.669292', 40, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.670291', 40, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.670291', 40, 2, '챕터 4 - 서브챕터 5 에서의 단일 답변', '챕터 4 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.671289', 40, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 4 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.671289', 40, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 4 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.672291', 41, 2, '챕터 5 - 서브챕터 1 에서의 단일 답변', '챕터 5 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.672291', 41, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.673290', 41, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.673290', 41, 2, '챕터 5 - 서브챕터 1 에서의 단일 답변', '챕터 5 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.676289', 41, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.677295', 41, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.677295', 42, 2, '챕터 5 - 서브챕터 2 에서의 단일 답변', '챕터 5 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.678291', 42, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.678291', 42, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.679293', 42, 2, '챕터 5 - 서브챕터 2 에서의 단일 답변', '챕터 5 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.679293', 42, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.680290', 42, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.680290', 43, 2, '챕터 5 - 서브챕터 3 에서의 단일 답변', '챕터 5 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.681295', 43, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.681295', 43, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.682291', 43, 2, '챕터 5 - 서브챕터 3 에서의 단일 답변', '챕터 5 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.682291', 43, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.683295', 43, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.683295', 44, 2, '챕터 5 - 서브챕터 4 에서의 단일 답변', '챕터 5 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.683295', 44, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.684295', 44, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.684295', 44, 2, '챕터 5 - 서브챕터 4 에서의 단일 답변', '챕터 5 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.685290', 44, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.685290', 44, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.686291', 45, 2, '챕터 5 - 서브챕터 5 에서의 단일 답변', '챕터 5 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.686291', 45, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.687289', 45, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.687289', 45, 2, '챕터 5 - 서브챕터 5 에서의 단일 답변', '챕터 5 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.688296', 45, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.688296', 45, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.689291', 46, 2, '챕터 5 - 서브챕터 6 에서의 단일 답변', '챕터 5 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.689291', 46, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.690293', 46, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.690293', 46, 2, '챕터 5 - 서브챕터 6 에서의 단일 답변', '챕터 5 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.691296', 46, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.691296', 46, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.692291', 47, 2, '챕터 5 - 서브챕터 7 에서의 단일 답변', '챕터 5 - 서브챕터 7 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.692291', 47, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 7 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.693289', 47, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 7 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.693289', 47, 2, '챕터 5 - 서브챕터 7 에서의 단일 답변', '챕터 5 - 서브챕터 7 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.693289', 47, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 7 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.694292', 47, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 7 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.694292', 48, 2, '챕터 5 - 서브챕터 8 에서의 단일 답변', '챕터 5 - 서브챕터 8 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.695294', 48, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 8 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.695294', 48, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 8 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.696293', 48, 2, '챕터 5 - 서브챕터 8 에서의 단일 답변', '챕터 5 - 서브챕터 8 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.696293', 48, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 8 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.697295', 48, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 8 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.697295', 49, 2, '챕터 5 - 서브챕터 9 에서의 단일 답변', '챕터 5 - 서브챕터 9 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.698294', 49, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 9 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.698294', 49, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 9 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.699290', 49, 2, '챕터 5 - 서브챕터 9 에서의 단일 답변', '챕터 5 - 서브챕터 9 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.699290', 49, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 9 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.700304', 49, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 9 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.700304', 50, 2, '챕터 5 - 서브챕터 10 에서의 단일 답변', '챕터 5 - 서브챕터 10 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.701292', 50, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 10 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.701292', 50, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 10 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.702291', 50, 2, '챕터 5 - 서브챕터 10 에서의 단일 답변', '챕터 5 - 서브챕터 10 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.702291', 50, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 10 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.703293', 50, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 10 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.703293', 51, 2, '챕터 5 - 서브챕터 11 에서의 단일 답변', '챕터 5 - 서브챕터 11 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.704298', 51, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 11 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.704298', 51, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 11 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.705293', 51, 2, '챕터 5 - 서브챕터 11 에서의 단일 답변', '챕터 5 - 서브챕터 11 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.705293', 51, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 11 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.706714', 51, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 11 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.706714', 52, 2, '챕터 5 - 서브챕터 12 에서의 단일 답변', '챕터 5 - 서브챕터 12 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.707728', 52, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 12 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.707728', 52, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 12 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.708742', 52, 2, '챕터 5 - 서브챕터 12 에서의 단일 답변', '챕터 5 - 서브챕터 12 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.708742', 52, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 5 - 서브챕터 12 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.709742', 52, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 5 - 서브챕터 12 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.709742', 53, 2, '챕터 6 - 서브챕터 1 에서의 단일 답변', '챕터 6 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.710739', 53, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.710739', 53, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.714734', 53, 2, '챕터 6 - 서브챕터 1 에서의 단일 답변', '챕터 6 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.714734', 53, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.715739', 53, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.715739', 54, 2, '챕터 6 - 서브챕터 2 에서의 단일 답변', '챕터 6 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.716739', 54, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.716739', 54, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.717736', 54, 2, '챕터 6 - 서브챕터 2 에서의 단일 답변', '챕터 6 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.717736', 54, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.718739', 54, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.718739', 55, 2, '챕터 6 - 서브챕터 3 에서의 단일 답변', '챕터 6 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.719735', 55, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.719735', 55, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.720736', 55, 2, '챕터 6 - 서브챕터 3 에서의 단일 답변', '챕터 6 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.720736', 55, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.720736', 55, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.721737', 56, 2, '챕터 6 - 서브챕터 4 에서의 단일 답변', '챕터 6 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.721737', 56, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.722737', 56, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.723245', 56, 2, '챕터 6 - 서브챕터 4 에서의 단일 답변', '챕터 6 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.723245', 56, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.724252', 56, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.724252', 57, 2, '챕터 6 - 서브챕터 5 에서의 단일 답변', '챕터 6 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.724252', 57, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.725253', 57, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.725253', 57, 2, '챕터 6 - 서브챕터 5 에서의 단일 답변', '챕터 6 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.726254', 57, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.726254', 57, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.727254', 58, 2, '챕터 6 - 서브챕터 6 에서의 단일 답변', '챕터 6 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.727254', 58, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.728260', 58, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.728260', 58, 2, '챕터 6 - 서브챕터 6 에서의 단일 답변', '챕터 6 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.728260', 58, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 6 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.729254', 58, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 6 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.729254', 59, 2, '챕터 7 - 서브챕터 1 에서의 단일 답변', '챕터 7 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.730257', 59, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.730257', 59, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.731253', 59, 2, '챕터 7 - 서브챕터 1 에서의 단일 답변', '챕터 7 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.731253', 59, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.732254', 59, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.732254', 60, 2, '챕터 7 - 서브챕터 2 에서의 단일 답변', '챕터 7 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.733258', 60, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.733258', 60, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.733258', 60, 2, '챕터 7 - 서브챕터 2 에서의 단일 답변', '챕터 7 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.734255', 60, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.734255', 60, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.735259', 61, 2, '챕터 7 - 서브챕터 3 에서의 단일 답변', '챕터 7 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.735259', 61, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.736253', 61, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.736253', 61, 2, '챕터 7 - 서브챕터 3 에서의 단일 답변', '챕터 7 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.737254', 61, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.737254', 61, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.738259', 62, 2, '챕터 7 - 서브챕터 4 에서의 단일 답변', '챕터 7 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.738259', 62, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.738259', 62, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.739254', 62, 2, '챕터 7 - 서브챕터 4 에서의 단일 답변', '챕터 7 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.739254', 62, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.740260', 62, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.740260', 63, 2, '챕터 7 - 서브챕터 5 에서의 단일 답변', '챕터 7 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.741259', 63, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.741259', 63, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.742253', 63, 2, '챕터 7 - 서브챕터 5 에서의 단일 답변', '챕터 7 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.742253', 63, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.743339', 63, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.743847', 64, 2, '챕터 7 - 서브챕터 6 에서의 단일 답변', '챕터 7 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.743847', 64, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.744858', 64, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.744858', 64, 2, '챕터 7 - 서브챕터 6 에서의 단일 답변', '챕터 7 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.745857', 64, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 7 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.745857', 64, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 7 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.746861', 65, 2, '챕터 8 - 서브챕터 1 에서의 단일 답변', '챕터 8 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.746861', 65, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.751856', 65, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.752855', 65, 2, '챕터 8 - 서브챕터 1 에서의 단일 답변', '챕터 8 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.753858', 65, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.753858', 65, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.754862', 66, 2, '챕터 8 - 서브챕터 2 에서의 단일 답변', '챕터 8 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.755857', 66, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.755857', 66, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.756859', 66, 2, '챕터 8 - 서브챕터 2 에서의 단일 답변', '챕터 8 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.756859', 66, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.757856', 66, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.757856', 67, 2, '챕터 8 - 서브챕터 3 에서의 단일 답변', '챕터 8 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.758855', 67, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.758855', 67, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.759859', 67, 2, '챕터 8 - 서브챕터 3 에서의 단일 답변', '챕터 8 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.759859', 67, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.760861', 67, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.761857', 68, 2, '챕터 8 - 서브챕터 4 에서의 단일 답변', '챕터 8 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.761857', 68, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.762859', 68, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.762859', 68, 2, '챕터 8 - 서브챕터 4 에서의 단일 답변', '챕터 8 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.763856', 68, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 8 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.763856', 68, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 8 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.765023', 69, 2, '챕터 9 - 서브챕터 1 에서의 단일 답변', '챕터 9 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.765023', 69, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.766038', 69, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.766038', 69, 2, '챕터 9 - 서브챕터 1 에서의 단일 답변', '챕터 9 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.766038', 69, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.767037', 69, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.767037', 70, 2, '챕터 9 - 서브챕터 2 에서의 단일 답변', '챕터 9 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.768033', 70, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.768033', 70, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.769039', 70, 2, '챕터 9 - 서브챕터 2 에서의 단일 답변', '챕터 9 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.769039', 70, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.770040', 70, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.770040', 71, 2, '챕터 9 - 서브챕터 3 에서의 단일 답변', '챕터 9 - 서브챕터 3 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.771037', 71, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 3 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.771037', 71, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 3 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.771037', 71, 2, '챕터 9 - 서브챕터 3 에서의 단일 답변', '챕터 9 - 서브챕터 3 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.772035', 71, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 3 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.772035', 71, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 3 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.773040', 72, 2, '챕터 9 - 서브챕터 4 에서의 단일 답변', '챕터 9 - 서브챕터 4 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.774039', 72, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 4 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.774039', 72, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 4 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.775038', 72, 2, '챕터 9 - 서브챕터 4 에서의 단일 답변', '챕터 9 - 서브챕터 4 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.775038', 72, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 4 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.776038', 72, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 4 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.776038', 73, 2, '챕터 9 - 서브챕터 5 에서의 단일 답변', '챕터 9 - 서브챕터 5 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.777038', 73, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 5 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.777038', 73, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 5 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.777038', 73, 2, '챕터 9 - 서브챕터 5 에서의 단일 답변', '챕터 9 - 서브챕터 5 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.778547', 73, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 5 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.778547', 73, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 5 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.778547', 74, 2, '챕터 9 - 서브챕터 6 에서의 단일 답변', '챕터 9 - 서브챕터 6 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.779836', 74, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 6 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.780349', 74, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 6 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.780349', 74, 2, '챕터 9 - 서브챕터 6 에서의 단일 답변', '챕터 9 - 서브챕터 6 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.781357', 74, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 6 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.781357', 74, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 6 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.782452', 75, 2, '챕터 9 - 서브챕터 7 에서의 단일 답변', '챕터 9 - 서브챕터 7 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.782452', 75, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 7 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.783462', 75, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 7 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.783462', 75, 2, '챕터 9 - 서브챕터 7 에서의 단일 답변', '챕터 9 - 서브챕터 7 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.784694', 75, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 7 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.785743', 75, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 7 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.786252', 76, 2, '챕터 9 - 서브챕터 8 에서의 단일 답변', '챕터 9 - 서브챕터 8 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.786252', 76, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 8 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.787264', 76, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 8 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.787264', 76, 2, '챕터 9 - 서브챕터 8 에서의 단일 답변', '챕터 9 - 서브챕터 8 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.788366', 76, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 8 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.788366', 76, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 8 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.788366', 77, 2, '챕터 9 - 서브챕터 9 에서의 단일 답변', '챕터 9 - 서브챕터 9 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.796600', 77, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 9 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.797602', 77, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 9 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.798604', 77, 2, '챕터 9 - 서브챕터 9 에서의 단일 답변', '챕터 9 - 서브챕터 9 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.799608', 77, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 9 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.799608', 77, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 9 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.800599', 78, 2, '챕터 9 - 서브챕터 10 에서의 단일 답변', '챕터 9 - 서브챕터 10 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.800599', 78, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 10 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.801636', 78, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 10 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.801636', 78, 2, '챕터 9 - 서브챕터 10 에서의 단일 답변', '챕터 9 - 서브챕터 10 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.801636', 78, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 9 - 서브챕터 10 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.803012', 78, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 9 - 서브챕터 10 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.803012', 79, 2, '챕터 10 - 서브챕터 1 에서의 단일 답변', '챕터 10 - 서브챕터 1 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.804024', 79, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 10 - 서브챕터 1 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.804024', 79, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 10 - 서브챕터 1 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.804024', 79, 2, '챕터 10 - 서브챕터 1 에서의 단일 답변', '챕터 10 - 서브챕터 1 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.805164', 79, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 10 - 서브챕터 1 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.805164', 79, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 10 - 서브챕터 1 에서의 질문 2: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.806177', 80, 2, '챕터 10 - 서브챕터 2 에서의 단일 답변', '챕터 10 - 서브챕터 2 에서의 질문 1: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.806177', 80, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 10 - 서브챕터 2 에서의 질문 1: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.807173', 80, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 10 - 서브챕터 2 에서의 질문 1: ERRORS', 'ERRORS');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (1, '2024-09-11 16:22:21.807173', 80, 2, '챕터 10 - 서브챕터 2 에서의 단일 답변', '챕터 10 - 서브챕터 2 에서의 질문 2: DEF', 'DEF');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.808176', 80, 2, '1단계: 코드 단계적 답변$2단계: 코드 단계적 답변$3단계: 코드 단계적 답변$4단계: 코드 단계적 답변', '챕터 10 - 서브챕터 2 에서의 질문 2: CODE', 'CODE');
INSERT INTO bluecode.chats (level, chat_date, curriculum_id, user_id, answer, question, question_type) VALUES (3, '2024-09-11 16:22:21.808176', 80, 2, '1단계: 에러 단계적 답변$2단계: 에러 단계적 답변$3단계: 에러 단계적 답변$4단계: 에러 단계적 답변', '챕터 10 - 서브챕터 2 에서의 질문 2: ERRORS', 'ERRORS');

-- studies
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 1, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 2, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 3, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 4, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 5, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 6, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 7, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 8, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 9, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 10, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 11, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 12, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 13, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 14, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 15, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 16, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 17, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 18, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 19, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 20, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 21, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 22, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 23, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 24, null, 2, null, null, null, 'EASY');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 25, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 26, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 27, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 28, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 29, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 30, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 31, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 32, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (true, '2024-09-11', 33, null, 2, null, null, null, 'HARD');
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 34, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 35, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 36, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 37, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 38, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 39, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 40, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 41, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 42, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 43, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 44, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 45, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 46, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 47, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 48, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 49, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 50, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 51, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 52, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 53, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 54, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 55, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 56, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 57, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 58, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 59, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 60, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 61, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 62, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 63, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 64, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 65, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 66, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 67, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 68, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 69, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 70, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 71, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 72, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 73, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 74, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 75, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 76, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 77, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 78, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 79, null, 2, null, null, null, null);
INSERT INTO bluecode.studies (passed, start_day, curriculum_id, total_time, user_id, text_code, text_def, text_quiz, level) VALUES (false, '2024-09-11', 80, null, 2, null, null, null, null);

-- tests
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (true, 0, 57, '2024-09-11 16:24:00.081462', '2024-09-11 16:24:00.081462', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (false, 0, 58, '2024-09-11 16:24:00.082475', '2024-09-11 16:24:00.082475', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (true, 0, 3, '2024-09-11 16:24:00.083472', '2024-09-11 16:24:00.083472', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (true, 0, 1, '2024-09-11 16:24:00.083472', '2024-09-11 16:24:00.083472', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (false, 1, 59, '2024-09-11 16:24:00.084472', '2024-09-11 16:24:00.084472', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (false, 0, 60, '2024-09-11 16:24:00.084472', '2024-09-11 16:24:00.084472', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (false, 1, 4, '2024-09-11 16:24:00.085500', '2024-09-11 16:24:00.085500', 2, 'INIT');
INSERT INTO bluecode.tests (passed, wrong_count, quiz_id, solved_date, test_date, user_id, test_type) VALUES (true, 0, 5, '2024-09-11 16:24:00.085500', '2024-09-11 16:24:00.085500', 2, 'INIT');
