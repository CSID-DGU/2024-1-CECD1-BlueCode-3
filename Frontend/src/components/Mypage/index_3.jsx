import styled from 'styled-components';
import SectionBarJsx from '../SectionBar';
import PrivateInfoJsx from '../PrivateInfo';
import axiosInstance from '../../axiosInstance';
import useChapterData from '../../useChapterData';
import React, { useState, useEffect } from 'react';
import MypageNavSectionJsx from '../MypageNavSection';

import "highlight.js/styles/a11y-light.css";
import ReactMarkdown from 'react-markdown';



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

  const color = {color : "#008BFF"};

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
        if (item.questionType === "DEF") {
          tag = "이론";
        } else if (item.questionType === "CODE") {
          tag = "코드";
        } else if(item.questionType === "ERRORS") {
          tag = "오류";        
        }

        if (!acc[tag]) {
          acc[tag] = [];
        }
        acc[tag].push(item);
        return acc;
      }, {});

      setGroupedByTag(groupedByTag);
      } else{
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



  return (
    <TestSection>
      <PrivateInfoJsx />
      <SectionBarJsx />
      <Content>
        <MypageNavSectionJsx height={height} l1={false} l2={false} l3={true} />
        <ContentSection width={width}>
          <Order>
            <OrderType style={date?color:{}} onClick={selectDate}> ㅇ 날짜별 </OrderType>
            <OrderType style={curriculum?color:{}} onClick={selectCurriculum}> ㅇ 과정별 </OrderType>
            <OrderType style={tag?color:{}} onClick={selectTag}> ㅇ 태그별 </OrderType>
          </Order> 
          <QuestionRecord height={height}> 
            {date && <QuestionInfo>
              {JSON.stringify(groupedDataByDate) === '{}'?
              (<div>
                <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
              </div>)
              :
              (<>{Object.keys(groupedDataByDate).map(date => (
                <div key={date}>
                  <QuestionTitle> {date.substr(5, 2) + '월 ' + date.substr(8, 2) + '일'} </QuestionTitle>
                  <div>
                    {groupedDataByDate[date].map((item, index) => (
                    <QuestionListSub key={index} onClick={() => setQNA(item)}> ❝ {item.question} ❞ </QuestionListSub>
                    ))}
                  </div>
                </div>
                ))}</>)}
              </QuestionInfo>}
            {curriculum && <QuestionInfo>
              {Object.keys(groupedDataByChapter).map(curr => (
              <div key={curr}>
                <QuestionTitle> {curr} </QuestionTitle>
                {groupedDataByChapter[curr].length > 0?
                (<div>
                  {groupedDataByChapter[curr].map((item, index) => (
                    <QuestionListSub key={index} onClick={() => setQNA(item)}> ❝ {item.question} ❞ </QuestionListSub>
                  ))}
                </div>)
                :
                <div>
                  <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
                </div>}
              </div>
              ))}
            </QuestionInfo>}
            {tag && <QuestionInfo>
              {JSON.stringify(groupedDataByTag) === '{}'?
              (<div>
                <QuestionListSub> 질문 내역이 없습니다. </QuestionListSub>
              </div>)
              :
              (<>{Object.keys(groupedDataByTag).map(tag => (
                <div key={tag}>
                  <QuestionTitle> {tag} </QuestionTitle>
                  <div>
                    {groupedDataByTag[tag].map((item, index) => (
                      <QuestionListSub key={index} onClick={() => setQNA(item)}> ❝ {item.question} ❞ </QuestionListSub>
                    ))}
                  </div>
                </div>
                ))}</>)}
            </QuestionInfo>}
            <QuestionContent height={height}>
              {selectedDialog === null ? "":<Dialog_client> <div> {selectedDialog.question} </div> </Dialog_client>} 
              {selectedDialog === null ? "":selectedDialog.answer.map((ans, ansIndex) => (
              <Dialog_server> 
                <div key={ansIndex}><ReactMarkdown>{ans}</ReactMarkdown></div>
              </Dialog_server>))}
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

const Content = styled.div`
  display : flex;
`

const ContentSection = styled.div`
  padding : 1rem;
  margin : 1rem 0rem 1rem 1rem;
  border : 0.125rem solid #008BFF;
  border-radius : 1rem 0rem 0rem 1rem;
  border-right-style : none;
  width : ${(props) => `${(props.width - 291.5) / 16}rem`};
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
  height : ${(props) => `${(props.height - 176) / 16}rem`};
`

const QuestionInfo = styled.div`
  width : 50%;
  overflow : scroll;
  padding : 0rem 1rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const QuestionTitle = styled.h3`
  font-weight : bold;
  margin : 1.75rem 0rem 0.75rem;
 `

 const QuestionListSub = styled.div`
  color : grey;
  cursor : pointer;
  margin : 0.25rem 0rem;

  &:hover {
    font-weight : bold;
  }
 `

const QuestionContent = styled.div`
  width : 50%;
  display : flex;
  padding : 1rem;
  overflow : scroll;
  border-radius : 1rem;
  flex-direction : column;
  border-top : 0.375rem solid #008BFF;
  border-bottom : 0.375rem solid #008BFF;

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
    border-radius : 1.5rem 1.5rem 1.5rem 0rem;
  }
`