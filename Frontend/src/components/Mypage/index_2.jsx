import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import React, { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import useChapterData from '../../useChapterData';

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

  const color = { color : "#008BFF", fontWeight : "bold" };
  const passColor = { color : "#0053B7" };
  const nullColor = { color : "grey", fontWeight : "bold" };
  const textDeco = { textDecoration : "none" };

  const { chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter } = useChapterData();

  const chapterColor = (index) => {
    var chColor;
    //console.log(chapterLevel);
    //console.log(chapterPass);

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
      <SectionBar>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
        </Logo>
      </SectionBar>
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {process} % </p> </InfoNav>
            <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
          </Info>
          <Dynamic>
            <NavLink style={textDeco} to="/mypage/todo"><Nav> ㅇ 내 할일 관련 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/lecture"><Nav style={color}> ㅇ 내 강의 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/question"><Nav> ㅇ 내 질문 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/info"><Nav> ㅇ 내 정보 수정 </Nav></NavLink>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <LectureInfo> ㅇ 이전 교육 복습 </LectureInfo>
          {chapter.map((chapterTitle, index) => (
            <div key={index}>
              <h2 style={chapterPass[index]?passColor:chapterColor(index)}>{chapterTitle} {chapterPass[index]&&("시험 패스 이미지")}</h2>
              <div style={{ marginLeft: '20px' }}>
              {subChapter[index].map((subItem, subIndex) => (
                <div style={{ display : "flex" }}>
                <p key={subIndex} style={currentChapter[index]&&currentChapter[index][subIndex]?color:nullColor}> {subItem}</p>  
                {(chapterLevel[index] === 'EASY' || chapterPass[index]) && (
                  <div style={{display : "flex", marginLeft : "20px"}}>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'DEF')}> 이론 </p>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </p>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </p>
                  </div>
                )}
                {(!chapterPass[index] && chapterLevel[index] === 'NORMAL') && (
                  <div style={{display : "flex", marginLeft : "20px"}}>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </p>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </p>
                  </div>
                )}
                {(!chapterPass[index] && chapterLevel[index] === 'HARD') && (
                  <div style={{display : "flex", marginLeft : "20px"}}>
                    <p onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </p>
                  </div>
                )}
                </div>
              ))}
              </div>
              {(!chapterPass[index] && currentChapter[index] && currentChapter[index].every(item => item === true)) && (
                <p onClick={() => goToCompre(chaptersid[index])}> 이해도 테스트 </p>
              )}
            </div>
          ))}
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
  margin : 0;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const Chapter = styled.div`
  margin : 1rem;
`