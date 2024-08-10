import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import React, { useRef, useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';


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

  const style = {color : '#008BFF', background : 'rgba(0, 139, 255, 0.25)'};
  const styled = {};
  const AddToNavContent = () => {
    if (navValue)
      setNavValue(false);
    else
      setNavValue(true);
  }


  const type_style = {background : 'rgba(0, 139, 255, 0.25)', borderRadius : '0.5rem'};
  const [typeValue1, setTypeValue1] = useState(false);
  const [typeValue2, setTypeValue2] = useState(false); 
  const [typeValue3, setTypeValue3] = useState(false);
  const [typeValue4, setTypeValue4] = useState(false);
  const selectType = () => {
    if(typeValue1) {
      setTypeValue1(false);
    }
    else {
      setTypeValue1(true);
    }

    if(typeValue2) {
      setTypeValue2(false);
    }
    else {
      setTypeValue2(true);
    }

    if(typeValue3) {
      setTypeValue3(false);
    }
    else {
      setTypeValue3(true);
    }

    if(typeValue4) {
      setTypeValue4(false);
    }
    else {
      setTypeValue4(true);
    }
  }

  const [dialog, setDialog] = useState("");
  const [dialogs, setDiv] = useState([]);
  const AddDialog = () => {
    if(dialog) {
      setDiv([...dialogs, <Dialog> {dialog} </Dialog>]);
      setDialog("");
    }
  }
  
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
            <NavLink style={{ textDecoration : "none" }} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
        <Dynamic>
          <Nav id="1" style={navValue ? style : styled} onClick={AddToNavContent}> 제 1장 </Nav>
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
      <ContentSection width={contentWidth}>
        <Instruction height={height}></Instruction>
        <Buttons>
          <Before> <img src={Left}></img> </Before>
          <After> <img src={Right}></img> </After>
          <GPT onClick={ShowGpt}> GPT </GPT>
        </Buttons>
      </ContentSection>
      {gptValue && (<ChatbotSection>
        <Chat height={height}>
          {dialogs.map(div => div)}
          <div ref={chat}></div>
        </Chat>
        <ChatType>
            <Type style={divValue === "개념"?borderStyle:{}} onClick={()=>getDivValue("개념")}> #개념 </Type>
            <Type style={divValue === "코드"?borderStyle:{}} onClick={()=>getDivValue("코드")}> #코드 </Type>
            <Type style={divValue === "오류"?borderStyle:{}} onClick={()=>getDivValue("오류")}> #오류 </Type>
        </ChatType>
          <ChatInput>
            <InputArea value={dialog} onChange={(e)=>setDialog(e.target.value)}></InputArea>
            <InputButton onClick={AddDialog}> <img src={Input}></img> </InputButton>
          </ChatInput>
        </ChatbotSection>)}
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
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};
`

const Instruction = styled.div`
  margin : 1rem 1rem 0.5rem;
  background : rgba(0, 0, 0, 0.25);
  height : ${(props) => `${(props.height - 200) / 16}rem`};
`

const Buttons = styled.div`
  float : right;
  padding : 0 1rem;
`

const Before = styled.button`
  width : 2rem;
  border : none;
  height : 2rem;
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
  color : #FFFFFF;
  font-size : 1rem;
  font-weight : bold;
  background : #008BFF;
  margin-left : 0.5rem;
  border-radius : 1rem;
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
  align-items : flex-end;
  flex-direction : column;
  height : ${(props) => `${(props.height - 277.5) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog = styled.p`
  margin : 0.5rem 0;
  width : fit-content;
  background : #FFFFFF;
  padding : 0.75rem 1rem;
  word-break : break-word;
  overflow-wrap : break-word;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  border-radius : 1.5rem 1.5rem 0rem 1.5rem;
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
  margin : 0.375rem 0;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;
  border : 0.125rem solid #FFFFFF;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`