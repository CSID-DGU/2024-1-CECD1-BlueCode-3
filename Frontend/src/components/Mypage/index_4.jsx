import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { SHA256 } from 'crypto-js';


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
  
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const passwdRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^~*])[A-Za-z\d!@#$%^~*]{9,16}$/;

  const [email, setEmail] = useState('example@example.com');
  const [newEmail, setNewEmail] = useState('');
  const [newPasswd, setNewPasswd] = useState('');
  const [newPasswdCheck, setNewPasswdCheck] = useState('');

  const [passwdValid, setPasswdValid] = useState(true);
  const [passwdEqual, setPasswdEqual] = useState(true);
  const [emailValid, setEmailValid] = useState(true);
  
  const newEmailBlur = () => {
    if (emailRegex.test(newEmail)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const newPasswdBlur = () => {
    if (passwdRegex.test(newPasswd)) {
      setPasswdValid(true);
    } else {
      setPasswdValid(false);
    }
  };

  const passwdEqualBlur = () => {
    if (newPasswd === newPasswdCheck)
      setPasswdEqual(true);
    else
      setPasswdEqual(false);
  }

  const hash = SHA256(newPasswd).toString();
  // <p> - 3개 이상 동일한 문자 / 숫자 제외, 연속적인 숫자나 생일은 제외 </p>
  // <p> - 아이디 및 이메일 제외 </p>

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
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {process} % </p> </InfoNav>
            <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
          </Info>
          <Dynamic>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav> ㅇ 내 할일 관련 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/lecture"><Nav> ㅇ 내 강의 정보 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/question"><Nav> ㅇ 내 질문 정보 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/info"><Nav style={color}> ㅇ 내 정보 수정 </Nav></NavLink>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <PrivateInfo>
            <SubInfo>
              <Element> ㅇ 아이디 </Element>
              <SubElement> 구체적인 아이디 </SubElement>
            </SubInfo>
            <SubInfo>
              <Element> ㅇ 이름 </Element>
              <SubElement> 구체적인 이름 </SubElement>
            </SubInfo>
          </PrivateInfo>
          <PrivateInfo>
            <SubInfo>
              <Element> ㅇ 생년월일 </Element>
              <SubElement> 구체적인 생년월일 </SubElement>
            </SubInfo>
            <SubInfo>
              <Element> ㅇ 회원가입 일자 </Element>
              <SubElement> 구체적인 회원가입 일자 </SubElement>
            </SubInfo>
          </PrivateInfo>
          <ChangingInfo>
            <Email> ㅇ 이메일 </Email>
            <ChangingSection>
              <InputArea  type="text" placeholder={email} value={newEmail} onChange={(e)=>setNewEmail(e.target.value)} onBlur={newEmailBlur}></InputArea>
              <Button> 이메일 변경 </Button>
              <p id="info"> - 인증번호 발급을 위한 이메일 작성 </p>
              {!emailValid && (<p style={{color : "#008BFF", fontWeight : "bold"}}> 옳지 않은 이메일입니다. </p>)}
            </ChangingSection>
          </ChangingInfo>
          <ChangingInfo>
            <Passwd> ㅇ 비밀번호 </Passwd>
            <ChangingSection>
              <InputArea type="password" placeholder="새 비밀번호" value={newPasswd} onChange={(e)=>setNewPasswd(e.target.value)} onBlur={newPasswdBlur}></InputArea>
              <InputArea type="password" placeholder="새 비밀번호 확인" value={newPasswdCheck} onChange={(e)=>setNewPasswdCheck(e.target.value)} onBlur={passwdEqualBlur}></InputArea>
              <Button> 비밀번호 변경 </Button>
              <p id="info"> - 9~16자의 영문, 숫자, 특수문자(!@#$%^~*) 조합 </p>
              {!passwdValid && (<p style={{color : "#008BFF", fontWeight : "bold"}}> 옳지 않은 비밀번호입니다. </p>)}
              {!passwdEqual && (<p style={{color : "#008BFF", fontWeight : "bold"}}> 비밀번호가 일치하지 않습니다. </p>)}
            </ChangingSection>
          </ChangingInfo>
        </ContentSection>
      </Content>
    </TestSection>
  );
}

export default Study_theory;



const RegexCheck = styled.p`
  display : flex;
  width : 20rem;
  margin : 0 auto ;
  color : #008BFF;
  font-weight : bold;
  font-size : 0.75rem;
  justify-content : left;
`

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
  font-weight : bold;
  padding : 0.625rem;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const InfoNav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  p {
    margin : 0;
    text-align : right;
  }
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
  display : flex;
  padding : 2rem;
  border-radius : 1rem;
  align-items : center;
  flex-direction : column;
  justify-content : center;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const PrivateInfo = styled.div`
  width : 45rem;
  display : flex;
  margin : auto 0;
`

const SubInfo = styled.div`
  display : flex;
  flex-direction : column;
`

const Element = styled.p`
  margin : 0;
  width : 22.5rem;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const SubElement = styled.p`
  font-weight : bold;
  color : rgba(0, 0, 0, 0.75);
  margin : 0.75rem 1.375rem 0.25rem;
`

const ChangingInfo = styled.div`
width : 45rem;
display : flex;
margin : auto 0;
flex-direction : column;
`

const Email = styled.p`
  margin : 0;
  width : 45rem;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const Passwd = styled.p`
  margin : 0;
  width : 45rem;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const ChangingSection = styled.div`
  p {
    font-size : 0.75rem;
    color : rgba(0, 0, 0, 0.375);
    margin : 0.25rem 1.375rem 0rem;
  }
`

const InputArea = styled.input`
  width : 13.5rem;
  height : 1rem;
  padding : 0.75rem;
  font-size : 1rem;
  font-weight : bold;
  border-radius : 0.5rem;
  margin : 0.25rem 0.5rem;
  border : 0.05rem ridge rgba(0, 0, 0, 0.5);
`

const Button = styled.button`
  border : none;
  width : 8.75rem;
  color : #FFFFFF;
  font-size : 1rem;
  height : 2.625rem;
  font-weight : bold;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : 0.25rem 0.5rem;
`