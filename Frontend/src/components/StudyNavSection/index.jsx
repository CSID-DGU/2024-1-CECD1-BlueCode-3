import PASS from '../../pass.png';
import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import styled from 'styled-components';
import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import useChapterData from '../../useChapterData';



function StudyNavSectionJsx() {
    
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

  const passColor = { color : "#0053B7" };
  const textDeco = { textDecoration : "none" };
  const color = { color : "#008BFF", fontWeight : "bold" };
  const nullColor = { color : "grey", fontWeight : "bold" };

  const [openChapters, setOpenChapters] = useState(Array(chapter.length).fill(false));

  const toggleChapter = (index) => {
    setOpenChapters((prevState) => {
      const newState = [...prevState];
      newState[index] = !newState[index]; // 클릭한 챕터의 상태만 토글
      return newState;
    });
  };
  
  const [menu, setMenu] = useState(false);
  const showMenu = () => {
    if (menu) {
      setMenu(false);
    } else {
      setMenu(true);
    }
  }

  return (
    !menu?
    <MenuButton onClick={showMenu}> ≡ </MenuButton>
    :
    <NavSection>
      <MenuButton onClick={showMenu}> ≡ </MenuButton>
      <NavContent>
        <SectionBar>
          <Logo><img src={BCODE} alt="Logo"></img></Logo>
        </SectionBar>
        <Static>
          <NavLink style={textDeco} to="/mypage/todo"><Nav> ㅇ 마이페이지 </Nav></NavLink>
          <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
        </Static>
        <Dynamic>
          <div>
            {chapter.map((chapterTitle, index) => (
            <div key={index}>
              <NavTitle onClick={() => toggleChapter(index)} 
                   style={chapterPass[index] ? passColor : chapterColor(index)}>
                  {index + 1}. {chapterTitle} {chapterPass[index] && <img src={PASS} alt="Logo" />}
              </NavTitle>
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
        </Dynamic>
      </NavContent>
    </NavSection>
  );
};

export default StudyNavSectionJsx;



const MenuButton = styled.button`
  border : none;
  top : 0.825rem;
  left : 0.75rem;
  width : 2.5rem;
  color : #FFFFFF;
  cursor : pointer;
  position : fixed;
  font-size : 2rem;
  border-radius : 1.25rem;
  background-color : #008BFF;
`

const NavSection = styled.div`
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.25);
`

const NavContent = styled.div`
  display : flex;
  min-width : 15rem;
  flex-direction : column;
  background-color: #FFFFFF;
`

const SectionBar = styled.div`
  display : flex;
  min-width : 15rem;
  background : #008BFF;
`

const Logo = styled.div`
  img {
    height : 2rem;
    width : 7.82rem;
    margin : 1rem 4rem;
  }
`

const Static = styled.div`
  padding : 0.625rem;
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
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

const NavTitle = styled.div`
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
  padding : 0.625rem 0.5rem 0.75rem;
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