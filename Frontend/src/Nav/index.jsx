import PASS from '../pass.png';
import styled from 'styled-components';
import React, { useState } from 'react';
import useChapterData from '../useChapterData';



function NavJsx() {
    
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

  const goToStudy = (subChapId, levelType) => {
    if (levelType === 'DEF') {
      window.location.replace(`/study/theory/${subChapId}/DEF`);
    }
    else if (levelType === 'CODE') {
      window.location.replace(`/study/training/${subChapId}/CODE`);
    }
    else if (levelType === 'QUIZ'){
      window.location.replace(`/study/training/${subChapId}/QUIZ`);
    }
    //def면 index_theory
    //code,quiz이면index_trainig으로 페이지 이동
  } 

  const color = { color : "#008BFF", fontWeight : "bold" };
  const passColor = { color : "#0053B7" };
  const nullColor = { color : "grey", fontWeight : "bold" };

  const [openChapters, setOpenChapters] = useState(Array(chapter.length).fill(false));

  const toggleChapter = (index) => {
    setOpenChapters((prevState) => {
      const newState = [...prevState];
      newState[index] = !newState[index]; // 클릭한 챕터의 상태만 토글
      return newState;
    });
  };

  return (
    <div>
      {chapter.map((chapterTitle, index) => (
      <div key={index}>
        <Nav onClick={() => toggleChapter(index)} 
             style={chapterPass[index] ? passColor : chapterColor(index)}>
          {index + 1}. {chapterTitle} {chapterPass[index] && <img src={PASS} alt="Logo" />}
        </Nav>
        {openChapters[index] && (
        <NavItemSection>
          {subChapter[index].map((subItem, subIndex) => (
          <div key={subIndex}>
            <div style={currentChapter[index] && currentChapter[index][subIndex] ? color : nullColor}> 
              {subItem} 
            </div>
            {!chapterLevel[index] && !chapterPass[index] && (
            <SubNav>
              <SubNavItem> - </SubNavItem>
            </SubNav>)}
            {(chapterLevel[index] === 'EASY' || chapterPass[index]) && (
            <SubNav>
              <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'DEF')}> 이론 </SubNavItem>
              <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </SubNavItem>
              <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </SubNavItem>
            </SubNav>)}
            {(!chapterPass[index] && chapterLevel[index] === 'NORMAL') && (
            <SubNav>
              <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </SubNavItem>
              <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </SubNavItem>
            </SubNav>)}
            {(!chapterPass[index] && chapterLevel[index] === 'HARD') && (
              <SubNav>
                <SubNavItem onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </SubNavItem>
              </SubNav>)}
            </div>))}
          </NavItemSection>)}
        </div>))}
    </div>
  );
};

export default NavJsx;



const Nav = styled.div`
  cursor : pointer;
  font-weight : bold;
  padding : 0.625rem 0.5rem;
  
  &:hover {
    background : rgba(0, 139, 255, 0.25);
  }

  img {
    width : 1rem;
  }
`

const NavItemSection = styled.div`
  width : 12.5rem;
  padding : 0.5rem 0.5rem 0.75rem;
`

const SubNav = styled.div`
  display : flex;
  padding : 0.5rem 0rem;
  justify-content : right;
`

const SubNavItem = styled.div`
  cursor : pointer;
  margin : 0rem 0.25rem;

  &:hover {
    font-weight : bold;
  }
`