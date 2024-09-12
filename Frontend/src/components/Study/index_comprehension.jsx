import NavJsx from '../NavSection';
import styled from 'styled-components';
import LOADING from '../../loading.png';
import SectionBarJsx from '../SectionBar';
import axiosInstance from '../../axiosInstance';
import useChapterData from '../../useChapterData';
import { useRef, useState, useEffect } from 'react';
import NavSectionJsx from '../NavSection';
import Editor from '@monaco-editor/react';
import AlertJsx from '../../Window/index_alert';
import ConfirmJsx from '../../Window/index_confirm';
import OptionJsx from '../../Window/index_confirm';
import { NavLink, useParams, useNavigate } from 'react-router-dom';

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

  useEffect(()=>{
    if (res) {
      console.log(res);
    }
  }, [res]);

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
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [isOptionOpen, setIsOptionOpen] = useState(false);
  const [dataPassed, setDataPassed] = useState(false);
  const navigate = useNavigate();
  const submitAnswer = async () => {
    var response;

    if (answer) {
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
        
        if(response.data.passed === true) {
          setDataPassed(true);
          if (order === 0) {
            //입문자 문제 맞출 경우
            setIsAlertOpen(true);
            alert("입문자 문제를 맞추셨습니다.");
            setOrder(1);
          }
          else if (order === 1) {
            setIsAlertOpen(true);
            alert("초급자 문제를 맞추셨습니다.");
            setOrder(2);
          }
          else if (order === 2) {
            //중급자 문제 맞출 경우
            /*alert("중급자 문제를 맞추셨습니다.");
    
            // 사용자의 level: easy / normal / hard(다음챕터레벨설정용) 입력을 받음 (수정필요)
            const prompt = window.prompt("EASY / NORMAL / HARD 중 택 1");
            postChapterPass(chapId, prompt);

            
            navigate('/mypage/lecture');*/
          }
        }
        else {
          setDataPassed(false);
          if (order === 0) {
            //입문자 문제 틀렸을 경우
            setIsAlertOpen(true);
            alert("입문자 문제를 틀리셨습니다.");
            alert("전반적인 학습을 다시 하세요.");
            navigate('/mypage/lecture');
          }
          else if (order === 1) {
            setIsAlertOpen(true);
            alert("초급자 문제를 틀리셨습니다.");
            alert("입문자 난이도로 다음 챕터가 설정되었습니다.");
            postChapterPass(chapId, "EASY");

            // 해당 챕터 재학습 선지 제시, 재학습 원할시 해당 챕터를 재학습 아닐경우 마이페이지로
            //subChapterId , chaptersid
            setIsConfirmOpen(true);
          }
          else if (order === 2) {
            setIsOptionOpen(true);
            /*alert("중급자 문제를 틀리셨습니다.");
            // 사용자의 level: easy / normal / hard(다음챕터레벨설정용) 입력을 받음 (수정필요)
            const prompt = window.prompt("EASY / NORMAL 중 택 1")
            postChapterPass(chapId, prompt);

            navigate('/mypage/lecture');*/
          }
        }
      } catch (err) {
        console.log(err);
      }
    }
  }

  const handleAlert = () => {
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

  const [menu, setMenu] = useState(false);
  const showMenu = () => {
    if (menu) {
      setMenu(false);
    } else {
      setMenu(true);
    }
  }

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



  return (
    <TestSection>
      {menu && <NavSectionJsx />}
      <MenuButton onClick={showMenu}> ≡ </MenuButton>
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
              {qtype === 'WORD' && (<WritingArea onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
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

const MenuButton = styled.button`
  border : none;
  top : 0.825rem;
  left : 0.75rem;
  width : 2.5rem;
  color : #FFFFFF;
  cursor : pointer;
  position : fixed;
  font-size : 2rem;
  border-radius : 1.25rem;
  background-color : #008BFF;
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