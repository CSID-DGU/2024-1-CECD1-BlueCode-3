import styled from 'styled-components';
import LOADING from '../../loading.png';
import Editor from '@monaco-editor/react';
import SectionBarJsx from '../SectionBar';
import { useNavigate } from 'react-router-dom';
import AlertJsx from '../../Window/index_alert';
import axiosInstance from '../../axiosInstance';
import ConfirmJsx from '../../Window/index_confirm';
import React, { useEffect, useState } from 'react';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



function Study_theory() {
  const [answer, setAnswer] = useState(' ');

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

const [isAlertOpen, setIsAlertOpen] = useState(false);
const [alertMessage, setAlertMessage] = useState('');
const [isConfirmOpen, setIsConfirmOpen] = useState(false);
const [next, setNext] = useState(true);
const [nextAlert, setNextAlert] = useState(false);
const [dataPassed, setDataPassed] = useState(false);
const navigate = useNavigate();
const submitAnswer = async () => {
  var response;
  //if (answer) {
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
      //const confirm = window.confirm("");
      if (qtype === "NUM") {
        response = await axiosInstance.post('/test/test/submit/num', TestAnswerCallDto)
        //console.log("객관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "WORD") {
        response = await axiosInstance.post('/test/test/submit/word', TestAnswerCallDto);
        //console.log("주관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "CODE") {
        response = await axiosInstance.post('/test/test/submit/code', TestAnswerCallDto);
        //console.log("서술식 정답 요청 " + response.data.passed);
      }
      
      // response.data.passed === true
      setDataPassed(response);
      //setDataPassed(confirm);
      setAnswer('');
      if(dataPassed) {
        if (order === 0) {
          setIsAlertOpen(true);
          setAlertMessage("중급자 문제르 맞췄기에, 다음 챕터의 문제로 넘어갑니다.");
        }
        else if (order === 1) {
          setIsAlertOpen(true);
          setAlertMessage("초급자 문제를 맞췄기에, 두 번째 중급자 문제로 넘어갑니다.");
        }
        else if (order === 2) {
          setIsAlertOpen(true);
          setAlertMessage("입문자 문제를 맞췄기에, 학습 시작 챕터가 설정되었습니다.");
        }
        else if (order === 3) {
          setIsAlertOpen(true);
          setAlertMessage("두 번째 중급자 문제를 맞췄기에, 다음 챕터의 문제로 넘어갑니다.");
        }
      }
      else {
        if (order === 0) {
          setIsAlertOpen(true);
          setAlertMessage("첫 번째 중급자 문제를 틀렸기에, 초급자 문제로 넘어갑니다.");
        }
        else if (order === 1) {
          setIsAlertOpen(true);
          setAlertMessage("초급자 문제를 틀렸기에, 입문자 문제로 넘어갑니다.");
        }
        else if (order === 2) {
          setIsAlertOpen(true);
          setAlertMessage("입문자 문제를 틀렸기에, 학습 시작 챕터가 설정되었습니다.");
        }
        else if (order === 3) {
          setIsAlertOpen(true);
          setAlertMessage("두 번째 중급자 문제를 틀리셨습니다.");
        }
      }
    } catch (err) {
      console.log(err);
    }
  //}
  }

const handleAlert = (next) => {
  if (next) {
    if (dataPassed) {
      if (order === 0) {
        setOrder(0);
        setQnumber(qnumber + 1);
        updateInitPass("HARD");
        getChapterQuiz2();
      } else if (order === 1) {
        setOrder(3);
      } else if (order === 2) {
        setOrder(0);
        updateInitComplete("EASY").then(
          ()=>{
          navigate('/mypage/todo');
          }
        );
      } else if (order === 3) {
        setOrder(0);
        setQnumber(qnumber + 1);
        updateInitPass("HARD");
        getChapterQuiz2();
      }
    } else {
      if (order === 0) {
        setOrder(1);
      } else if (order === 1) {
        setOrder(2);
      } else if (order === 2) {
        setOrder(0);
        updateInitComplete("EASY").then(
          ()=>{
          navigate('/mypage/todo');
          }
        );
      } else if (order === 3) {
        setIsConfirmOpen(true);
      }
    }
  } else {
    if (nextAlert) {
      setQnumber(qnumber + 1);
      updateInitPass("HARD");
      getChapterQuiz2();
    } else {
      updateInitComplete("NORMAL").then(
        ()=>{
          navigate('/mypage/todo');
        }
      );
    }
  }
  
  setNext(true);
  setIsAlertOpen(false);
}

const handleConfirm = (confirm) => {
  if (confirm) {
    setNextAlert(true);
    setAlertMessage("다음 챕터의 문제로 넘어갑니다.");
  } else {
    setNextAlert(false);
    setAlertMessage("시작 챕터가 설정되었습니다.");
  }

  setOrder(0);
  setNext(false);
  setIsConfirmOpen(false);
  setIsAlertOpen(true);
}

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

const [time, setTime] = useState(null);
const [min, setMin] = useState('');
const [sec, setSec] = useState('');

useEffect(()=>{
  if (data) {
    setTime(300);
    console.log(data);
  }
}, [data]);

useEffect(() => {
  if (time !== null && time >= -1) {
    const tick = () => {
      setTime(prev => prev - 1);
      
      const minute = time / 60;
      setMin('0' + parseInt(minute).toString());

      const second = time % 60;
      setSec(second < 10 ? '0' + second : second.toString());
    };

    const timerId = setInterval(tick, 1000);

    return () => clearInterval(timerId);
  }
}, [time]);

