import PASS from '../../pass.png';
import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import styled from 'styled-components';
import getUserInfo from '../../getUserInfo';
import SectionBarJsx from '../../SectionBar';
import React, { useState, useEffect} from 'react';
import useChapterData from '../../useChapterData';
import getChapterPass from '../../getChapterPass';
import { NavLink, useNavigate } from 'react-router-dom';

function Study_theory() {
  const [width, setWidth] = useState(window.innerWidth);
  const [height, setHeight] = useState(window.innerHeight);

  const handleResize = () => {
    setWidth(window.innerWidth);
    setHeight(window.innerHeight);
  };

  useState(() => {
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  const [contentWidth, setContentWidth] = useState(width);

  const [point, setPoint] = useState(0);
  const [process, setProcess] = useState(0);
  const [processPass, setProcessPass] = useState(0);

  useEffect(() => {
    getChapterPass()
    .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
        setProcess(data.length);
        setProcessPass(data.filter(element => element === true).length);
    })
      .catch(error => {
        // 데이터 가져오기 실패 시 에러 처리
      console.error('Error fetching data:', error);
    });

    getUserInfo()
      .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
        setPoint(data.exp);
      })
      .catch(error => {
        // 데이터 가져오기 실패 시 에러 처리
        console.error('Error fetching data:', error);
      });
  }, []);


  const color = { color : "#008BFF", fontWeight : "bold" };
  const passColor = { color : "#0053B7" };
  const nullColor = { color : "grey", fontWeight : "bold" };
  const textDeco = { textDecoration : "none" };

  const { chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter } = useChapterData();

  const chapterColor = (index) => {
    var chColor;

    if(chapterLevel[index]=== null) {   
      chColor = {color : "grey"};
    }
    else if (chapterLevel[index] === 'EASY') {
      chColor = {color : "#00E5BA"};
    } 
    else if (chapterLevel[index] === 'NORMAL') {
      chColor = {color : "#00CFEE"};
    } 
    else if (chapterLevel[index] === 'HARD') {
      chColor = {color : "#00B2FF"};
    }
      return chColor;
  }


  // 챕터가 passed -> 해당 챕터의 서브챕터들은 모든 선택지 선택 가능 --> 
  // 챕터가 notpass이고  챕터가 easy -> def부터,code부터,quiz부터 | normal -> code부터,quiz부터  |  hard면 quiz부터
  // 선택지로 정해진 def,code,quiz와 누른 서브챕터id를 파라미터로 

  // def 이론 , code 예시코드(실습), quiz 코테형식자료(퀴즈)

  const navigate = useNavigate();
  const goToStudy = (subChapId, levelType) => {
    if (levelType === 'DEF') {
      navigate(`/study/theory/${subChapId}/DEF`);
    }
    else if (levelType === 'CODE') {
      navigate(`/study/training/${subChapId}/CODE`);
    }
    else if (levelType === 'QUIZ'){
      navigate(`/study/training/${subChapId}/QUIZ`);
    }
    //def면 index_theory
    //code,quiz이면index_trainig으로 페이지 이동
  } 

  const goToCompre = (chapId) => {
    navigate(`/study/comprehension/${chapId}`);
  } 

  

  return (
    <TestSection>
      <SectionBarJsx />
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {isNaN(Math.round(processPass / process * 100))?"- %":Math.round(processPass / process * 100) + " %"} </p> </InfoNav>
            <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
          </Info>
          <Dynamic>
            <NavLink style={textDeco} to="/mypage/todo"><Nav> ㅇ 내 할일 관련 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/lecture"><Nav style={color}> ㅇ 내 강의 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/question"><Nav> ㅇ 내 질문 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/info"><Nav> ㅇ 내 정보 수정 </Nav></NavLink>
          </Dynamic>
        </NavSection>
        <ContentSection width={width}>
          <LectureInfo> ㅇ 이전 교육 복습 </LectureInfo>
          <LectureContent height={height}>
          {chapter.map((chapterTitle, index) => (
            <Lecture key={index}>
              <LectureTitle style={chapterPass[index]?passColor:chapterColor(index)}>{index + 1}. {chapterTitle} {chapterPass[index]&&<img src={PASS} alt="Logo"></img>}</LectureTitle>
              <SubLectureInfo>
              {subChapter[index].map((subItem, subIndex) => (
                <SubLecture>
                  <SubLectureTitle key={subIndex} style={currentChapter[index]&&currentChapter[index][subIndex]?color:nullColor}> {subItem} </SubLectureTitle>  
                  {(chapterLevel[index] === 'EASY' || chapterPass[index]) && (
                  <LectureData>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'DEF')}> 이론 </Data>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </Data>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </Data>
                  </LectureData>
                  )}
                  {(!chapterPass[index] && chapterLevel[index] === 'NORMAL') && (
                  <LectureData>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </Data>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </Data>
                  </LectureData>
                  )}
                  {(!chapterPass[index] && chapterLevel[index] === 'HARD') && (
                  <LectureData>
                    <Data onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </Data>
                  </LectureData>
                  )}
                </SubLecture>
              ))}
              </SubLectureInfo>
              {(!chapterPass[index] && currentChapter[index] && currentChapter[index].every(item => item === true)) && (
                <CompreTest onClick={() => goToCompre(chaptersid[index])}> 이해도 테스트 </CompreTest>
              )}
            </Lecture>
          ))}
          </LectureContent>
        </ContentSection>
      </Content>
    </TestSection>
  );
}

