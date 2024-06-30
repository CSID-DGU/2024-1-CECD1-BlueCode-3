import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import React, { useState } from 'react';
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
  const [dialog, setDialog] = useState("");

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

  const [div, setDiv] = useState([]);
  const AddDialog = () => {
    setDiv([...div, <Dialog> {dialog} </Dialog>]);
    setDialog("");
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
            <NavLink style={{ textDecoration : "none" }} to="/Mypage"><Nav> 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> 로그아웃 </Nav></NavLink>
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
          {div.map(div => div)}
        </Chat>
        <ChatType>
          <Type style={typeValue1 ? type_style : styled} onClick={selectType}> #이론 </Type>
          <Type style={typeValue2 ? type_style : styled} onClick={selectType}> #문법 </Type>
          <Type style={typeValue3 ? type_style : styled} onClick={selectType}> #코드 </Type>
          <Type style={typeValue4 ? type_style : styled} onClick={selectType}> #오류 </Type>
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
  width : 15rem;
  display : flex;
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
  flex-direction : column;
  font-weight : bold;
  padding : 0.625rem;
  align-items : center;
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
  flex-direction : column;
  font-weight : bold;
  padding : 0.375rem;
  align-items : left;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
  }
`

const Dynamic = styled.div`
  padding : 0.625rem;
  overflow : scroll;
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
  background : rgba(0, 0, 0, 0.25);
  margin : 1rem 1rem 0.5rem;
  height : ${(props) => `${(props.height - 200) / 16}rem`};
`

const Buttons = styled.div`
  float : right;
  padding : 0 1rem;
`

const Before = styled.button`
  border : none;
  width : 2rem;
  margin : 0 0.25rem;
  color : #008BFF;
  height : 2rem;
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
  border : none;
  width : 2rem;
  color : #008BFF;
  margin : 0 0.25rem;
  height : 2rem;
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
  border : none;
  width : 4rem;
  color : #FFFFFF;
  height : 2rem;
  font-weight : bold;
  font-size : 1rem;
  background : #008BFF;
  border-radius : 1rem;
  margin-left : 0.5rem;
`

const ChatbotSection = styled.div`
  margin : 2rem 2rem 2rem 0;
  width : 25rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Chat = styled.div`
  margin : 1rem;
  height : ${(props) => `${(props.height - 277.5) / 16}rem`};
  overflow : scroll;
  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog = styled.p`
  margin : 0.5rem 0;
  padding : 0.875rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  border-radius : 1rem 0rem 1rem 1rem;
  background : #FFFFFF;
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
  font-weight : bold;
  padding : 0.25rem 0.5rem;
  margin : 0.5rem 1rem;
  color : #008BFF;

  &:hover {
    background : rgba(0, 139, 255, 0.25);
    border-radius : 0.5rem;
  }
`

const ChatInput = styled.div`
  display : flex;
  height : 3.5rem;
  background : #008BFF;
  border-radius : 0 0 1rem 1rem;
`

const InputArea = styled.input`
  margin : 0.375rem 0.75rem;
  font-size : 1rem;
  padding : 0 1rem;
  height : 2.5rem;
  width : 18.25rem;
  border : none;
  border-radius : 1.25rem;
`

const InputButton = styled.button`
  border : 0.125rem solid #FFFFFF;
  width : 2.5rem;
  color : #FFFFFF;
  margin : 0.375rem 0;
  height : 2.5rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`