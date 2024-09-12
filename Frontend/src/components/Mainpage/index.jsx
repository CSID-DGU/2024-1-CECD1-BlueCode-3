import styled from 'styled-components';
import BCODE from '../../logo_b.png'
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { SHA256 } from 'crypto-js';


function Mainpage() {
  const [id, setId] = useState('');
  const [passwd, setPasswd] = useState('');
  const [newPasswd, setNewPasswd] = useState('');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [birthday, setBirthday] = useState('');
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

  const idRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,16}$/;
  const passwdRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^~*])[A-Za-z\d!@#$%^~*]{9,16}$/;
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const birthdayRegex = /^(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$/;

  const [idValid, setIdValid] = useState(true);
  const [passwdValid, setPasswdValid] = useState(true);
  const [passwdEqual, setPasswdEqual] = useState(true);
  const [emailValid, setEmailValid] = useState(true);
  const [birthdayValid, setBirthdayValid] = useState(true);
  
  const [authValid, setAuthValid] = useState(false);
  
  const [idDup, setIdDup] = useState(false);
  const [emailDup, setEmailDup] = useState(false);

  const idBlur = async () => {
    if (idRegex.test(id)) {
      try{
        setIdValid(true);
        const res = await axios.get(`/user/user/exists/id/${id}`);
        if(res.data){
          setIdDup(true);
        }
        else{
          setIdDup(false);
        }
      }
      catch(err) {
        console.log(err);
      }
    }
    else if(id === '') {
      setIdValid(true);
    }
    else {
      setIdValid(false);
      setIdDup(false);
    }
  };

  const passwdBlur = () => {
    if (passwdRegex.test(passwd) || passwd === '') {
      setPasswdValid(true);
    }
    else {
      setPasswdValid(false);
    }
  };

  const emailBlur = async () => {
    if (emailRegex.test(email)) {
      try {
        setEmailValid(true);
        const res = await axios.get(`/user/user/exists/email/${email}`);
        if(res.data) {
          setEmailDup(true);
        }
        else {
          setEmailDup(false);
        }
      }
      catch (err) {
        console.log(err);
      }
    }
    else if(email === '') {
      setEmailValid(true);
      setEmailDup(false);
    }
    else {
      setEmailValid(false);
      setEmailDup(false);
    }
  };

  const birthdayBlur = () => {
    if (birthdayRegex.test(birthday)) {
      setBirthdayValid(true);
    }
    else if(birthday === '') {
      setBirthdayValid(true);
    }
    else {
      setBirthdayValid(false);
    }
  };

  const passwdEqualBlur = () => {
    if (passwd === newPasswd || newPasswd === '')
      setPasswdEqual(true);
    else
      setPasswdEqual(false);
  }

  const ChangeToSignIn = () => {
    document.getElementById("SectionName").innerText = "로그인";
    
    setId('');

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

    setEmailDup(true);
  }

  const ChangeToFindPasswd = () => {
    document.getElementById("SectionName").innerText = "비밀번호찾기";

    setId('');

    setCheckIdValue(false);
    setSignInValue(false);
    setFindIdValue(false);
    setFindPasswdValue(true);
    setSignUpValue(false);

    setIdDup(true);
    setEmailDup(true);
  }

  const ChangeToSignUp = () => {
    document.getElementById("SectionName").innerText = "회원가입";

    setId('');

    setSignInValue(false);
    setFindIdValue(false);
    setFindPasswdValue(false);
    setSignUpValue(true);
  }

  const ChangeToVerify = async () => {
    if (((name && email && emailValid && FindIdValue) || (id && email && idValid && emailValid && FindPasswdValue)
        || (id && passwd && email && name && birthday && idValid && passwdValid && emailValid && birthdayValid && SignUpValue))
        && !pin)
    {
      try {

        //url path 수정 필요
        axios.get(`/user/user/email/send/${email}`);

        document.getElementById("RequestName").innerText = "인 증 하 기";
        setRequestPinValue(true);
      }
      catch (err) {
        console.log(err);
      }
    }
    else if (ResetPasswdValue && passwd && passwdEqual)
    {
      try{
        const hash_passwd = SHA256(passwd).toString();
        const UserAddCallDto = {
          'username' : '',
          'email' : '',
          'id' : id,
          'password' : hash_passwd,
          'birth' : ''
        };
        
        await axios.post("/user/user/updatePassword", UserAddCallDto);
      }
      catch (err) {
        console.log(err);
      }

      setPasswd('');
      setResetPasswdValue(false);
      setCheckPasswdValue(true);
    }
    else
    {
      if (FindIdValue && pin)
      {
        try {
          const EmailVerifyDto = {
            'email' : email,
            'code' : pin
          };
          const res = await axios.post("/user/user/email/verify", EmailVerifyDto);
          
          if(res.data) {
            try {
              const UserAddCallDto = {
                'username' : name,
                'email' : email,
                'id' : '',
                'password' : '',
                'birth' : ''
              };
              
              const res = await axios.post("/user/user/findId", UserAddCallDto);

              setId(res.data);

              setName('');
              setEmail('');
              setPin('');
      
              setFindIdValue(false);
              setCheckIdValue(true);
              
              document.getElementById("RequestName").innerText = "인 증 요 청";
              setRequestPinValue(false);
            }
            catch (err) {
              console.log(err);
            }
          } 
          else {
            alert("인증번호가 일치하지 않습니다.");
          }      
        }
        catch (err) {
          console.log(err);
        }
      }
      else if(FindPasswdValue && pin)
      {
        try {
          const EmailVerifyDto = {
            'email' : email,
            'code' : pin
          };
          const res = await axios.post("/user/user/email/verify", EmailVerifyDto);
          
          if(res.data) {
            try {
              setId('');
              setEmail('');
              setPin('');

              setFindPasswdValue(false);
              setResetPasswdValue(true);

              document.getElementById("RequestName").innerText = "인 증 요 청";
              setRequestPinValue(false);
            }
            catch (err) {
              console.log(err);
            }
          } 
          else {
            alert("인증번호가 일치하지 않습니다.");
          }      
        }
        catch (err) {
          console.log(err);
        }
      }
      else if(SignUpValue && pin)
      {
        try {
          const EmailVerifyDto = {
            'email' : email,
            'code' : pin
          };
          const res = await axios.post("/user/user/email/verify", EmailVerifyDto);
          
          if(res.data) {
            try {
              const hash_passwd = SHA256(passwd).toString();
              const UserAddCallDto = {
                'username' : name,
                'email' : email,
                'id' : id,
                'password' : hash_passwd,
                'birth' : birthday
              };
              
              const res=await axios.post("/user/user/create", UserAddCallDto);
              const userTableId=res.data;
              //초기 미션 할당
              try {
                const UserMissionDataCallDto = {
                  'userId' : userTableId
                  };
                await axios.post('/mission/mission/init', UserMissionDataCallDto);
              }
              catch(err){
                console.log(err);
              }

              setId('');
              setPasswd('');
              setEmail('');
              setName('');
              setBirthday('');
              setPin('');
  
              setSignUpValue(false);
              setCheckSignUpValue(true);
  
              document.getElementById("RequestName").innerText = "인 증 요 청";
              setRequestPinValue(false);
            }
            catch (err) {
              console.log(err);
            }
          } 
          else {
            alert("인증번호가 일치하지 않습니다.");
          }      
        }
        catch (err) {
          console.log(err);
        }        
      }
    }
  }

  const navigate = useNavigate();
  const SignIn = async () => {
    if(!id){
      alert("아이디를 입력해주세요");
    }
    else if(!passwd){
      alert("비밀번호를 입력해주세요");
    }
    else{
      try {
        const hash_passwd = SHA256(passwd).toString();
        const LoginCallDto = {
          'id' : id,
          'password' : hash_passwd
        };

        //url path 수정 필요
        const res = await axios.post("/api/api/auth/login", LoginCallDto);
        const accessToken = res.data.accessToken;
        const userid = res.data.userid;
        
        localStorage.setItem("userid", userid);
        localStorage.setItem("accessToken", accessToken);
        //현재는 파이썬만
        localStorage.setItem("rootid", 1);
        navigate('/mypage/todo');

 
      }
      catch (err) {
        navigate('/');
        alert("입력하신 정보가 일치하지 않습니다.");
      }
    }
  }

  
  const getStatus = (text, textValid, textDup) => {
    if (text === '') {
      return 'default';
    }
    else {
      return textValid && !textDup ? 'valid' : 'invalid';
    }
  };

  const getStatusR = (text, textValid, textDup) => {
    if (text === '') {
      return 'default';
    }
    else {
      return textValid && textDup ? 'valid' : 'invalid';
    }
  };

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
          <Button onClick={SignIn}> 로 그 인 </Button>
          <Others>
            <p onClick={ChangeToFindId}> 아이디 찾기 </p>
            <p onClick={ChangeToFindPasswd}> 비밀번호 찾기 </p>
            <p onClick={ChangeToSignUp}> 회원가입 </p>
          </Others>
        </ChangingSection>)}

        {FindIdValue && (<ChangingSection>
          <InputArea type="text"  placeholder="이름" value={name} onChange={(e)=>setName(e.target.value)} status={getStatus(name, true, false)}></InputArea>
          <InputArea type="text" placeholder="이메일" value={email} onChange={(e)=>setEmail(e.target.value)} onBlur={emailBlur} status={getStatus(email, emailValid, !emailDup)}></InputArea>
          {!emailValid && (<RegexCheck> 옳지 않은 이메일입니다. </RegexCheck>)}
          {email && emailValid && !emailDup && (<RegexCheck> 서비스에 등록되지 않은 이메일입니다. </RegexCheck>)}
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)} status={getStatus(pin, true, false)}></InputArea>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 인 증 요 청 </Button>
        </ChangingSection>)}

        {CheckIdValue && (<ChangingSection>
          <Explaination> 아이디는 다음과 같습니다. </Explaination>
          <Id> {id} </Id>
          <ButtonArea>
            <Btn onClick={ChangeToSignIn}> 로그인하기 </Btn>
            <Btn onClick={ChangeToFindPasswd}> 비밀번호찾기 </Btn>
          </ButtonArea>
        </ChangingSection>)}
        
        {FindPasswdValue && (<ChangingSection>
          <InputArea type="text" placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)} onBlur={idBlur} status={getStatus(id, idValid, false)}></InputArea>
          {id && !idDup && (<RegexCheck> 서비스에 등록되지 않은 아이디입니다. </RegexCheck>)}
          <InputArea type="text" placeholder="이메일" value={email} onChange={(e)=>setEmail(e.target.value)} onBlur={emailBlur} status={getStatus(email, emailValid, !emailDup)}></InputArea>
          {!emailValid && (<RegexCheck> 옳지 않은 이메일입니다. </RegexCheck>)}
          {email && emailValid && !emailDup && (<RegexCheck> 서비스에 등록되지 않은 이메일입니다. </RegexCheck>)}
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)} status={getStatus(pin, true, false)} ></InputArea>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 인 증 요 청 </Button>
        </ChangingSection>)}

        {ResetPasswdValue && (<ChangingSection>
          <InputArea type="password" placeholder="새 비밀번호" value={passwd} onChange={(e)=>setPasswd(e.target.value)} onBlur={passwdBlur} status={getStatus(passwd, passwdValid, false)}></InputArea>
          <RegexCheck> 비밀번호: 9~16자의 영문, 숫자, 특수문자(!@#$%^~*) 조합 </RegexCheck>
          {!passwdValid && (<RegexCheck> 옳지 않은 비밀번호입니다. </RegexCheck>)}
          <InputArea type="password" placeholder="새 비밀번호 확인" value={newPasswd} onChange={(e)=>setNewPasswd(e.target.value)} onBlur={passwdEqualBlur} status={getStatus(newPasswd, passwdEqual, false)}></InputArea>
          {!passwdEqual && (<RegexCheck> 비밀번호가 일치하지 않습니다. </RegexCheck>)}
          <Button id="RequestName" onClick={ChangeToVerify}> 확 인 </Button>
        </ChangingSection>)}

        {CheckPasswdValue && (<ChangingSection>
          <Explaination> 비밀번호 재설정이 완료되었습니다. </Explaination>
          <Button id="RequestName" onClick={ChangeToSignIn}> 로그인 페이지로 </Button>
        </ChangingSection>)}

        {SignUpValue && (<ChangingSection>
          <InputArea type="text" placeholder="아이디" value={id} onChange={(e)=>setId(e.target.value)} onBlur={idBlur} status={getStatus(id, idValid, idDup)}></InputArea>
          <RegexCheck style={{ color : "#008BFF" }}> 아이디: 5~16자의 영문, 숫자 조합 </RegexCheck>
          {!idValid && (<RegexCheck> 옳지 않은 아이디입니다. </RegexCheck>)}
          {idDup && (<RegexCheck> 사용불가한 아이디입니다. </RegexCheck>)}
          <InputArea type="password" placeholder="비밀번호" value={passwd} onChange={(e)=>setPasswd(e.target.value)} onBlur={passwdBlur} status={getStatus(passwd, passwdValid, false)}></InputArea>
          <RegexCheck style={{ color : "#008BFF" }}> 비밀번호: 9~16자의 영문, 숫자, 특수문자(!@#$%^~*) 조합 </RegexCheck>
          {!passwdValid && (<RegexCheck> 옳지 않은 비밀번호입니다. </RegexCheck>)}
          <InputArea type="text" placeholder="이메일" value={email} onChange={(e)=>setEmail(e.target.value)} onBlur={emailBlur} status={getStatus(email, emailValid, emailDup)}></InputArea>
          {!emailValid && (<RegexCheck> 옳지 않은 이메일입니다. </RegexCheck>)}
          {emailDup && (<RegexCheck> 사용불가한 이메일입니다. </RegexCheck>)}
          <InputArea type="text" placeholder="이름" value={name} onChange={(e)=>setName(e.target.value)} status={getStatus(name, true, false)}></InputArea>
          <InputArea type="text" placeholder="생년월일 8자리" value={birthday} onChange={(e)=>setBirthday(e.target.value)} onBlur={birthdayBlur} status={getStatus(birthday, birthdayValid, false)}></InputArea>
          {!birthdayValid && (<RegexCheck> 옳지 않은 생년월일입니다. </RegexCheck>)}
          {RequestPinValue && (<InputArea type="text" placeholder="인증번호" value={pin} onChange={(e)=>setPin(e.target.value)} status={getStatus(pin, true, false)}></InputArea>)}
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



const RegexCheck = styled.p`
  display : flex;
  width : 20rem;
  margin : 0 auto ;
  color : #FD0100;
  font-weight : bold;
  font-size : 0.75rem;
  justify-content : left;
`

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
  margin : 0.25rem auto 0.25rem;

  ${({status}) => {
    switch (status) {
      case 'default':
        return `border : 0.1rem solid rgba(0, 0, 0, 0.5)`;
      case 'invalid':
        return `border : 0.1rem solid #FD0100`;
      case 'valid':
        return `border : 0.1rem solid #008BFF`;
    }
  }}
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