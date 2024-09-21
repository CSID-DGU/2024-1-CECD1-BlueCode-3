import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import TerminalJsx from '../../Terminal';
import StudyNavSectionJsx from '../StudyNavSection';
import SectionBarJsx from '../SectionBar';
import axiosInstance from '../../axiosInstance';
import ChatbotSectionJsx from '../ChatbotSection';
import ConfirmJsx from '../../Window/index_confirm';
import { useParams, useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



function Study_training() {
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


  const [gptValue, setGptValue] = useState(false);

  const ShowGpt = () => {
    if (gptValue) {
      setGptValue(false);
    }
    else {
      setGptValue(true);
    }
  }

  const { subChapId, text } = useParams();

  useEffect(()=>{
    getStudyText(subChapId, text);
    console.log(subChapId);
    console.log(text);
  }, []);

  
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const navigate = useNavigate();
  const goToNext = () => {
    // quiz 에서 다음 누르면 해당 서브챕터 pass 요청
    setIsConfirmOpen(true);
    
  }

  const handleConfirm = (confirm) => {
    if(text === 'QUIZ') {
      if (confirm) {
        postSubchapterPass(subChapId).then(
          () => {
            navigate('/mypage/lecture'); 
          }
        );
        // navigate('/mypage/lecture');
      }
    }
    else if (text === 'CODE') {  // code 에서 다음 누르면 quiz 학습으로
      if (confirm) {
        window.location.replace(`/study/training/${subChapId}/QUIZ`);
        //navigate(`/study/training/${subChapId}/QUIZ`, {replace : true});
      }
    }
    setIsConfirmOpen(false);
  }

  const goBack = ()=> {
    //이전페이지로 이동
    
    if(text==='QUIZ'){
      window.location.replace(`/study/training/${subChapId}/CODE`);
    }
    else if (text === 'CODE'){  // code 에서 다음 누르면 quiz 학습으로
      navigate(-1);
    }
  }
  const [training, setTraining] = useState('');

  //useEffect(()=>{}, [training]);

  // DEF 이론 , CODE 예시 코드, QUIZ 코드를 이용한 예시 문제
  const getStudyText = async (subChapterid, textType) => {
    try {
      //파라미터에서 파싱하도록 수정
      const userId = localStorage.getItem('userid');
  
      const CurriculumTextCallDto = {
        'userId': userId,
        'curriculumId': subChapterid,
        'textType': textType
      };
        
      const res = await axiosInstance.post('/curriculum/curriculum/text', CurriculumTextCallDto);
      console.log("학습데이터호출");
      setTraining(res.data.text);
    }
    catch (err){
      console.error(err); 
    }
  }

  const postSubchapterPass = async (subChapterid) => {
    try {
      //파라미터에서 파싱하도록 수정
      const userId = localStorage.getItem('userid');

      const CurriculumPassCallDto = {
        'userId': userId,
        'curriculumId': subChapterid
      };
      const res = await axiosInstance.post('/curriculum/curriculum/subChapter/pass', CurriculumPassCallDto);
      console.log(res);
    }
    catch (err){
      console.error(err); 
    }
  }

  return (
    <TestSection>
      <StudyNavSectionJsx />
      <SectionBarJsx />
      <Content>
        <ContentSection gptValue={gptValue}>
          {training?
          <Instruction height={height}>
            <ReactMarkdown rehypePlugins={[rehypeHighlight]}>{training}</ReactMarkdown>
          </Instruction>
          :
          <InstructionLoading height={height}>
            <img src={LOADING} alt="loading"></img>
          </InstructionLoading>}
          <Train height={height}>
            <TerminalJsx />
            <Buttons>
              <Before onClick={goBack}> <img src={Left}></img> </Before>
              <After onClick={goToNext}> <img src={Right}></img> </After>
              <GPT onClick={ShowGpt}> GPT </GPT>
            </Buttons>
          </Train>
          {isConfirmOpen &&
          <ConfirmJsx message={text === "CODE"?"심화 코드 학습으로 넘어가시겠습니까?":"해당 서브 챕터 학습을 마치시겠습니까?"}
                      onConfirm={()=>handleConfirm(true)}
                      onCancel={()=>handleConfirm(false)}>
          </ConfirmJsx>}
          
        </ContentSection>
        {gptValue && <ChatbotSectionJsx height={height} subChapId={subChapId}/>}
      </Content>
    </TestSection>
  );
}

export default Study_training;



const TestSection = styled.div`
`

const Content = styled.div`
  display : flex;
`

const ContentSection = styled.div`
  display: flex;
  margin: 1rem 1rem 1rem 0rem;
  border: 0.125rem solid #008BFF;
  border-left-style: none;
  border-radius: 0rem 1rem 1rem 0rem;
  width : ${(props) => (props.gptValue ? '66%' : '100%')};
`

const Instruction = styled.div`
  overflow : scroll;
  margin-bottom : 1rem;
  margin : 0rem 1rem 0rem 1rem;
  height : ${(props) => `${(props.height - 120) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const InstructionLoading = styled.div`
  width : 100%;
  display : flex;
  align-items : center;
  justify-content : center;
  padding : 0rem 1rem;
  
  img {
    width : 12.5rem;
    height : 5rem;
  }
`

const Buttons = styled.div`
  display : flex;
  justify-content : right;
  padding : 0.375rem 0rem 0.625rem;
`

const Before = styled.button`
  width : 2rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  cursor : pointer;
  margin : 0 0.25rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;

  img {
    width : 0.5rem;
  }
`

const After = styled.button`
  width : 2rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  cursor : pointer;
  margin : 0 0.25rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;

  img {
    width : 0.5rem;
  }
`

const GPT = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #FFFFFF;
  cursor : pointer;
  font-size : 1rem;
  font-weight : bold;
  background : #008BFF;
  margin-left : 0.5rem;
  border-radius : 1rem;
`

const Train = styled.div`
  max-width : 50%;
  display : flex;
  margin : 1rem 1rem 0rem 1rem;
  flex-direction : column;
  height : ${(props) => `${(props.height - 120) / 16}rem`};
`
