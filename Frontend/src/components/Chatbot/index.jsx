import styled from 'styled-components';
import BCODE from '../../logo_w.png'
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

  const [contentWidth, setContentWidth] = useState(width);

  const [point, setPoint] = useState(0);
  const [process, setProcess] = useState(0);
  const color = {color : "#008BFF"};


  const [dialog, setDialog] = useState("");
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
            <NavLink style={{ textDecoration : "none" }} to="/chatbot"><Nav style={color}> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 내역 </InfoNav>
            <p> 구체적인 질문 내역 </p>
          </Info>
          <Dynamic>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
            <Chat height={height}>
              {div.map(div => div)}
            </Chat>
            <ChatType>
              <Type> #개념 </Type>
              <Type> #코드 </Type>
              <Type> #오류 </Type>
            </ChatType>
            <ChatInput>
              <InputArea width={contentWidth} value={dialog} onChange={(e)=>setDialog(e.target.value)}></InputArea>
              <InputButton onClick={AddDialog}> <img src={Input}></img> </InputButton>          
            </ChatInput>
        </ContentSection>
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

const Info = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  
  p {
    margin : 0;
    padding : 0.625rem;
    text-align : right;
  }
`

const InfoNav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
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

const ContentSection = styled.div`
  margin : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};
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
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  border-radius : 1.5rem 1.5rem 0rem 1.5rem;
`

const ChatType = styled.div`
  height : 3rem;
  display : flex;
  align-item : center;
  justify-content : right;
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
    background : rgba(0, 139, 255, 0.25);
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
  padding : 0 1rem;
  font-size : 1rem;
  border-radius : 1.25rem;
  margin : 0.375rem 0.75rem;
  width : ${(props) => `${(props.width - 416) / 16}rem`};
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