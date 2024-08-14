import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import React, { useEffect, useState } from 'react';
import axiosInstance from '../../axiosInstance';

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
      if(time > 0) {
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
        const topLevelIds = storedData.map(item => item.curriculumId);
        
        // 추출된 ID 배열을 상태에 저장
        setCurriculumIds(topLevelIds);
      }
    };

    getCurriculumIdData();
  }, []);

  useEffect(() => {
    // curriculumIds 상태가 변경될 때마다 호출
    console.log('Updated curriculumIds:', curriculumIds);
  }, [curriculumIds]);

  /*
  const getChapterQuiz =  async () =>{
    const userid = localStorage.getItem('userid');
    console.log("불러올려고 하는 curri id "+ curriculumIds[currentcurriculumId])
      const DataCallDto = {
        'userId': userid,
        'curriculumId': curriculumIds[currentcurriculumId]
      };
      try {
        const res = await axiosInstance.post('/test/test/create/init', DataCallDto);
        console.log(res);
        return;// 요청이 성공하면 함수 종료
      } catch (err) {
        console.error(err);
        if (err.response && err.response.status === 400) {
          setcurrentcurriculumId(currentcurriculumId + 1); // currentcurriculumId를 증가시킴
          console.log(`400 error received. Retrying with currentcurriculumId ${curriculumIds[currentcurriculumId]}`);
        } else {
          // 400 외의 에러는 재시도하지 않고 종료
          return;
        }
      }
  }
*/
const getChapterQuiz =  async () =>{
  const userid = localStorage.getItem('userid');
  console.log("불러올려고 하는 curri id "+ curriculumIds[currentcurriculumId])
    const DataCallDto = {
      'userId': userid,
      'curriculumId': "3"
    };
    try {
      const res = await axiosInstance.post('/test/test/create/init', DataCallDto);
      console.log(res);
    } catch (err) {
      console.error(err);
    }
}

  // 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.
  const [qtype, setQtype] = useState(1);
  const [type, setType] = useState('');
  useEffect(()=>{
    if(qtype === 1)
      setType('객관식');
    else if(qtype === 2)
      setType('주관식');
    else if(qtype === 3)
      setType('서술식');
  })

  return (
    
    <TestSection>
      <SectionBar>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
          <button onClick={getChapterQuiz}>Test API</button>
        </Logo>
      </SectionBar>
      <Content>
        <NavSection height={height}>
          <Dynamic>
            <Nav> 1번 문제 </Nav>
            <ProgressImg>
              <svg viewBox="0 0 200 200">
                <Circle></Circle>
                <CircleCur strokeDasharray={`${2 * Math.PI * 75 * current / 10} ${2 * Math.PI * 75 * (10 - current) / 10}`}
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
        <ContentSection width={contentWidth}>
          <QuestionArea>
            <Question> 1번. 구체적인 문제 </Question>
            <View> 보기 </View>
          </QuestionArea>
          <AnswerArea>
            <Answer> {type} 답안 </Answer>
            {qtype === 1 && (<SelectionArea>
              <Selection>
                <input type="radio" id="first" value="1" checked={answer==="1"} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="first"> first </Label>
              </Selection>
              <Selection>
                <input type="radio" id="second" value="2" checked={answer==="2"} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="second"> second </Label>
              </Selection>
              <Selection>
                <input type="radio" id="third" value="3" checked={answer==="3"} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="third"> third </Label>
              </Selection>
              <Selection>
                <input type="radio" id="fourth" value="4" checked={answer==="4"} onChange={(e)=>setAnswer(e.target.value)}></input>
                <Label for="fourth"> fourth </Label>
              </Selection>
            </SelectionArea>)}
            {qtype === 2 && (<WritingArea onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
            {qtype === 3 && (<CodeArea onChange={(e)=>setAnswer(e.target.value)}></CodeArea>)}
            <Submit>
              <Button> 제출 </Button>
            </Submit>
          </AnswerArea>
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

const QuestionArea = styled.div`
  height : 5rem;
  width : 42.5rem;
  padding : 1rem 1.25rem 0.25rem;
`

const Question = styled.div`
  font-size : 1.125rem;
  padding : 0.0625rem 0 0.5rem;
  border-bottom : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 368) / 16}rem`};
`

const View = styled.div`
  margin-top : 1.5rem;
  height : 27.125rem;
  padding : 1.25rem;
  font-weight : bold;
  font-size : 1.125rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const AnswerArea = styled.div`
  width : 42.5rem;
  padding : 1rem 1.25rem 0.25rem;
  border-left : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Answer = styled.div`
  font-size : 1.125rem;
  padding-bottom : 0.5rem;
  color : rgba(0, 0, 0, 0.5);
  border-bottom : 0.05rem solid rgba(0, 0, 0, 0.375);
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
  font-size : 1.25rem;
  width : 31.5rem;
  height : 23.125rem;
  margin-top : 1.5rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Submit = styled.div`
  display : flex;
  text-align : center;
`

const Button = styled.button`
  border : none;
  width : 8.75rem;
  color : #FFFFFF;
  font-size : 1rem;
  height : 2.625rem;
  font-weight : bold;
  background : #008BFF;
  border-radius : 0.5rem;
  margin : 1.5rem auto 0;
`

const Label = styled.label`
  margin-top : 0.125rem;
  padding-left : 0.375rem;
`