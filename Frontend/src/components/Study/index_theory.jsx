import Left from '../../left.png';
import Right from '../../right.png';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import NavSectionJsx from '../NavSection';
import SectionBarJsx from '../SectionBar';
import axiosInstance from '../../axiosInstance';
import ChatbotSectionJsx from '../ChatbotSection';
import React, { useState, useEffect } from 'react';
import ConfirmJsx from '../../Window/index_confirm';
import { useParams, useNavigate } from 'react-router-dom';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



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


  const [gptValue, setGptValue] = useState(false);
  const ShowGpt = () => {
    if (gptValue) {
      setGptValue(false);
    }
    else {
      setGptValue(true);
    }

    // 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.
  }

  

  const { subChapId, text } = useParams();

  useEffect(()=>{
    getStudyText(subChapId, text);
  }, []);
  
  const [theory, setTheory] = useState('');

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
      setTheory(res.data.text);
    }
    catch (err){
      console.error(err); 
    }
  }

  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const navigate = useNavigate();
  const goToTraining = () => {
    setIsConfirmOpen(true);
  }

  const handleConfirm = (confirm) => {
    if (confirm) {
      navigate(`/study/training/${subChapId}/CODE`);
    }
    setIsConfirmOpen(false);
  }

  const [menu, setMenu] = useState(false);
  const showMenu = () => {
    if (menu) {
      setMenu(false);
    } else {
      setMenu(true);
    }
  }
 
  return (
    <TestSection>
      {menu && <NavSectionJsx />}
      <MenuButton onClick={showMenu}> ≡ </MenuButton>
      <SectionBarJsx />
      <Content>
        <ContentSection gptValue={gptValue}>
          {theory?
          <Instruction height={height}>
            <ReactMarkdown rehypePlugins={[rehypeHighlight]}>{theory}</ReactMarkdown>
          </Instruction>
          :
          <InstructionLoading height={height}>
            <img src={LOADING} alt="loading"></img>
          </InstructionLoading>}
          {isConfirmOpen &&
          <ConfirmJsx message="예제 코드 학습으로 넘어가시겠습니까?"
                      onConfirm={()=>handleConfirm(true)}
                      onCancel={()=>handleConfirm(false)}>
          </ConfirmJsx>}
          <Buttons>
            <Before> <img src={Left}></img> </Before>
            <After onClick={goToTraining}> <img src={Right}></img> </After>
            <GPT onClick={ShowGpt}> GPT </GPT>
          </Buttons>
        </ContentSection>
        {gptValue && <ChatbotSectionJsx height={height} subChapId={subChapId}/>}
      </Content>
    </TestSection>
  );
}

export default Study_theory;



const TestSection = styled.div`
  height : 100vh;
`

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

const Content = styled.div`
  display : flex;
`

const ContentSection = styled.div`
  margin : 1rem 1rem 1rem 0rem;
  border : 0.125rem solid #008BFF;
  border-left-style : none;
  border-radius : 0rem 1rem 1rem 0rem;
  width : ${(props) => (props.gptValue ? '66%' : '100%')};
`

const Instruction = styled.div`
  overflow : scroll;
  padding : 0rem 1rem;
  margin-bottom : 0.5rem;
  height : ${(props) => `${(props.height - 158) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const InstructionLoading = styled.div`
  display : flex;
  padding : 0rem 1rem;
  align-items : center;
  margin-bottom : 0.5rem;
  justify-content : center;
  height : ${(props) => `${(props.height - 158) / 16}rem`};

  img {
    height : 5rem;
    width : 12.5rem;
  }
`

const Buttons = styled.div`
  float : right;
  padding : 0rem 1rem 0.75rem;
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