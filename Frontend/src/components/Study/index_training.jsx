import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import BCODE from '../../logo_w.png';
import Markdown from '../../Markdown';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import SectionBarJsx from '../../SectionBar';
import axiosInstance from '../../axiosInstance';
import React, { useRef, useState, useEffect } from 'react';
import { NavLink, useParams, useNavigate } from 'react-router-dom';
import TerminalComponent from '../../TerminalComponent';


function Study_training() {
  const [pyodide, setPyodide] = useState(null);
  const [code, setCode] = useState("");
  const [result, setResult] = useState("");
  
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
  const [contentWidth, setContentWidth] = useState(width);
  const [navValue, setNavValue] = useState(false);

  const ShowGpt = () => {
    if (gptValue) {
      setGptValue(false);
      setContentWidth(contentWidth + 432);
    }
    else {
      setGptValue(true);
      setContentWidth(contentWidth - 432)
    }

    // 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.
  }

  const nav_style = {color : '#008BFF', background : 'rgba(0, 139, 255, 0.25)'};
  const styled = {};
  const AddToNavContent = () => {
    if (navValue)
      setNavValue(false);
    else
      setNavValue(true);
  }

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
          
          setDialogs((pre) => [...pre, <Dialog_server> <div> <Markdown>{res.answerList[0]}</Markdown> </div> </Dialog_server>]);
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


  const AddStepDialog = async () => {
    setDialogs((pre) => [...pre, <Dialog_server> <div> <Markdown>{stepDialogs.answerList[step]}</Markdown> </div> </Dialog_server>]);

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
        dialogsToAdd.push(<Dialog_server> <div> <Markdown>{answer[j]}</Markdown> </div> </Dialog_server>);
      }
    }
    setDialogs((pre) => [...pre, ...dialogsToAdd]);
  } catch (err) {
    console.error(err);
  }
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

  const [divValue, setDivValue] = useState('');
  const borderStyle = { borderRadius : "0.5rem", borderBottom : "0.25rem solid #008BFF" };
  const getDivValue = (divVal) => {
    setDivValue(divVal);
  }


  const { subChapId, text } = useParams();

  useEffect(()=>{
    getStudyText(subChapId, text);
    console.log(subChapId);
    console.log(text);
  }, []);

  const navigate = useNavigate();
  const goToNext = () => {
    
    // quiz 에서 다음 누르면 해당 서브챕터 pass 요청
    if(text==='QUIZ'){
      const userConfirm = window.confirm("해당 서브 챕터 학습을 마치시겠습니까?");
      if (userConfirm) {
        postSubchapterPass(subChapId);
        navigate('/mypage/lecture');
      }
    }
    else if (text === 'CODE'){  // code 에서 다음 누르면 quiz 학습으로
      const userConfirm = window.confirm("심화 코드 학습으로 넘어가시겠습니까?");
      if (userConfirm) {
        window.location.replace(`/study/training/${subChapId}/QUIZ`);
        //navigate(`/study/training/${subChapId}/QUIZ`, {replace : true});
      }
    }
  }

  const goBack = ()=> {
    //이전페이지로 이동
    // quiz 에서 다음 누르면 해당 서브챕터 pass 요청
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
      <SectionBarJsx />
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={{ textDecoration : "none" }} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Dynamic>
            <Nav id="1" style={navValue ? nav_style : styled} onClick={AddToNavContent}> 제 1장 </Nav>
            {navValue && (<NavContent>
              <NavItem> 목차 1 </NavItem>
              <NavItem> 목차 2 </NavItem> 
              <NavItem> 목차 3 </NavItem>
            </NavContent>)}
            <Nav> 제 2장 </Nav>
            <Nav> 제 3장 </Nav>
            <Nav> 제 4장 </Nav>
            <Nav> 제 5장 </Nav>
            <Nav> 제 6장 </Nav>
            <Nav> 제 7장 </Nav>
            <Nav> 제 8장 </Nav>
            <Nav> 제 9장 </Nav>
            <Nav> 제 10장 </Nav>
            <Nav> 제 11장 </Nav>
            <Nav> 제 12장 </Nav>
            <Nav> 제 13장 </Nav>
            <Nav> 제 14장 </Nav>
            <Nav> 제 15장 </Nav>
          </Dynamic>
        </NavSection>
        <ContentSection width={width}>
          {training?
          <Instruction height={height}>
            <Markdown>{training}</Markdown>
          </Instruction>
          :
          <InstructionLoading height={height}>
            <img src={LOADING} alt="loading"></img>
          </InstructionLoading>}
          <Train height={height} width={contentWidth}>
            <CodeArea height={height} width={contentWidth} value={code} onChange={(e)=>setCode(e.target.value)}></CodeArea>
            <Buttons_>
              <GPT onClick={ShowGpt}> GPT </GPT>
              <Interpret> 실행 </Interpret>
              <After onClick={goToNext}> <img src={Right}></img> </After>
              <Before onClick={goBack}> <img src={Left}></img> </Before>
            </Buttons_>
          </Train>
          <div style={{ flex: 1, border: '1px solid #ccc', marginLeft: '20px' }}>
          <TerminalComponent />
          </div>
        </ContentSection>
        {gptValue && (<ChatbotSection>
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
        </ChatbotSection>)}
      </Content>
    </TestSection>
  );
}

