import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import Markdown from '../../Markdown';
import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import getUserInfo from '../../getUserInfo';
import SectionBarJsx from '../../SectionBar';
import axiosInstance from '../../axiosInstance';
import useChapterData from '../../useChapterData';
import getChapterPass from '../../getChapterPass';
import React, { useState, useEffect } from 'react';

import "highlight.js/styles/a11y-light.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



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
  const [processPass, setProcessPass] = useState(0);

  useEffect(() => {
    getUserInfo()
      .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
        setPoint(data.exp);
      })
      .catch(error => {
        // 데이터 가져오기 실패 시 에러 처리
        console.error('Error fetching data:', error);
      });

      getChapterPass()
      .then(data => {
          // 데이터 가져오기 성공 시 상태 업데이트
          setProcess(data.length);
          setProcessPass(data.filter(element => element === true).length);
      })
        .catch(error => {
          // 데이터 가져오기 실패 시 에러 처리
        console.error('Error fetching data:', error);
      });  
  }, []);


  const color = {color : "#008BFF"};
  const textDeco = { textDecoration : "none" };

  const [date, setDate] = useState(true);
  const [curriculum, setCurriculum] = useState(false);
  const [tag, setTag] = useState(false);

  const selectDate = () => {
    setDate(true);
    setCurriculum(false);
    setTag(false);
    setselectedDialog(null);
  }

  const selectCurriculum = () => {
    setDate(false);
    setCurriculum(true);
    setTag(false);
    setselectedDialog(null);
    fetchDataByChapter();
  }

  const selectTag = () => {
    setDate(false);
    setCurriculum(false);
    setTag(true);
    setselectedDialog(null);
  }

  const { chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter } = useChapterData();

  // chapter , chaptersid , chapterLevel 챕터레벨에서 null이 아닌 인덱스들 확인해서 c

  const [selectedDialog, setselectedDialog] = useState(null);
  const setQNA = (item) =>{
    setselectedDialog(item);
  };
  
  const [groupedDataByDate, setGroupedDataByDate] = useState({});
  const [groupedDataByChapter, setGroupedDataByChapter] = useState({});
  const [groupedDataByTag, setGroupedByTag] = useState({});

  useEffect(() => {
    /*
    const fetchDataByDate = async () => {
      try {
        const data = await getChatHistoryByRoot();
        const groupedByDate = data.list.reduce((acc, item) => {
          const date = item.chatDate.split('T')[0]; // 날짜만 추출
          if (!acc[date]) {
            acc[date] = [];
          }
          acc[date].push(item);
          return acc;
        }, {});

        setGroupedDataByDate(groupedByDate);
    
      } catch (err) {
        console.error(err);
      } 
    };
  */
    
   /*
    const fetchDataByTag= async () => {
      try {
        const data = await getChatHistoryByRoot();
        console.log(data);
        const groupedByTag = data.list.reduce((acc, item) => {
          let tag = "";
          if (item.questionType === "DEF"){
             tag = "이론";
          } else if (item.questionType === "CODE") {
             tag = "코드";
          } else if(item.questionType === "ERRORS"){
             tag = "오류";        
          }

          if (!acc[tag]) {
            acc[tag] = [];
          }
          acc[tag].push(item);
          return acc;
        }, {});

        setGroupedByTag(groupedByTag);
      } catch (err) {
        console.error(err);
      } 
    };
*/


    const fetchDataByDate = async () => {
      const data = await getChatHistoryByRoot();
      if(data){
        const groupedByDate = data.list.reduce((acc, item) => {
        const date = item.chatDate.split('T')[0]; // 날짜만 추출
        if (!acc[date]) {
          acc[date] = [];
        }
        acc[date].push(item);
        return acc;
        }, {});
        setGroupedDataByDate(groupedByDate);
      }
      else{
        setGroupedDataByDate({});
       
      }
      
    };

    const fetchDataByTag= async () => {
      
        const data = await getChatHistoryByRoot();
        if(data){
       
        const groupedByTag = data.list.reduce((acc, item) => {
          let tag = "";
          if (item.questionType === "DEF"){
             tag = "이론";
          } else if (item.questionType === "CODE") {
             tag = "코드";
          } else if(item.questionType === "ERRORS"){
             tag = "오류";        
          }

          if (!acc[tag]) {
            acc[tag] = [];
          }
          acc[tag].push(item);
          return acc;
        }, {});

        setGroupedByTag(groupedByTag);
        }
        else{
          setGroupedByTag({});
        }
        
    };

    fetchDataByDate();
    fetchDataByTag();
  }, []);

  const fetchDataByChapter = async () => {
    
    let groupedByChapter = {};
    // console.log("챕터레벨" + chapterLevel);
    for (var i = 0; i < chapterLevel.length; i++) {
      groupedByChapter[(i + 1) + "장 " + chapter[i]] = [];
     
      if (chapterLevel[i]) {
        try {
          const data = await getChatHistoryByChapter(chaptersid[i]);
          
          if(data)
            groupedByChapter[(i + 1) + "장 " + chapter[i]]= data.list;
        }
        catch (err){
          console.log(err);
        }
      } 
    }
    
    setGroupedDataByChapter(groupedByChapter);
  };

  const getChatHistoryByRoot = async () => {
    try {
      const userid = localStorage.getItem('userid');
      const rootid = localStorage.getItem('rootid');

      const QuestionCallDto = {
        'userId' : userid,
        'curriculumId':rootid
      };
      
      const res = await axiosInstance.post('/chat/chat/historyByRoot', QuestionCallDto);
      //console.log(res);
      return res.data;
     }
     catch (err){
      console.error(err); 
     }
  }

  const getChatHistoryByChapter = async (chapterId) => {
    try {
      const userid = localStorage.getItem('userid');

      const QuestionCallDto = {
        'userId' : userid,
        'curriculumId':chapterId
      };
      
      const res = await axiosInstance.post('/chat/chat/historyByChapter', QuestionCallDto);
      console.log(res);
      return res.data;
     }
     catch (err){
      console.error(err); 
     }
  }

  const getChatHistoryByTag = async (chapterId) => {
    try {
      const userid = localStorage.getItem('userid');

      const QuestionCallDto = {
        'userId' : userid,
        'curriculumId':chapterId
      };
      
      const res = await axiosInstance.post('/chat/chat/historyByChapter', QuestionCallDto);
      console.log(res);
      return res.data;
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
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {isNaN(Math.round(processPass / process * 100))?"- %":Math.round(processPass / process * 100) + " %"} </p> </InfoNav>
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
            {date && <QuestionInfo>
              {JSON.stringify(groupedDataByDate) === '{}'?
              (<QuestionList>
                  <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
                </QuestionList>)
              :
              (<>{Object.keys(groupedDataByDate).map(date => (
                <div key={date}>
                  <QuestionTitle> {date.substr(5, 2) + '월 ' + date.substr(8, 2) + '일'} </QuestionTitle>
                  <QuestionList>
                    {groupedDataByDate[date].map((item, index) => (
                      <QuestionListSub key={index} onClick={() => setQNA(item)} style={{cursor: 'pointer'}}> {item.question}</QuestionListSub>
                    ))}
                  </QuestionList>
                </div>
                ))}</>)}
              </QuestionInfo>}
            {curriculum && <QuestionInfo>
              {Object.keys(groupedDataByChapter).map(curr => (
              <div key={curr}>
                <QuestionTitle> {curr} </QuestionTitle>
                {groupedDataByChapter[curr].length > 0?
                (<QuestionList>
                  {groupedDataByChapter[curr].map((item, index) => (
                    <QuestionListSub key={index} onClick={() => setQNA(item)} style={{cursor: 'pointer'}}> {item.question}</QuestionListSub>
                  ))}
                </QuestionList>)
                :
                <QuestionList>
                  <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
                </QuestionList>}
              </div>
              ))}
            </QuestionInfo>}
            {tag && <QuestionInfo>
              {JSON.stringify(groupedDataByTag) === '{}'?
              (<QuestionList>
                <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
              </QuestionList>)
              :
              (<>{Object.keys(groupedDataByTag).map(tag => (
                <div key={tag}>
                  <QuestionTitle> {tag} </QuestionTitle>
                  <QuestionList>
                    {groupedDataByTag[tag].map((item, index) => (
                      <QuestionListSub key={index} onClick={() => setQNA(item)} style={{cursor: 'pointer'}}> {item.question}</QuestionListSub>
                    ))}
                  </QuestionList>
                </div>
                ))}</>)}
            </QuestionInfo>}
            <QuestionContent height={height}>
              {selectedDialog === null ? "":<Dialog_client> <div> {selectedDialog.question} </div> </Dialog_client>} 
              {selectedDialog === null ? "":selectedDialog.answer.map((ans, ansIndex) => (<Dialog_server> 
                  <div key={ansIndex}><ReactMarkdown>{ans}</ReactMarkdown></div>
              </Dialog_server>
              ))}
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
  cursor : pointer;
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

const QuestionInfo = styled.div`
  width : 32.5rem;
  padding : 0.75rem 3.75rem 1.25rem 1.25rem;
  overflow : scroll;
  height : 30.375rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const QuestionTitle = styled.h3`
  font-weight : bold;
  margin : 0.5rem 0rem;
 `

 const QuestionList = styled.div`
  margin : 0.5rem 0rem 1.5rem 1rem;
 `

 const QuestionListSub = styled.div`
  color : grey;
  padding : 0.125rem;

  &:hover {
    font-weight : bold;
  }
 `

const QuestionContent = styled.div`
  display : flex;
  width : 75rem;
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