useEffect(() => {
  if (data && time === -1) {
    submitAnswer();
  }
}, [time, data, order, qtype, answer]);

  return (
    <TestSection>
      <SectionBarJsx />
      <Content>
        <ContentSection>
          {data.length > 0 ?<>
            <Instruction height={height}>
              <Info>
                <Time> {`제한 시간 05 : 00 / 남은 시간 ${min} : ${sec}`} </Time>
              </Info>
              <ReactMarkdown rehypePlugins={[rehypeHighlight]}>{data[order].text}</ReactMarkdown>
              <br />
              {<>
                {qtype === 'NUM' && (<SelectionArea>
                <Selection>
                  <input type="radio" id="first" value={data[order].q1} checked={answer===data[order].q1} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="first"> {data[order].q1} </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="second" value={data[order].q2} checked={answer===data[order].q2} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="second"> {data[order].q2} </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="third" value={data[order].q3} checked={answer===data[order].q3} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="third"> {data[order].q3} </Label>
                  </Selection>
                <Selection>
                  <input type="radio" id="fourth" value={data[order].q4} checked={answer===data[order].q4} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="fourth"> {data[order].q4} </Label>
                </Selection>
              </SelectionArea>)}
              {qtype === 'WORD' && (<WritingArea value={answer} placeholder={"O".repeat(data[order].wordCount)} onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
              {(qtype === 'NUM' || qtype === 'WORD') && <Submit onClick={submitAnswer}> 제출 </Submit>}
            </>}
          </Instruction>  
          <Train height={height}>
            {qtype === 'CODE' &&
            <><Editor height="100%"
                      theme="tomorrow"
                      defaultLanguage="python"
                      value={answer} onChange={(value)=>setAnswer(value)}>
            </Editor>
            <Submit onClick={submitAnswer}> 제출 </Submit></>
          }
          </Train></>
          :
          <InstructionLoading>
            <img src={LOADING} alt="loading"></img>
          </InstructionLoading>}
          {isAlertOpen && <AlertJsx message={alertMessage} onAlert={()=>handleAlert(next)}></AlertJsx>}
          {isConfirmOpen &&
          <ConfirmJsx message="다음 챕터의 문제로 넘어가시겠습니까?"
                    onConfirm={()=>handleConfirm(true)}
                    onCancel={()=>handleConfirm(false)}>
          </ConfirmJsx>}
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
  height : 100%;
  display : flex;
`

const ContentSection = styled.div`
  width : 100%;
  display: flex;
`

const Info = styled.div`
  width : 100%;
  display : flex;
  color : #008BFF;
  align-items : center;
  justify-content : right;
`

const Time = styled.div`
  display : flex;
  color : #008BFF;
  font-weight : bold;
  flex-direction : column;
`

const Instruction = styled.div`
  display : flex;
  width : 47.25%;
  overflow : scroll;
  padding : 1rem 1rem;
  background : #FFFFFF;
  margin-bottom : 0.5rem;
  flex-direction : column;
  margin : 1rem 0.5rem 1rem 0rem;
  border : 0.125rem solid #008BFF;
  border-left-style : none;
  border-radius : 0rem 1rem 1rem 0rem;
  max-height : ${(props) => `${(props.height - 136) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const SelectionArea = styled.div`
  width : 100%;
  display : flex;
  flex-direction : column;
`

const Selection = styled.div`
  width : 100%;
  display : flex;
  margin : 0.625rem 0rem 0rem;

  input[type='radio'] {
    -moz-appearance : none;
    -webkit-appearance : none;

    outline : none;
    height : 1.5rem;
    cursor : pointer;
    appearance : none;
    min-width : 1.5rem;
    border-radius : 1rem;
    border : 0.25rem solid rgba(0, 0, 0, 0.25);
  }

  input[type='radio']:checked {
    background-color : #008BFF;
    border : 0.25rem solid #FFFFFF;
    box-shadow : 0 0 0 0.125rem #008BFF;
  }
`

const Label = styled.label`
  white-space: pre;
  margin-top : 0.2rem;
  padding-left : 0.375rem;
`

const WritingArea = styled.input`
  width : 100%;
  resize : none;
  height : 1.375rem;
  text-align : center;
  font-size : 1.125rem;
  padding : 0.5rem 0rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Submit = styled.button`
  border : none;
  width : 8.75rem;
  color : #FFFFFF;
  cursor : pointer;
  font-size : 1rem;
  height : 2.625rem;
  font-weight : bold;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : 1.25rem auto 0rem;
`

const Train = styled.div`
  display : flex;
  width : 47.25%;
  overflow : scroll;
  padding : 1rem 1rem;
  background : #FFFFFF;
  margin-bottom : 0.5rem;
  flex-direction : column;
  margin : 1rem 0rem 1rem 0.5rem;
  border : 0.125rem solid #008BFF;
  border-right-style : none;
  border-radius : 1rem 0rem 0rem 1rem;
  max-height : ${(props) => `${(props.height - 136) / 16}rem`};
  
  &::-webkit-scrollbar {
    display : none;
  }
`

const InstructionLoading = styled.div`
  display : flex;
  margin : 0 auto;
  align-items : center;
  justify-content : center;

  img {
    width : 12.5rem;
    height : 5rem;
  }
`