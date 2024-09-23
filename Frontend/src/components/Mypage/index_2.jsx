import PASS from '../../pass.png';
import styled from 'styled-components';
import SectionBarJsx from '../SectionBar';
import PrivateInfoJsx from '../PrivateInfo';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../axiosInstance';
import React, { useState, useEffect} from 'react';
import useChapterData from '../../useChapterData';
import MypageNavSectionJsx from '../MypageNavSection';
import getChapterTestable from '../../getChapterTestable';



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

  const [chapterTestable, setChaptherTestable] = useState([]);

  
  useEffect(() => {
    getChapterTestable()
    .then(data => {
      // 데이터 가져오기 성공 시 상태 업데이트'
      setChaptherTestable(data);
    })
    .catch(error => {
      // 데이터 가져오기 실패 시 에러 처리
      console.error('Error fetching data:', error);
    });
  }, []);


  const passColor = { color : "#0053B7" };
  const color = { color : "#008BFF", fontWeight : "bold" };
  const nullColor = { color : "grey", fontWeight : "bold" };

  const { chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter } = useChapterData();

  useEffect(()=>{
    //console.log("7개 중에 데이터가 바뀜");
  }, [chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter]);

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

  const postChapterPass =  async (chapterid, level) =>{
    const userid = localStorage.getItem('userid');

    const CurriculumPassCallDto = {
      'userId': userid,
      'curriculumId': chapterid,
      'levelType': level
    };

    try {
      //chapter 이해도 테스트 통과 완료 처리 요청
      const response = await axiosInstance.post('/curriculum/curriculum/chapter/pass', CurriculumPassCallDto);
      window.location.replace('/mypage/lecture');
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }
  


  return (
    <TestSection>
      <PrivateInfoJsx />
      <SectionBarJsx />
      <Content>
        <MypageNavSectionJsx height={height} l1={false} l2={true} l3={false} />
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
              {(!chapterTestable[index] && !chapterPass[index] && currentChapter[index] && currentChapter[index].every(item => item === true)) && (
                <CompreTest onClick={()=> postChapterPass(chaptersid[index],"EASY")}> 다음 챕터 학습하기 </CompreTest>
              )}
              {(chapterTestable[index] && !chapterPass[index] && currentChapter[index] && currentChapter[index].every(item => item === true)) && (
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

const Content = styled.div`
  display : flex;
`

const ContentSection = styled.div`
  padding : 1rem;
  margin : 1rem 0rem 1rem 1rem;
  border : 0.125rem solid #008BFF;
  border-radius : 1rem 0rem 0rem 1rem;
  border-right-style : none;
  width : ${(props) => `${(props.width - 291.5) / 16}rem`};
`

const LectureInfo = styled.p`
  font-weight : bold;
  font-size : 1.05rem;
  margin : 0rem 0rem 1rem;
  color : rgba(0, 0, 0, 0.5);
`

const LectureContent = styled.div`
  display : flex;
  overflow :scroll;
  flex-wrap : wrap;
  padding : 0rem 1rem;
  height : ${(props) => `${(props.height - 174) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const Lecture = styled.div`
  width : 31.25%;
  padding : 0rem 0.75rem;
`

const LectureTitle = styled.h3`
  font-weight : bold;
  margin : 1.75rem 0rem 0.75rem;

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
  padding-right : 0.2rem;

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
  white-space: nowrap;
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