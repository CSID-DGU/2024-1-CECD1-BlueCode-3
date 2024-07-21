import styled from 'styled-components';
import BCODE from '../../logo_b.png'
import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';


function Mainpage() {
  const [id, setId] = useState('');
  const [passwd, setPasswd] = useState('');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [birthday, setBirthday] = useState('');
  const [phonenum, setPhonenum] = useState('');
  const [pin, setPin] = useState('');

  const [SignInValue, setSignInValue] = useState(true);
  const [FindIdValue, setFindIdValue] = useState(false);
  const [CheckIdValue, setCheckIdValue] = useState(false);
  const [FindPasswdValue, setFindPasswdValue] = useState(false);
  const [ResetPasswdValue, setResetPasswdValue] = useState(false);
  const [CheckPasswdValue, setCheckPasswdValue] = useState(false);
  const [SignUpValue, setSignUpValue] = useState(false);
  const [CheckSignUpValue, setCheckSignUpValue] = useState(false);
  const [RequestPinValue, setRequestPinValue] = useState(false);

  const ChangeToSignIn = () => {
    document.getElementById("SectionName").innerText = "아이디찾기";

    setSignInValue(true);
    setFindIdValue(false);
    setFindPasswdValue(false);
    setSignUpValue(false);
    setCheckIdValue(false);
    setCheckPasswdValue(false);
    setCheckSignUpValue(false);
  }

  const ChangeToFindId = () => {
    document.getElementById("SectionName").innerText = "아이디찾기";

    setSignInValue(false);
    setFindIdValue(true);
    setFindPasswdValue(false);
    setSignUpValue(false);
  }

  const ChangeToFindPasswd = () => {
    document.getElementById("SectionName").innerText = "비밀번호찾기";

    setCheckIdValue(false);
    setSignInValue(false);
    setFindIdValue(false);
    setFindPasswdValue(true);
    setSignUpValue(false);
  }

  const ChangeToSignUp = () => {
    document.getElementById("SectionName").innerText = "회원가입";

    setSignInValue(false);
    setFindIdValue(false);
    setFindPasswdValue(false);
    setSignUpValue(true);
  }

  const ChangeToVerify = () => {
    if (ResetPasswdValue === false && RequestPinValue === false)
    {
      document.getElementById("RequestName").innerText = "인 증 하 기";
      setRequestPinValue(true);
    }
    else if (ResetPasswdValue && RequestPinValue === false)
    {
      setResetPasswdValue(false);
      setCheckPasswdValue(true);
    }
    else
    {
      if (FindIdValue)
      {
        setFindIdValue(false);
        setCheckIdValue(true);
      }
      else if(FindPasswdValue)
      {
        setFindPasswdValue(false);
        setResetPasswdValue(true);
      }
      else
      {
        setSignUpValue(false);
        setCheckSignUpValue(true);
      }

      document.getElementById("RequestName").innerText = "인 증 요 청";
      setRequestPinValue(false);
    }
  }

  return (
    <MainpageSection>
      <Section>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
        </Logo>
        <SectionName id="SectionName"> 로그인 </SectionName>
        
        {SignInValue && (<ChangingSection>
          <InputArea type="text" placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)}></InputArea>
          <InputArea type="password" placeholder="비밀번호" value={passwd} onChange={(e)=>setPasswd(e.target.value)}></InputArea>
          <Button> 로 그 인 </Button>
          <Others>
            <p onClick={ChangeToFindId}> 아이디 찾기 </p>
            <p onClick={ChangeToFindPasswd}> 비밀번호 찾기 </p>
            <p onClick={ChangeToSignUp}> 회원가입 </p>
          </Others>
        </ChangingSection>)}

        {FindIdValue && (<ChangingSection>
          <InputArea type="text"  placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="이메일" value={email} onChange={(e)=>setEmail(e.target.value)} ></InputArea>
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)}></InputArea>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 인 증 요 청 </Button>
        </ChangingSection>)}

        {CheckIdValue && (<ChangingSection>
          <Explaination> 아이디는 다음과 같습니다. </Explaination>
          <Id>  </Id>
          <ButtonArea>
            <Btn onClick={ChangeToSignIn}> 로그인하기 </Btn>
            <Btn onClick={ChangeToFindPasswd}> 비밀번호찾기 </Btn>
          </ButtonArea>
        </ChangingSection>)}
        
        {FindPasswdValue && (<ChangingSection>
          <InputArea type="text" placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="이메일" value={email} onChange={(e)=>setEmail(e.target.value)}></InputArea>
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)}></InputArea>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 인 증 요 청 </Button>
        </ChangingSection>)}

        {ResetPasswdValue && (<ChangingSection>
          <InputArea type="passwd" placeholder="새 비밀번호" value={id} onChange={(e)=>setId(e.target.value)}></InputArea>
          <InputArea type="passwd" placeholder="새 비밀번호 확인" value={email} onChange={(e)=>setEmail(e.target.value)}></InputArea>
          <Button id="RequestName" onClick={ChangeToVerify}> 확 인 </Button>
        </ChangingSection>)}

        {CheckPasswdValue && (<ChangingSection>
          <Explaination> 비밀번호 재설정이 완료되었습니다. </Explaination>
          <Button id="RequestName" onClick={ChangeToSignIn}> 로그인 페이지로 </Button>
        </ChangingSection>)}

        {SignUpValue && (<ChangingSection>
          <InputArea type="text" placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)}></InputArea>
          <InputArea type="passwd" placeholder="비밀번호" value={passwd} onChange={(e)=>setPasswd(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="이메일" value={passwd} onChange={(e)=>setEmail(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="이름" value={name} onChange={(e)=>setName(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="생년월일 8자리" value={birthday} onChange={(e)=>setBirthday(e.target.value)}></InputArea>
          <InputArea type="text" placeholder="전화번호" value={phonenum} onChange={(e)=>setPhonenum(e.target.value)}></InputArea>
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)}></InputArea>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 인 증 요 청 </Button>
        </ChangingSection>)}

        {CheckSignUpValue && (<ChangingSection>
          <Explaination> 회원가입이 완료되었습니다. </Explaination>
          <Button id="RequestName" onClick={ChangeToSignIn}> 로그인 페이지로 </Button>
        </ChangingSection>)}

      </Section>
    </MainpageSection>
  );
}

