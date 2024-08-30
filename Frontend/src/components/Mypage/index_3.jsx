import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import styled from 'styled-components';
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
  const textDeco = { textDecoration : "none" };

  const [date, setDate] = useState(true);
  const [curriculum, setCurriculum] = useState(false);
  const [tag, setTag] = useState(false);

  const selectDate = () => {
    setDate(true);
    setCurriculum(false);
    setTag(false);
  }

  const selectCurriculum = () => {
    setDate(false);
    setCurriculum(true);
    setTag(false);
  }

  const selectTag = () => {
    setDate(false);
    setCurriculum(false);
    setTag(true);
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
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {process} % </p> </InfoNav>
            <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
          </Info>
          <Dynamic>
            <NavLink style={textDeco} to="/mypage/todo"><Nav> ㅇ 내 할일 관련 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/lecture"><Nav> ㅇ 내 강의 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/question"><Nav style={color}> ㅇ 내 질문 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/info"><Nav> ㅇ 내 정보 수정 </Nav></NavLink>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <Order>
            <OrderType style={date?color:{}} onClick={selectDate}> ㅇ 날짜별 </OrderType>
            <OrderType style={curriculum?color:{}} onClick={selectCurriculum}> ㅇ 과정별 </OrderType>
            <OrderType style={tag?color:{}} onClick={selectTag}> ㅇ 태그별 </OrderType>
          </Order>
          <QuestionRecord height={height}>
            {date && <QuestionTitle>
              <QuestionDate> - 오늘 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 어제 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 8월 8일 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 8월 7일 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 8월 6일 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
            </QuestionTitle>}
            {curriculum && <QuestionTitle>
              <QuestionDate> - 제1장 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 제2장 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 제3장 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 제4장 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 제5장 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
            </QuestionTitle>}
            {tag && <QuestionTitle>
              <QuestionDate> - 개념 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 코드 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
              <QuestionDate> - 오류 </QuestionDate>
              <QuestionList>
                <QuestionListSub> &gt; 질문 1 </QuestionListSub>
                <QuestionListSub> &gt; 질문 2 </QuestionListSub>
                <QuestionListSub> &gt; 질문 3 </QuestionListSub>
              </QuestionList>
            </QuestionTitle>}
            <QuestionContent height={height}>
              <Dialog_client> <p> 구체적인 질문 </p> </Dialog_client>
              <Dialog_server> <p>구체적인 답변+구체적인 답변+구체적인 답변+구체적인 답변+구체적인 답변 </p> </Dialog_server>
            </QuestionContent>
          </QuestionRecord>
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
  padding : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const Order = styled.div`
  display : flex;
`

const OrderType = styled.div`
  margin : 0;
  font-weight : bold;
  font-size : 1.05rem;
  margin-right : 2.5rem;
  color : rgba(0, 0, 0, 0.5);
`

const QuestionRecord = styled.div`
  display : flex;
  margin-top : 1rem;
  height : ${(props) => `${(props.height - 265) / 16}rem`};
`

const QuestionTitle = styled.div`
  width : 40rem;
  padding : 0.75rem 1.25rem 1.25rem;
  overflow : scroll;
  height : 30.375rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const QuestionDate = styled.div`
  font-weight : bold;
  margin : 0.5rem 0rem;
 `

 const QuestionList = styled.div`
  margin : 0.5rem 0rem 1.5rem 0.75rem;
 `

 const QuestionListSub = styled.div`
  padding : 0.05rem;
 `

const QuestionContent = styled.div`
  display : flex;
  width : 37.5rem;
  padding : 1.25rem;
  overflow : scroll;
  align-item : right;
  border-radius : 1rem;
  flex-direction : column;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  height : ${(props) => `${(props.height - 265) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog_client = styled.div`
  display : flex;
  justify-content : flex-end;

  p {
    margin : 0.5rem 0;
    width : fit-content;
    background : #FFFFFF;
    padding : 0.75rem 1rem;
    word-break : break-word;
    overflow-wrap : break-word;
    border : 0.05rem solid rgba(0, 0, 0, 0.5);
    border-radius : 1.5rem 1.5rem 0rem 1.5rem;
  }
`

const Dialog_server = styled.div`
  p {
    color : #FFFFFF;
    margin : 0.5rem 0;
    width : fit-content;
    padding : 0.75rem 1rem;
    word-break : break-word;
    overflow-wrap : break-word;
    background : rgba(0, 139, 255, 0.75);
    border : 0.05rem solid rgba(0, 0, 0, 0.5);
    border-radius : 1.5rem 1.5rem 1.5rem 0rem;
  }
`