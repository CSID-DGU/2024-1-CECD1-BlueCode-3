import axios from 'axios';
import { SHA256 } from 'crypto-js';
import styled from 'styled-components';
import axiosInstance from '../../axiosInstance';
import React, { useState, useEffect } from 'react';



function PrivateInfoJsx() {
  
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const passwdRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^~*])[A-Za-z\d!@#$%^~*]{9,16}$/;

  const [id, setId] = useState('');
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [newEmail, setNewEmail] = useState('');
  const [birthday, setBirthday] = useState('');
  const [signInDate, setSignInDate] = useState('');
  const [newPasswd, setNewPasswd] = useState('');
  const [newPasswdCheck, setNewPasswdCheck] = useState('');

  const [emailValid, setEmailValid] = useState(false);
  const [passwdValid, setPasswdValid] = useState(false);
  
  const newEmailBlur = async () => {
    if (!newEmail) {
      setEmailValid(false);
      document.getElementById("emailInfo").innerText = "";
    }
    else if (emailRegex.test(newEmail)) {
      try{
        const res = await axios.get(`/user/user/exists/email/${newEmail}`);

        if (res.data) {  
          setEmailValid(false);
          document.getElementById("emailInfo").innerText = "이미 서비스에 등록된 이메일입니다.";
        }
        else {
          setEmailValid(true);
          document.getElementById("emailInfo").innerText = "";
        }
      } catch (err) {
        console.log(err);
      }
    }
    else {
      setEmailValid(false);
      document.getElementById("emailInfo").innerText = "옳지 않은 이메일입니다.";
    }
  };

  const changeEmail = async () => {
    if (emailValid) {
      try {
        const userid = localStorage.getItem('userid');
        const email_data = {
          'userId' : userid,
          'email': newEmail
        };
        const res = await axiosInstance.post('/checkAuth/checkAuth/updateEmail', email_data);
        alert(res.data);
      }
      catch (err) {
        console.log(err);
      }
    }
  }

  const newPasswdBlur = () => {
    if (passwdRegex.test(newPasswd) || !newPasswd) {
      document.getElementById("passwdInfo").innerText = "";
    } 
    else {
      document.getElementById("passwdInfo").innerText = "옳지 않은 비밀번호입니다.";
    }
  };

  const passwdEqualBlur = () => {
    if (!newPasswdCheck) {
      setPasswdValid(false);
      document.getElementById("passwdInfo").innerText = "";
    }
    else if (newPasswd === newPasswdCheck) {
      setPasswdValid(true);
      document.getElementById("passwdInfo").innerText = "";
    }
    else {
      setPasswdValid(false);
      document.getElementById("passwdInfo").innerText = "비밀번호가 일치하지 않습니다.";
    }
  }

  const changePasswd = async () => {
    if (passwdValid) {
      try {
        const hash_passwd = SHA256(newPasswd).toString();
        const userid = localStorage.getItem('userid');
        const password_data = {
          'userId' : userid,
          'password': hash_passwd
        };
        const res = await axiosInstance.post('/checkAuth/checkAuth/updatePassword', password_data);
        alert(res.data);
      }
      catch (err) {
        console.log(err);
      }
    }
  }

  useEffect(() => {
    // 서버에서 사용자 정보를 가져오는 함수
    const getUserInfo = async () => {
      try {
        const userid = localStorage.getItem('userid');
        const UserIdDto = {
          'userId' : userid
        };

        const res = await axiosInstance.post('/checkAuth/checkAuth/getUserInfo', UserIdDto);
        setId(res.data.id);
        setEmail(res.data.email);
        setBirthday(setFormat(res.data.birth));
        setName(res.data.username);

        const rdate = res.data.registerDateTime;
        setSignInDate(rdate.substr(0, 4) + "년 " + rdate.substr(5, 2) + "월 " + rdate.substr(8, 2) + "일");
      }
      catch (err){
        console.error(err); 
      }
    };
    
    getUserInfo(); // 데이터를 불러오는 함수 호출
  }, []);
    
  const setFormat = (text) => {
    const year = text.substr(0, 4);
    const month = text.substr(4, 2);
    const day = text.substr(6, 2);

    return year + "년 " + month + "월 " + day + "일";
  }

  const [menu, setMenu] = useState(false);
  const showMenu = () => {
    if (menu) {
      setMenu(false);
      setNewEmail('');
      setNewPasswd('');
      setNewPasswdCheck('');
    } else {
      setMenu(true);
    }
  }

  const color = { color : "#008BFF", fontWeight : "bold" };

  return (<>
    {!menu?
    <SectionBarMenu onClick={showMenu}> 내 정보 확인 </SectionBarMenu>
    :
    <NavSection>
      <NavContent>
        <SectionBar>
          <p onClick={showMenu}> 내 정보 확인 </p>
        </SectionBar>
        <ContentSection>
          <SubInfo>
            <Element> ㅇ 아이디 </Element>
            <SubElement> {id?id:"-"} </SubElement>
          </SubInfo>
          <SubInfo>
            <Element> ㅇ 이름 </Element>
            <SubElement> {name?name:"-"} </SubElement>
          </SubInfo>
          <SubInfo>
            <Element> ㅇ 생년월일 </Element>
            <SubElement> {birthday?birthday:"-"} </SubElement>
          </SubInfo>
          <SubInfo>
            <Element> ㅇ 회원가입 일자 </Element>
            <SubElement> {signInDate?signInDate:"-"} </SubElement>
          </SubInfo>
          <ChangingInfo>
            <Element> ㅇ 이메일 </Element>
            <ChangingSection>
              <InputArea  type="text" placeholder={email?email:"-"} value={newEmail} onChange={(e)=>setNewEmail(e.target.value)} onBlur={newEmailBlur}></InputArea>
              <Button onClick={changeEmail}> 이메일 변경 </Button>
              <p id="info"> - 인증번호 발급을 위한 이메일 작성 </p>
              <p id="emailInfo" style={color}></p>
            </ChangingSection>
          </ChangingInfo>
          <ChangingInfo>
            <Element> ㅇ 비밀번호 </Element>
            <ChangingSection>
              <div style={{ display:"flex" }}>
                <InputSection>
                  <InputArea type="password" placeholder="새 비밀번호" value={newPasswd} onChange={(e)=>setNewPasswd(e.target.value)} onBlur={newPasswdBlur}></InputArea>
                  <InputArea type="password" placeholder="새 비밀번호 확인" value={newPasswdCheck} onChange={(e)=>setNewPasswdCheck(e.target.value)} onBlur={passwdEqualBlur}></InputArea>
                </InputSection>
                <Button style={{ height:"5.725rem" }} onClick={changePasswd}> 비밀번호 변경 </Button>
              </div>
              <p> - 9~16자의 영문, 숫자, 특수문자(!@#$%^~*) 조합 </p>
              <p id="passwdInfo" style={color}></p>
            </ChangingSection>
          </ChangingInfo>
        </ContentSection>
      </NavContent>
    </NavSection>}</>
  );
};

export default PrivateInfoJsx;



const SectionBarMenu = styled.p`
  top : 0;
  right : 0;
  color : #FFFFFF;
  cursor : pointer;
  position : fixed;
  font-weight : bold;
  margin : 1.75rem 2rem;
`

const NavSection = styled.div`
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  position: fixed;
  justify-content : end;
  background-color: rgba(0, 0, 0, 0.25);
`

const NavContent = styled.div`
  display : flex;
  flex-direction : column;
  background-color: #FFFFFF;
`

const SectionBar = styled.div`
  display : flex;
  min-width : 30rem;
  background : #008BFF;
  justify-content : right;

  p {
    color : #FFFFFF;
    cursor : pointer;
    font-weight : bold;
    margin : 1.75rem 2rem 1.125rem;
  }
`

const ContentSection = styled.div`
  width : 100%;
  height : 100%;
  display : flex;
  margin : 1rem 0rem;
  padding : 1rem 0rem;
  flex-direction : column;
`

const SubInfo = styled.div`
  display : flex;
  flex-direction : column;
  margin : 0rem 1.75rem 1.25rem;
`

const Element = styled.p`
  width : 100%;
  margin : 0rem;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const SubElement = styled.p`
  font-weight : bold;
  margin : 0.25rem 1.5rem;
  color : rgba(0, 0, 0, 0.75);
`

const ChangingInfo = styled.div`
width : 100%;
display : flex;
flex-direction : column;
margin : 0rem 1.75rem 1.25rem;
`

const ChangingSection = styled.div`
  p {
    height : 1rem;
    font-size : 0.75rem;
    color : rgba(0, 0, 0, 0.375);
    margin : 0.25rem 1.375rem 0rem;
  }
`

const InputSection = styled.div`
  display : flex;
  width : 16.12rem;
  margin-right : 0.5rem;
  flex-direction : column;
`

const InputArea = styled.input`
  width : 14rem;
  height : 1rem;
  font-size : 1rem;
  padding : 0.75rem;
  font-weight : bold;
  border-radius : 0.5rem;
  margin : 0.25rem 0.5rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Button = styled.button`
  border : none;
  width : 8.75rem;
  color : #FFFFFF;
  cursor : pointer;
  font-size : 1rem;
  height : 2.625rem;
  font-weight : bold;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : 0.25rem 0.5rem;
`