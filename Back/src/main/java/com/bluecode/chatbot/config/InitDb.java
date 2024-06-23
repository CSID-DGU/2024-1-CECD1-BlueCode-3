package com.bluecode.chatbot.config;

import com.bluecode.chatbot.domain.Curriculums;
import com.bluecode.chatbot.domain.Users;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트용 데이터를 DB에 저장하는 class 입니다.
 */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.userInit();
        initService.curriculumInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void userInit() {
            Users user1 = createUser("testName", "testEmail", "testId", "1111", "11110033", false); // 초기 테스트 미진행 유저
            em.persist(user1);
        }

        public void curriculumInit() {

            Curriculums root = createCurriculum(null, "파이썬", "", "", "", false);
            em.persist(root);
            em.flush();

            Curriculums chap1 = createCurriculum(root, "1. 프로그래밍 정의", "", "", "파이썬이란?, 프로그래밍 버그란?", false);
            Curriculums chap2 = createCurriculum(root, "2. 파이썬 설치 환경", "", "", "OS별 (MS, Linux, Mac) 파이썬 설치 방법", false);
            Curriculums chap3 = createCurriculum(root, "3. 파이썬 실행 원리", "", "", "IDE를 이용한 파이썬 코드 입력 및 결과 출력 방법, CLI를 이용한 파이썬 코드 입력 및 결과 출력 방법, 파이썬의 실행 원리(파이썬 인터프리터 와 OS 와 HW의 관계로)", false);
            Curriculums chap4 = createCurriculum(root, "4. 표현식", "타입(숫자형(정수, 소수, 복소수), boolean)", "산술, 할당, 항등, 멤버, 논리 연산자", "삼항, 비트연산자", true);
            Curriculums chap5 = createCurriculum(root, "5. 변수와 메모리", "변수의 정의, 변수 할당 방법", "변수의 재할당, 여러개 변수 할당, 변수 명명 규칙", "코딩에서의 컴퓨터 메모리", true);
            Curriculums chap6 = createCurriculum(root, "6. 파이썬 오류", "파이썬 오류의 정의", "주로 나오는 파이썬 예외 종류", "try-catch 예외 처리, 나만의 예외 처리 만들기", true);
            Curriculums chap7 = createCurriculum(root, "7. 주석,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", "", "", "주석 사용 방법(한줄, 여러줄), 주석의 역할 및 용도,파이썬에서 대괄호, 중괄호, 소괄호 간 차이", false);
            Curriculums chap8 = createCurriculum(root, "8. 함수", "함수란?, 파이썬 내장 함수,함수의 매개 변수", "전역변수와 지역 변수, 사용자 정의 함수, 함수 리턴값과 None", "함수의 메모리 주소, 함수 호출 스택 구조", true);
            Curriculums chap9 = createCurriculum(root, "9. 문자열", "문자열 선언 방법", "문자열 슬라이싱, 서식 지정자, f-string, format, escape 문자, 문자열 출력, 문자열 입력", "다행 문자열", true);
            Curriculums chap10 = createCurriculum(root, "10. 조건문", "", "", "bool값, if-else문, 중첩 조건문과 삼항 연산자", false);
            Curriculums chap11 = createCurriculum(root, "11. 모듈화", "", "", "모듈 import, 사용자 정의 모듈", false);
            Curriculums chap12 = createCurriculum(root, "12. 메서드", "클래스, 메서드, 메서드 호출", "문자열 메서드", "매직 메서드", true);
            Curriculums chap13 = createCurriculum(root, "13. 리스트", "리스트 데이터 저장, 리스트 타입 표기, 리스트 수정", "리스트 연산, 리스트 슬라이싱", "리스트 메서드", true);
            Curriculums chap14 = createCurriculum(root, "14. 반복문", "for 문, while 문, 리스트를 활용한 반복문, 수 범위 순회, 인덱스 사용 리스트 처리, 중첩 반복문, 조건 반복문, 무한루프, break와 continue", "문자열 내 문자 처리", "사용자 입력에 따른 반복", true);
            Curriculums chap15 = createCurriculum(root, "15. 파일 처리", "", "", "with 문, 파일 정리, 특정 파일 명시, 파일 읽기, 파일 쓰기, StringIO, 다수행 레코드, 미리보기", false);
            Curriculums chap16 = createCurriculum(root, "16. 컬렉션", "세트,튜플,딕셔너리,컬렉션이란", "세트 연산,딕셔너리 순회,연산,도치, 컬렉션 in 연산자,컬렉션 비교", "“:”를 활용한 파이썬에서 타입 명시 방법", true);
            Curriculums chap17 = createCurriculum(root, "17. 객체 지향 프로그래밍", "객체지향 프로그래밍이란, 클래스란", "클래스의 생성자의 사용법", "클래스 상속, 오버라이딩,오버로딩", true);

            em.persist(chap1);
            em.persist(chap2);
            em.persist(chap3);
            em.persist(chap4);
            em.persist(chap5);
            em.persist(chap6);
            em.persist(chap7);
            em.persist(chap8);
            em.persist(chap9);
            em.persist(chap10);
            em.persist(chap11);
            em.persist(chap12);
            em.persist(chap13);
            em.persist(chap14);
            em.persist(chap15);
            em.persist(chap16);
            em.persist(chap17);
        }

        private Curriculums createCurriculum(
                Curriculums parent,
                String curriculumName,
                String keywordEasy,
                String keywordNormal,
                String keywordHard,
                boolean testable
        ) {
            Curriculums curriculums = new Curriculums();
            curriculums.setCurriculumName(curriculumName);
            curriculums.setParent(parent);
            curriculums.setKeywordEasy(keywordEasy);
            curriculums.setKeywordNormal(keywordNormal);
            curriculums.setKeywordHard(keywordHard);
            curriculums.setTestable(testable);

            return curriculums;
        }

        private Users createUser(
                String username,
                String email,
                String id,
                String password,
                String birth,
                boolean initTest) {

            Users user = new Users();
            user.setUsername(username);
            user.setId(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setBirth(birth);
            user.setInitTest(initTest);

            return user;
        }
    }
}