export default Study_theory;



const TestSection = styled.div`
  height : 100vh;
`

const SectionBar = styled.div`
  width : 100vw;
  display : flex;
  background : #008BFF;
`

const Logo = styled.div`
  img {
    height : 2rem;
    width : 7.82rem;
    margin : 1rem 4rem;
  }
`

const Content = styled.div`
  display : flex;
`

const NavSection = styled.div`
  display : flex;
  min-width : 15rem;
  flex-direction : column;
  border-right : 0.125rem solid rgba(0, 0, 0, 0.125);
  height : ${(props) => `${(props.height - 68) / 16}rem`};
`

const Static = styled.div`
  padding : 0.625rem;
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const Info = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const InfoNav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  p {
    margin : 0;
    text-align : right;
  }
`

const Nav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
    background : rgba(0, 139, 255, 0.25);
  }
`

const Dynamic = styled.div`
  overflow : scroll;
  padding : 0.625rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const ContentSection = styled.div`
  margin : 2rem;
  padding : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const LectureInfo = styled.p`
  font-weight : bold;
  font-size : 1.05rem;
  margin : 0rem 0rem 1.5rem;
  color : rgba(0, 0, 0, 0.5);
`

const LectureContent = styled.div`
  display : flex;
  overflow :scroll;
  flex-wrap : wrap;
  padding : 0rem 0.75rem;
  height : ${(props) => `${(props.height - 250) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const Lecture = styled.div`
  width : 32.5rem;
  padding : 0rem 0.75rem;
  margin : 1rem 0rem;
`

const LectureTitle = styled.h3`
  font-weight : bold;
  margin : 0rem 0rem 0.75rem;

  img {
    width : 1rem;
  }
`

const SubLectureInfo = styled.div`
  margin-left : 1rem;
`

const CompreTest = styled.div`
  color : grey;
  cursor : pointer;
  text-align : right;
  font-weight : bold;
  font-size : 1.125rem;
  padding-right : 1.125rem;

  &:hover {
    color : black;
  }
`

const SubLecture = styled.div`
  display : flex;
  margin-bottom : 0.25rem;
`

const SubLectureTitle = styled.div`
  width : 23rem;
`

const LectureData = styled.div`
  display : flex;
`

const Data = styled.div`
  color : grey;
  cursor : pointer;
  font-weight : bold;
  margin : 0rem 0.25rem;

  &:hover {
    color : #008BFF;
  }
`