export default Study_training;



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

const Nav = styled.div`
  display : flex;
  font-weight : bold;
  padding : 0.625rem;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
    background : rgba(0, 139, 255, 0.25);
  }
`

const NavContent = styled.div`

`

const NavItem = styled.div`
  display : flex;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  padding : 0.375rem 0.375rem 0.375rem 1.75rem;

  &:hover {
    color : #008BFF;
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
  display : flex;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};
`

const Instruction = styled.div`
  width : 25rem;
  overflow : scroll;
  padding : 0rem 1rem;
  margin : 1rem 1rem 0.5rem;
  border : 0.125rem solid rgba(0, 139, 255, 0.75);
  height : ${(props) => `${(props.height - 170) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const InstructionLoading = styled.div`
  width : 25rem;
  display : flex;
  padding : 0rem 1rem;
  align-items : center;
  justify-content : center;
  margin : 1rem 1rem 0.5rem;
  border : 0.125rem solid rgba(0, 139, 255, 0.75);
  height : ${(props) => `${(props.height - 170) / 16}rem`};
  
  img {
    width : 12.5rem;
    height : 5rem;
  }
`

const Before = styled.button`
  width : 2rem;
  border : none;
  height : 2rem;
  float : right;
  color : #008BFF;
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
  float : right;
  color : #008BFF;
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
  float : right;
  color : #FFFFFF;
  font-size : 1rem;
  font-weight : bold;
  background : #008BFF;
  margin-left : 0.5rem;
  border-radius : 1rem;
`

const Train = styled.div`
  display : flex;
  margin-top : 1rem;
  flex-direction : column;
  background : grey;
  width : ${(props) => `${(props.width - 792) / 16}rem`};
  height : ${(props) => `${(props.height - 170) / 16}rem`};
`

const CodeArea = styled.textarea`
  resize : none;
  padding : 1rem;
  font-size : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 826) / 16}rem`};
  height : ${(props) => `${(props.height - 440) / 16}rem`};
`

const Buttons_ = styled.div`
  margin : 0.425rem 0.025rem;
`

const Interpret = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  float : right;
  color : #008BFF;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  margin-left : 0.5rem;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Save = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  font-weight : bold;
  margin-left : 0.5rem;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Submit = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  margin : 0 0.5rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const CodeResult = styled.div`
  height : 9rem;
  background : #FFFFFF;
  border : 1px solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};

  p {
    margin : 0.5rem 1rem 0rem;
    color : rgba(0, 0, 0, 0.5);
  }
`

const ResultPre = styled.pre`
  height : 6.375rem;
`

const Result = styled.div`
  padding : 0 1rem;
  font-size : 1rem;
  height : 5.375rem;
  overflow : scroll;
  width : ${(props) => `${(props.width - 388) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const ChatbotSection = styled.div`
  width : 25rem;
  border-radius : 1rem;
  margin : 2rem 2rem 2rem 0;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Chat = styled.div`
  margin : 1rem;
  display : flex;
  overflow : scroll;
  flex-direction : column;
  height : ${(props) => `${(props.height - 277.5) / 16}rem`};

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
    border : 0.05rem solid rgba(0, 0, 0, 0.5);
    border-radius : 1.5rem 1.5rem 0rem 1.5rem;
  }
`

const Dialog_server = styled.div`
  div {
    color : #FFFFFF;
    margin : 0.5rem 0;
    padding : 0.75rem;
    width : fit-content;
    word-break : break-word;
    overflow-wrap : break-word;
    background : rgba(0, 139, 255, 0.75);
    border : 0.05rem solid rgba(0, 0, 0, 0.5);
    border-radius : 1.5rem 1.5rem 1.5rem 0rem;
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
  border-radius : 0 0 1rem 1rem;
`

const InputArea = styled.input`
  border : none;
  height : 2.5rem;
  width : 18.25rem;
  padding : 0 1rem;
  font-size : 1rem;
  border-radius : 1.25rem;
  margin : 0.375rem 0.75rem;
`

const InputButton = styled.button`
  width : 2.5rem;
  color : #FFFFFF;
  height : 2.5rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;
  border : 0.125rem solid #FFFFFF;
  margin : 0.375rem 0.625rem 0.375rem 0rem;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`