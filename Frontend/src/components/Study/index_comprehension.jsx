import NavJsx from '../StudyNavSection';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import SectionBarJsx from '../SectionBar';
import Editor from '@monaco-editor/react';
import { useState, useEffect } from 'react';
import AlertJsx from '../../Window/index_alert';
import axiosInstance from '../../axiosInstance';
import useChapterData from '../../useChapterData';
import OptionJsx from '../../Window/index_confirm';
import ConfirmJsx from '../../Window/index_confirm';
import StudyNavSectionJsx from '../StudyNavSection';
import { useParams, useNavigate } from 'react-router-dom';

import "highlight.js/styles/a11y-dark.css";
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from "rehype-highlight";



function Study_training() {
  const [answer, setAnswer] = useState('');

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

  const [res, setRes] = useState();
  const [order, setOrder] = useState(0);

  const { chapId } = useParams();
  const getChapterQuiz =  async (chapterid) =>{
    const userid = localStorage.getItem('userid');

    const DataCallDto = {
      'userId': userid,
      'curriculumId': chapterid
    };

    try {
      //이해도 테스트용 3 문제 호출 api
      const response = await axiosInstance.post('/test/test/create/normal', DataCallDto);
      setRes(response.data.tests);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(()=>{
    getChapterQuiz(chapId);

  }, []);

  /*
  useEffect(()=>{
    if (res) {
      setTime(300);
      console.log(res);
    }
  }, [res]);
  */

  const { chapter, chaptersid, chapterLevel, chapterPass, subChapter, subChapterId, currentChapter } = useChapterData();
  const [type, setType] = useState('');
  const [qtype, setQtype] = useState('');
  useEffect(()=>{
    if(res) {
      setQtype(res[order].quizType);
    }

    if(qtype === 'NUM')
      setType('객관식');
    else if(qtype === 'WORD')
      setType('주관식');
    else if(qtype === 'CODE')
      setType('서술식');
  })
  

  
  const [isAlertOpen, setIsAlertOpen] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [isOptionOpen, setIsOptionOpen] = useState(false);
  const [dataPassed, setDataPassed] = useState(false);
  const navigate = useNavigate();
  const submitAnswer = async () => {
    var response;

    //if (answer || answer === '') {
    const userid = localStorage.getItem('userid');
    const TestAnswerCallDto = {
      'userId': userid,
      'testId': res[order].testId,
      'quizId': res[order].quizId,
      'answer': answer
    };
    
    try {
      // 문제 타입 객관식
      if (qtype === "NUM") {
        response = await axiosInstance.post('/test/test/submit/num', TestAnswerCallDto);
        // console.log("객관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "WORD") {
        response = await axiosInstance.post('/test/test/submit/word', TestAnswerCallDto);
        // console.log("주관식 정답 요청 " + response.data.passed);
      }
      else if (qtype === "CODE") {
        response = await axiosInstance.post('/test/test/submit/code', TestAnswerCallDto);
        // console.log("서술식 정답 요청 " + response.data.passed);
      }
        
      setAnswer('');
      if(response.data.passed === true) {
        setDataPassed(true);
        if (order === 0) {
          //입문자 문제 맞출 경우
          setAlertMessage("입문자 문제를 맞추셨습니다.");
          setIsAlertOpen(true);
        }
        else if (order === 1) {
          setAlertMessage("초급자 문제를 맞추셨습니다.");
          setIsAlertOpen(true);
        }
        else if (order === 2) {
          setIsOptionOpen(true);
        }
      }
      else {
        setDataPassed(false);
        if (order === 0) {
          //입문자 문제 틀렸을 경우
          setAlertMessage("입문자 문제를 틀렸기에, 내 강의 정보로 돌아갑니다.");
          setIsAlertOpen(true);
        }
        else if (order === 1) {
          setAlertMessage("초급자 문제를 틀렸기에, 다음 챕터가 입문자 난이도로 설정되었습니다.");
          setIsAlertOpen(true);
        }
        else if (order === 2) {
          setIsOptionOpen(true);
        }
      }
    } catch (err) {
      console.log(err);
    }
    //}
  }

  const handleAlert = () => {
    if (dataPassed) {
      if (order === 0) {
        setOrder(1);
      } else if (order === 1) {
        setOrder(2);
      }
    } else {
      if (order === 0) {
        navigate('/mypage/lecture');
      } else if (order === 1) {
        postChapterPass(chapId, "EASY");
        setIsConfirmOpen(true);
      }
    }
      
    setIsAlertOpen(false);
  }

  const handleConfirm = (confirm) => {
    if (confirm) {
      for (var i = 0; i < chaptersid.length; i++) {
        if (chapId === chaptersid[i].toString()) {
          navigate(`/study/theory/${subChapterId[i][0]}/DEF`);
        }
      }
    } else {
      navigate('/mypage/lecture');
    }

    setIsConfirmOpen(false);
  }

  const handleOption = (option) => {
    postChapterPass(chapId, option);
    navigate('/mypage/lecture');
    setIsOptionOpen(false);
  }


  const postChapterPass =  async (chapterid, level) =>{
    const userid = localStorage.getItem('userid');

    const CurriculumPassCallDto = {
      'userId': userid,
      'curriculumId': chapterid,
      'levelType': level
    };

    try {
      //chapter 이해도 테스트 통과 완료 처리 요청
      const response = await axiosInstance.post('/curriculum/curriculum/chapter/pass', CurriculumPassCallDto);
      console.log(response);
    } catch (err) {
      console.error(err);
    }
  }

  const [time, setTime] = useState(null);
  const [min, setMin] = useState('');
  const [sec, setSec] = useState('');

  useEffect(()=>{
    if (res) {
      setTime(300);
      console.log(res);
    }
  }, [res]);

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
    if (res && time === -1) {
      submitAnswer();
    }
  }, [time, res, order, qtype, answer]);


  return (
    <TestSection>
      <StudyNavSectionJsx />
      <SectionBarJsx />
      <Content>
        <ContentSection>
          {res?<>
            <Instruction height={height}>
              <Info>
                <Time> {`제한 시간 05 : 00 / 남은 시간 ${min} : ${sec}`} </Time>
              </Info>
              <ReactMarkdown rehypePlugins={[rehypeHighlight]}>{res[order].text}</ReactMarkdown>
              <br />
              {<>
                {qtype === 'NUM' && (<SelectionArea>
                <Selection>
                  <input type="radio" id="first" value={res[order].q1} checked={answer===res[order].q1} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="first"> {res[order].q1} </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="second" value={res[order].q2} checked={answer===res[order].q2} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="second"> {res[order].q2} </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="third" value={res[order].q3} checked={answer===res[order].q3} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="third"> {res[order].q3} </Label>
                  </Selection>
                <Selection>
                  <input type="radio" id="fourth" value={res[order].q4} checked={answer===res[order].q4} onChange={(e)=>setAnswer(e.target.value)}></input>
                  <Label for="fourth"> {res[order].q4} </Label>
                </Selection>
              </SelectionArea>)}
              {qtype === 'WORD' && (<WritingArea value={answer} placeholder={"O".repeat(res[order].wordCount)} onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
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
          {isAlertOpen && <AlertJsx message={alertMessage} onAlert={()=>handleAlert()}></AlertJsx>}
          {isConfirmOpen &&
          <ConfirmJsx message="해당 챕터를 재학습하시겠습니까?"
                    onConfirm={()=>handleConfirm(true)}
                    onCancel={()=>handleConfirm(false)}>
          </ConfirmJsx>}
          {isOptionOpen && 
          <OptionJsx message={dataPassed?"중급자 문제를 맞추셨습니다.":"중급자 문제를 틀리셨습니다."}
                     onOption1={()=>handleOption('EASY')}
                     onOption2={()=>handleOption('NORMAL')}
                     onOption3={dataPassed?()=>handleOption('HARD'):null}>
          </OptionJsx>}
        </ContentSection>
      </Content>
    </TestSection>
  );
}

export default Study_training;



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