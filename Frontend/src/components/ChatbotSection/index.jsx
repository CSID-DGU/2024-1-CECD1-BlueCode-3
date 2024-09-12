import React from 'react';
import Input from '../../input.png';
import styled from 'styled-components';
import axiosInstance from '../../axiosInstance';
import { useRef, useState, useEffect } from 'react';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';

function ChatbotSectionJsx({ height, subChapId }) {

    const [dialog, setDialog] = useState("");
  const [dialogs, setDialogs] = useState([]);
  
  const [step, setStep] = useState(0);
  const [stepDialogs, setStepDialogs] = useState();

  const AddDialog = async () => {
    if(dialog) {
      if(!divValue) {
        setDialogs((pre) => [...pre, <Dialog_server> <div> 태그를 선택해주세요 </div> </Dialog_server>]);
      }
      else {
        setDialogs((pre) => [...pre, <Dialog_client> <div> {dialog} </div> </Dialog_client>]);
        try {
          const res = await getChatResponse(dialog, divValue);
          
          setDialogs((pre) => [...pre, <Dialog_server> <div> <ReactMarkdown>{res.answerList[0]}</ReactMarkdown> </div> </Dialog_server>]);
          if (divValue === "CODE" || divValue === "ERRORS") {
            console.log("1로바꿈")
            setStep(1);
          }
        } catch (err) {
          console.log(err);
        }
        setDialog("");
      }
    }
  }

  useEffect(()=>{
    getSubChapterChatHistory();
  }, []);

  //서브 챕터 단위 채팅 히스토리 로드
  const getSubChapterChatHistory =  async () =>{
    const userid = localStorage.getItem('userid');

    const QuestionCallDto = {
      'userId': userid,
      'curriculumId': subChapId
    };
    try {
      const response = await axiosInstance.post('/chat/chat/historyBySubChapter', QuestionCallDto);
      // console.log(response);

      const dialogsToAdd = [];

      const res = response.data.list;
      // console.log(res);
      for (var i = 0; i < res.length; i++) {
        // console.log(res[i].question);
        
        const question = res[i].question
        dialogsToAdd.push(<Dialog_client> <div> {question} </div> </Dialog_client>);

        //console.log(res[i].answer);
        const answer = res[i].answer;
        //console.log(answer.length);
        for (var j = 0; j < answer.length; j++) {
          dialogsToAdd.push(<Dialog_server> <div> <ReactMarkdown>{answer[j]}</ReactMarkdown> </div> </Dialog_server>);
        }
      }
      setDialogs((pre) => [...pre, ...dialogsToAdd]);
    } catch (err) {
      console.error(err);
    }
  }
  
  const AddStepDialog = async () => {
    setDialogs((pre) => [...pre, <Dialog_server> <div> <ReactMarkdown>{stepDialogs.answerList[step]}</ReactMarkdown> </div> </Dialog_server>]);

    // 백에 next 처리 요청
    postNextResponse(stepDialogs.chatId);
    setStep(step + 1);
    if(step === stepDialogs.answerList.length - 1)
    {
      setStepDialogs('');
      setStep(0);
    }
  }

  const EndStepDialog = () => {
    setStep(0);
  }

  const [divValue, setDivValue] = useState('');
  const borderStyle = { borderRadius : "0.5rem", borderBottom : "0.25rem solid #008BFF" };
  const getDivValue = (divVal) => {
    setDivValue(divVal);
  }

  //챗봇 답변 요청 (질문 타입 : DEF, CODE, ERRORS )
  const getChatResponse =  async (dialog, divValue) =>{
    const userid = localStorage.getItem('userid');
    console.log("질문 본문 "+dialog+" 질문 타입 "+divValue);

    const QuestionCallDto = {
      'userId': userid,
      'curriculumId': subChapId,
      "text": dialog,
      "type": divValue
    };

    try {
      const res = await axiosInstance.post('/chat/chat/response', QuestionCallDto);
      console.log(res);
      if(divValue!="DEF" )
        console.log(res.data)
        setStepDialogs(res.data);
      return res.data;
    } catch (err) {
      console.error(err);
    }
  }

  //단계적 답변(code,error 타입 질문) 다음 답변 요청
  const postNextResponse =  async (chatId) =>{
    const NextLevelChatCallDto = {
      'chatId': chatId
    };
    try {
      const response = await axiosInstance.post('/chat/chat/next', NextLevelChatCallDto);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }
  
  useEffect(()=>{
    if (step){
      console.log(step);
    }
  }, [step]);
  
  const chat = useRef();
  const scrollToBottom = () => {
    chat.current?.scrollIntoView();
  };

  useEffect(()=>{
    scrollToBottom();
  }, [dialogs]);
    
    return (
        <ChatbotSection>
            <Chat height={height}>
                {dialogs.map(div => div)}
                <div ref={chat}></div>
            </Chat>
            {(step > 0) && <ChatType>
                <Type onClick={AddStepDialog}> 다음 답변보기 </Type>
                <Type onClick={EndStepDialog}> 다른 질문하기 </Type>
            </ChatType>}
            {!step && <ChatType>
              <Type style={divValue === "DEF"?borderStyle:{}} onClick={()=>getDivValue("DEF")}> #개념 </Type>
              <Type style={divValue === "CODE"?borderStyle:{}} onClick={()=>getDivValue("CODE")}> #코드 </Type>
              <Type style={divValue === "ERRORS"?borderStyle:{}} onClick={()=>getDivValue("ERRORS")}> #오류 </Type>
            </ChatType>}
            <ChatInput>
              <InputArea value={dialog} onChange={(e)=>setDialog(e.target.value)}></InputArea>
              <InputButton onClick={AddDialog}> <img src={Input}></img> </InputButton>
            </ChatInput>
        </ChatbotSection>
    );
}

export default ChatbotSectionJsx;



const ChatbotSection = styled.div`
  width : 33%;
  margin : 1rem 0rem;
  background-color : #FFFFFF;
  border : 0.125rem solid #008BFF;
  border-right-style : none;
  border-radius : 1rem 0rem 0rem 1rem;
`

const Chat = styled.div`
  margin : 1rem;
  display : flex;
  overflow : scroll;
  flex-direction : column;
  height : ${(props) => `${(props.height - 248) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog_client = styled.div`
  display : flex;
  justify-content : flex-end;

  div {
    margin : 0.5rem 0;
    padding : 0.75rem;
    width : fit-content;
    background : #FFFFFF;
    word-break : break-word;
    overflow-wrap : break-word;
    border-radius : 1.5rem 1.5rem 0rem 1.5rem;
    border : 0.05rem solid rgba(0, 0, 0, 0.375);
  }
`

const Dialog_server = styled.div`
  display : flex;

  div {
    color : #FFFFFF;
    margin : 0.5rem 0;
    padding : 0.75rem;
    overflow : scroll;
    width : fit-content;
    word-break : break-word;
    overflow-wrap : break-word;
    background : rgba(0, 139, 255, 0.75);
    border-radius : 1.5rem 1.5rem 1.5rem 0rem;

    &::-webkit-scrollbar {
      display : none;
    }
  }
`

const ChatType = styled.div`
  height : 3rem;
  display : flex;
  align-item : center;
  justify-content : center;
  border-top : 0.25rem solid #008BFF;
  border-bottom : 0.25rem solid #008BFF;
`

const Type = styled.div`
  color : #008BFF;
  cursor : pointer;
  font-weight : bold;
  margin : 0.5rem 1rem;
  padding : 0.25rem 0.5rem;

  &:hover {
    border-radius : 0.5rem;
    border-bottom : 0.25rem solid rgba(0, 139, 255, 0.375);
  }
`

const ChatInput = styled.div`
  display : flex;
  height : 3.5rem;
  background : #008BFF;
  border-radius : 0rem 0rem 0rem 1rem;
`

const InputArea = styled.input`
  border : none;
  width : 78.75%;
  height : 2.5rem;
  font-size : 1rem;
  padding : 0rem 1rem;
  border-radius : 1.25rem;
  margin : 0.375rem 0.75rem;
`

const InputButton = styled.button`
  color : #FFFFFF;
  height : 2.5rem;
  cursor : pointer;
  font-weight : bold;
  min-width : 2.5rem;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;
  margin : 0.375rem 0rem 0rem;
  border : 0.125rem solid #FFFFFF;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`