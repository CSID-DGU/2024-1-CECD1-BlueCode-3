import { remove } from '../../remove';
import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import getUserInfo from '../../getUserInfo';
import getChapterPass from '../../getChapterPass';
import React, { useEffect, useState } from 'react';

import P1200 from '../../1200p.png';
import P2400 from '../../2400p.png';
import P3600 from '../../3600p.png';
import P4800 from '../../4800p.png';
import P6000 from '../../6000p.png';
import P7200 from '../../7200p.png';



function MypageNavSectionJsx({height, l1, l2, l3}) {

  const [point, setPoint] = useState(0);
  const [process, setProcess] = useState(0);
  const [processPass, setProcessPass] = useState(0);

   useEffect(()=>{
    getChapterPass()
    .then(data => {
      setProcess(data.length);
      setProcessPass(data.filter(element => element === true).length);
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });

    getUserInfo()
    .then(data => {
      setPoint(data.exp);
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  }, []);

  const color = { color : "#008BFF" };
  const textDeco = { textDecoration : "none" };

  return (
      <NavSection height={height}>
        <Static>
          <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
          <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
        </Static>
        <Info point={point}>
          <InfoNav> ㅇ 현재 진행률 <p> {isNaN(Math.round(processPass / process * 100))?"- %":Math.round(processPass / process * 100) + " %"} </p> </InfoNav>
          <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
        </Info>
        <Dynamic>
          <NavLink style={textDeco} to="/mypage/todo"><Nav style={l1?color:{}}> ㅇ 내 할일 관련 </Nav></NavLink>
          <NavLink style={textDeco} to="/mypage/lecture"><Nav style={l2?color:{}}> ㅇ 내 강의 정보 </Nav></NavLink>
          <NavLink style={textDeco} to="/mypage/question"><Nav style={l3?color:{}}> ㅇ 내 질문 정보 </Nav></NavLink>
        </Dynamic>
      </NavSection>
    );
};

export default MypageNavSectionJsx;



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
  background-size: cover;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  background-position: center;
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);

  background-image: url(${(props) => {
    if (props.point <= 1200) return P1200;
    if (props.point <= 2400) return P2400;
    if (props.point <= 3600) return P3600;
    if (props.point <= 4800) return P4800;
    if (props.point <= 6000) return P6000;
    return P7200;
  }});
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
