import BCODE from '../../logo_w.png';
import Markdown from '../../Markdown';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import SectionBarJsx from '../../SectionBar';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../axiosInstance';
import React, { useEffect, useState } from 'react';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



function Study_theory() {
  const [answer, setAnswer] = useState('');

  const [width, setWidth] = useState(window.innerWidth);
  const [height, setHeight] = useState(window.innerHeight);

  const [curriculumIds, setCurriculumIds] = useState([]);
  const [currentcurriculumId, setcurrentcurriculumId] = useState(0);

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

  
  const [current, setCurrent] = useState(1);
  const [time, setTime] = useState(300);
  const [min, setMin] = useState('');
  const [sec, setSec] = useState('');

  useEffect(()=>{
    const tick = () => {
      if(time >= 0) {
        setTime(time - 1);
        
        const minute = time / 60;
        setMin('0' + parseInt(minute).toString());
        
        const second = time % 60;
        if(second < 10)
          setSec('0' + second);
        else
          setSec(second);
      }
    };

    const timerId = setInterval(tick, 1000);

    return ()=>clearInterval(timerId);
  }, [time]);

  useEffect(() => {

    const getCurriculumIdData =  () => {
      const storedData = JSON.parse(localStorage.getItem('chapters'));
      if (storedData && Array.isArray(storedData)){

        // 상위 리스트의 curriculumId만 추출 (subChapters는 무시)
        const testableCurriculumIds = storedData
          .filter(chapter => chapter.testable)  // testable이 true인 챕터만 필터링
          .map(chapter => chapter.curriculumId);  // curriculumId만 추출
        console.log(testableCurriculumIds)

        // 추출된 ID 배열을 상태에 저장
        setCurriculumIds(testableCurriculumIds);
      }
    };
    

    const createCurriculumTable = async () => {  
      try {
        const rootid = localStorage.getItem('rootid');
        const userid = localStorage.getItem('userid');
  
        const datacalldto = {
          'userId' : userid,
          'curriculumId' : rootid,
        };
  
        const res = await axiosInstance.post('/curriculum/curriculum/create', datacalldto);
      }
      catch (err){
        console.error(err); 
      }
    }
  
    createCurriculumTable();
    getCurriculumIdData();
  }, []);
  
  useEffect(() => {
    // curriculumIds 상태가 변경될 때마다 호출
    console.log('Updated curriculumIds:', curriculumIds);
    if(curriculumIds.length > 0){
      getChapterQuiz();
    }
  }, [curriculumIds]);

  const [res, setRes] = useState();
  const [data, setData] = useState([]);
  const [order, setOrder] = useState(0);

  useEffect(() => {
    // data 상태가 변경될 때마다 실행
    console.log(data);
    if (data.length > 0) {
      console.log(data[0]);
      console.log(data[0].text);
    }
  }, [data]);

const getChapterQuiz =  async () =>{
  const userid = localStorage.getItem('userid');
  if(curriculumIds[currentcurriculumId] != null){
    console.log("불러올려고 하는 curri id "+ curriculumIds[currentcurriculumId]);

    const DataCallDto = {
      'userId': userid,
      'curriculumId': curriculumIds[currentcurriculumId]
    };

    try {
      //초기 테스트용 4 문제 호출 api
      setData([]);
      const response = await axiosInstance.post('/test/test/create/init', DataCallDto);
      setData(response.data.tests); //4 문제를 Data에 저장
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }else{
    console.log("불러올려고 하는 인덱스 " + currentcurriculumId + " 는 범위를 벗어남")
  }
}

//다음챕터 문제 가져오기
const getChapterQuiz2 =  async () =>{
  const userid = localStorage.getItem('userid');
  if(curriculumIds[currentcurriculumId] != null){
    console.log("불러올려고 하는 curri id "+ curriculumIds[currentcurriculumId+1]);

    const DataCallDto = {
      'userId': userid,
      'curriculumId': curriculumIds[currentcurriculumId+1]
    };

    try {
      //초기 테스트용 4 문제 호출 api
      setData([]);
      const response = await axiosInstance.post('/test/test/create/init', DataCallDto);
      setData(response.data.tests); //4 문제를 Data에 저장
      setcurrentcurriculumId(prev => prev + 1);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }else{
    console.log("불러올려고 하는 인덱스 " + currentcurriculumId + " 는 범위를 벗어남")
  }
}

const navigate = useNavigate();
const submitAnswer = async () => {
  var response;
  if (answer) {
    const userid = localStorage.getItem('userid');
    const TestAnswerCallDto = {
      'userId': userid,
      'testId': data[order].testId,
      'quizId': data[order].quizId,
      'answer': answer
    };
    console.log(answer);
    try {
      // 문제 타입 객관식
      if (qtype === "NUM") {
        response = await axiosInstance.post('/test/test/submit/num', TestAnswerCallDto)
        console.log("객관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "WORD") {
        response = await axiosInstance.post('/test/test/submit/word', TestAnswerCallDto);
        console.log("주관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "CODE") {
        response = await axiosInstance.post('/test/test/submit/code',TestAnswerCallDto);
        console.log("서술식 정답 요청 " + response.data.passed);
      }
      
    
      if(response.data.passed === true) {
        if (order === 0) {
          alert("중급자 문제를 맞추셨습니다.");
          alert("다음 챕터의 문제로 넘어갑니다.");
          setOrder(0);
          setQnumber(qnumber + 1);
          //handleProcess();
          updateInitPass("HARD");
          getChapterQuiz2();
        }
        else if (order === 1) {
          alert("초급자 문제를 맞추셨습니다.");
          alert("두번째 중급자 문제를 제시합니다.");
          setOrder(3);
        }
        else if (order === 2) {
          alert("입문자 문제를 맞추셨습니다.");
          alert("학습 시작 챕터가 설정되었습니다.");
          setOrder(0);
          updateInitComplete("EASY").then(
            ()=>{
            navigate('/mypage/todo');
            }
          );
        }
        else if (order === 3) {
          alert("두번째 중급자 문제를 맞추셨습니다.");
          alert("다음 챕터의 문제로 넘어갑니다.");
          setOrder(0);
          setQnumber(qnumber + 1);
          //handleProcess();
          updateInitPass("HARD");
          getChapterQuiz2();

        }
      }
      else {
        if (order === 0) {
          alert("첫 번째 중급자 문제를 틀리셨습니다.");
          alert("초급자 문제로 넘어갑니다.");
          setOrder(1);
        }
        else if (order === 1) {
          alert("초급자 문제를 틀리셨습니다.");
          alert("입문자 문제로 넘어갑니다.");
          setOrder(2);
        }
        else if (order === 2) {
          alert("입문자 문제를 틀리셨습니다.");
          alert("학습 시작 챕터가 설정되었습니다.");
          setOrder(0);
          updateInitComplete("EASY").then(
            ()=>{
            navigate('/mypage/todo');
            }
          );
        }
        else if (order === 3) {
          alert("두 번째 중급자 문제를 틀리셨습니다.");
          const userConfirm = window.confirm("다음 챕터의 문제로 넘어가시겠습니까?");
          if (userConfirm) {
            alert("다음 챕터의 문제로 넘어갑니다.");
            setQnumber(qnumber + 1);
            //handleProcess();
          updateInitPass("HARD");
          getChapterQuiz2();

          } else {
            alert("시작 챕터가 설정되었습니다.");
            updateInitComplete("NORMAL").then(
              ()=>{
              navigate('/mypage/todo');
              }
            );
          }
          setOrder(0);
        }
      }
      
        } catch (err) {
      console.log(err);
    }
  }
}


const handleProcess = async () => {
  try {
    // 첫 번째로 updateInitPass 실행
    await updateInitPass("HARD");

    // 두 번째로 currentcurriculumId를 증가
    await setcurrentcurriculumId(prev => prev + 1);  // 상태 변경은 바로 처리되지 않기 때문에 nextId를 변수로 저장해둠

    // 세 번째로 getChapterQuiz 실행
    await getChapterQuiz();

  } catch (err) {
    console.error("Error during process: ", err);
  }
};



// chapter 초기 테스트 내 챕터 통과 완료 처리 요청
const updateInitPass = async (levelType) => {
  const userId = localStorage.getItem('userid');
  const initChapterPassRequestDto  = {
    'userId': userId,
    'curriculumId': curriculumIds[currentcurriculumId],
    'level': levelType
  };
  
  try {
    const res = await axiosInstance.post('/curriculum/curriculum/init/pass', initChapterPassRequestDto);
    console.log("curi id "+ curriculumIds[currentcurriculumId]+"에 레벨타입 "+ levelType+ "으로 패스 요청");
  } catch (err) {
    console.error(err);
  }
}

const updateInitComplete = async (levelType) => {
  const userId = localStorage.getItem('userid');
  const initChapterPassRequestDto  = {
    'userId': userId,
    'curriculumId': curriculumIds[currentcurriculumId],
    'level': levelType
  };

  try {
    const res = await axiosInstance.post('/curriculum/curriculum/init/complete', initChapterPassRequestDto);
    await axiosInstance.get(`/test/test/complete/init/${userId}`);
  } catch (err) {
    console.error(err);
  }
}

const [qnumber, setQnumber] = useState(1);
const [qlevel, setQlevel] = useState();
// 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.
const [qtype, setQtype] = useState('');
const [type, setType] = useState('');
// 객관식 : NUM , 단답형 : WORD , 코드 작성형 : CODE
useEffect(()=>{
  if(data[order]){
    setQtype(data[order].quizType);
  }

  if (order === 0 || order === 3) {
    setQlevel("중급자");
  } else if (order === 1) {
    setQlevel("초급자");
  } else {
    setQlevel("입문자");
  }

  if(qtype === "NUM")
    setType('객관식');
  else if(qtype === "WORD")
    setType('주관식');
  else if(qtype === "CODE")
    setType('서술식');
  })



  return (
    <TestSection>
      <SectionBarJsx />
      <Content>
        <NavSection height={height}>
          <Dynamic>
            <Nav> {qnumber}번 {qlevel} 문제 </Nav>
            <ProgressImg>
              <svg viewBox="0 0 200 200">
                <Circle></Circle>
                <CircleCur strokeDasharray={`${2 * Math.PI * 75 * qnumber / 9} ${2 * Math.PI * 75 * (9 - qnumber) / 9}`}
                           transform={`rotate(-90, 100, 100)`}>
                </CircleCur>
              </svg>
              <QuestionLeft> 진행도 </QuestionLeft>
            </ProgressImg>
            <ProgressImg>
              <svg viewBox="0 0 200 200">
                <Circle></Circle>
                <CircleCur strokeDasharray={`${2 * Math.PI * 75 * time / 300} ${2 * Math.PI * 75 * (300 - time) / 300}`}
                           transform={`rotate(-90, 100, 100)`}>
                </CircleCur>
              </svg>
              <TimeLeft> 남은 시간 </TimeLeft>
              <Time> {min} : {sec}</Time>
            </ProgressImg>
          </Dynamic>
        </NavSection>
        {data.length > 0 ?
        <ContentSection width={width}>
          <QuestionArea height={height} width={width}>
              <ReactMarkdown rehypePlugins={[rehypeHighlight]}>{data[order].text}</ReactMarkdown>
          </QuestionArea>
          <AnswerArea>
            <Answer> {type} 답안 </Answer>
            {qtype === "NUM" && (<SelectionArea>
              <Selection>
                <input type="radio" id="first" value={data[order].q1} checked={answer===data[order].q1} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="first"> <ReactMarkdown>{data[order].q1}</ReactMarkdown> </Label>
              </Selection>
              <Selection>
                <input type="radio" id="second" value={data[order].q2} checked={answer===data[order].q2} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="second"> <ReactMarkdown>{data[order].q2}</ReactMarkdown> </Label>
              </Selection>
              <Selection>
                <input type="radio" id="third" value={data[order].q3} checked={answer===data[order].q3} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="third"> <ReactMarkdown>{data[order].q3}</ReactMarkdown> </Label>
              </Selection>
              <Selection>
                <input type="radio" id="fourth" value={data[order].q4} checked={answer===data[order].q4} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="fourth"> <ReactMarkdown>{data[order].q4}</ReactMarkdown> </Label>
              </Selection>
            </SelectionArea>)}
            {qtype === "WORD" && (<WritingArea onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
            {qtype === "CODE" && (<CodeArea height={height} width={width} onChange={(e)=>setAnswer(e.target.value)}></CodeArea>)}
            <Submit>
              <Button onClick={submitAnswer}> 제출 </Button>
            </Submit>
          </AnswerArea>
        </ContentSection>
        :<ContentSectionLoading width={width}>
          <img src={LOADING} alt="loading"></img>
        </ContentSectionLoading>}
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

const NavSection = styled.div`
  display : flex;
  min-width : 15rem;
  flex-direction : column;
  border-right : 0.125rem solid rgba(0, 0, 0, 0.125);
  height : ${(props) => `${(props.height - 68) / 16}rem`};
`

const Nav = styled.div`
  display : flex;
  color : #008BFF;
  padding : 0.625rem;
  font-weight : bold;
  align-items : center;
  flex-direction : column;
  justify-content : center;
  background : rgba(0, 139, 255, 0.25);
`

const Dynamic = styled.div`
  overflow : scroll;
  padding : 0.625rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const ProgressImg = styled.div`
  width : 12.5rem;
  height : 12.5rem;
  padding : 0.5rem;
`

const Circle = styled.circle`
  r : 75;
  cx : 100;
  cy : 100;
  fill : none;
  stroke-width : 37.5;
  stroke : rgba(0, 0, 0, 0.125);
`

const CircleCur = styled.circle`
  r : 75;
  cx : 100;
  cy : 100;
  fill : none;
  stroke : #008BFF;
  stroke-width : 37.5; 
`

const QuestionLeft = styled.div`
  top : -7.25rem;
  left : 4.875rem;
  width : 3.25rem;
  color : rgba(0, 0, 0, 0.625);
  font-weight : bold;
  position : relative;
  font-size : 1rem;
`

const TimeLeft = styled.div`
  top : -7.75rem;
  left : 4.125rem;
  width : 4.5rem;
  color : rgba(0, 0, 0, 0.625);
  font-weight : bold;
  position : relative;
  font-size : 1rem;
`

const Time = styled.div`
  top : -7.75rem;
  left : 4.75rem;
  width : 4.5rem;
  color : rgba(0, 0, 0, 0.625);
  font-weight : bold;
  position : relative;
  font-size : 1rem;
`

const ContentSection = styled.div`
  margin : 2rem;
  display : flex;
  padding : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const ContentSectionLoading = styled.div`
  margin : 2rem;
  display : flex;
  padding : 2rem;
  align-items : center;
  border-radius : 1rem;
  justify-content : center;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};

  img {
    width : 12.5rem;
    height : 5rem;
  }
`

const QuestionArea = styled.div`
  font-size : 1rem;
  overflow : scroll;
  padding : 0rem 1rem;
  border : 0.125rem solid rgba(0, 139, 255, 0.75);
  width : ${(props) => `${(props.width - 1000) / 16}rem`};
  height : ${(props) => `${(props.height - 202) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const AnswerArea = styled.div`
  width : 42.5rem;
  margin-left : 1.25rem;
`

const Answer = styled.div`
  font-size : 1.125rem;
  padding-bottom : 0.5rem;
  color : rgba(0, 0, 0, 0.5);
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.375);
`

const SelectionArea = styled.div`
  display : flex;
  margin : 1.5rem auto 1rem;
`

const Selection = styled.div`
  display : flex;
  width : 7.5rem;
  margin : 0 auto;
  font-size : 1.125rem;

  input[type='radio'] {
    -moz-appearance : none;
    -webkit-appearance : none;

    outline : none;
    width : 1.5rem;
    height : 1.5rem;
    cursor : pointer;
    appearance : none;
    border-radius : 1rem;
    border : 0.25rem solid rgba(0, 0, 0, 0.25);
  }

  input[type='radio']:checked {
    background-color : #008BFF;
    border : 0.25rem solid #FFFFFF;
    box-shadow : 0 0 0 0.125rem #008BFF;
  }
`

const WritingArea = styled.textarea`
  resize : none;
  padding : 1rem;
  font-size : 1.25rem;
  width : 31.5rem;
  height : 1.5rem;
  margin-top : 1.5rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const CodeArea = styled.textarea`
  resize : none;
  padding : 1rem;
  width : 31.5rem;
  height : 23.125rem;
  font-weight : bold;
  font-size : 1.125rem;
  margin-top : 1.25rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 900) / 16}rem`};
  height : ${(props) => `${(props.height - 352) / 16}rem`};
`

const Submit = styled.div`
  display : flex;
  text-align : center;
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
  margin : 1.25rem auto 0;
`

const Label = styled.label`
  margin-top : 0.125rem;
  padding-left : 0.375rem;
`