export default Mainpage;



const MainpageSection = styled.div`
  height : 100vh;
  background : #008BFF;
`

const Section = styled.div`
  float : right; 
  height : 100vh;
  width : 32.5rem;
  background : #FFFFFF;
  border-radius : 2.5rem  0 0 2.5rem;
`

const Logo = styled.div`
  display : flex;
  margin : 4rem auto 1.125rem auto;
  justify-content : center;

  img {
    width : 32.5%;
    height : 32.5%;
  }
`

const SectionName = styled.p`
  display : flex;
  margin : 1rem auto ;
  color : #008BFF;
  font-weight : bold;
  font-size : 1.125rem;
  justify-content : center;
`

const ChangingSection = styled.div`
  display : flex;
  flex-direction : column;
`

const InputArea = styled.input`
  width : 18.5rem;
  height : 1.5rem;
  padding : 0.75rem;
  font-size : 1.25rem;
  border-radius : 0.5rem;
  margin : 0.125rem auto 0.625rem auto;
  border : 0.05rem ridge rgba(0, 0, 0, 0.5);
`

const Explaination = styled.p`
  display : flex;
  margin : 1rem auto ;
  color : #008BFF;
  font-weight : bold;
  font-size : 1.125rem;
  justify-content : center;
`

const Id = styled.p`
  display : flex;
  margin : 1rem auto ;
  color : #000000;
  font-weight : bold;
  font-size : 1.125rem;
  justify-content : center;
`

const ButtonArea = styled.div`
  width : 20rem;
  margin : 1.75rem auto 0.75rem auto;`

const Btn = styled.button`
  border : none;
  width : 9.5rem;
  color : #FFFFFF;
  height : 3rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : auto 0.25rem;
`

const Button = styled.button`
  height : 3rem;
  border : none;
  width : 20rem;
  color : #FFFFFF;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : 1.75rem auto 0.75rem auto;
`

const Others = styled.div`
  width : 25rem;
  margin : 0 auto;
  text-align : center;

  p {
    opacity : 0.5;
    font-weight : bold;
    font-size : 0.875rem;
    display : inline-block;
    margin : 0 1.5rem 0 1.5rem;
  }
`