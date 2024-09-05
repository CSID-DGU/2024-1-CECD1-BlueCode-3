import React, { useState } from 'react';
import PASS from '../pass.png';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import useChapterData from '../useChapterData';

function NavSectionJsx() {
    
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

  const goToCompre = (chapId) => {
    navigate(`/study/comprehension/${chapId}`);
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
          {/* 챕터 타이틀 클릭 시 토글 */}
          <div 
            onClick={() => toggleChapter(index)} 
            style={chapterPass[index] ? passColor : chapterColor(index)}
          >
            {index + 1}. {chapterTitle} {chapterPass[index] && <img src={PASS} alt="Logo" />}
          </div>

          {/* 서브챕터는 챕터가 열려 있을 때만 보이도록 */}
          {openChapters[index] && (
            <div>
              {subChapter[index].map((subItem, subIndex) => (
                <div key={subIndex}>
                  <div style={currentChapter[index] && currentChapter[index][subIndex] ? color : nullColor}> 
                    {subItem} 
                  </div>  
                  {(chapterLevel[index] === 'EASY' || chapterPass[index]) && (
                    <div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'DEF')}> 이론 </div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </div>
                    </div>
                  )}
                  {(!chapterPass[index] && chapterLevel[index] === 'NORMAL') && (
                    <div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'CODE')}> 실습 </div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </div>
                    </div>
                  )}
                  {(!chapterPass[index] && chapterLevel[index] === 'HARD') && (
                    <div>
                      <div onClick={() => goToStudy(subChapterId[index][subIndex], 'QUIZ')}> 퀴즈 </div>
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default NavSectionJsx;
