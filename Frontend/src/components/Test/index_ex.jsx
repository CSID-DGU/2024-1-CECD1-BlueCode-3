import styled from 'styled-components';
import BCODE from '../../logo_b.png'
import React, { useState } from 'react';


function Test() {

  const [option, setOption] = useState('');
  const [correct, setCorrect] = useState(0);
  const [current, setCurrent] = useState(1);

  const Progression = () => {
    setCorrect(correct + 1);
    setCurrent(current + 1);
  }

  return (
    <TestSection>
      <Centering>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
        </Logo>
        <Section>
          <ProgressArea>
            <Time> 남은 시간 </Time>
            <Progress>
              <svg viewBox="0 0 200 200">
                <CircleLeft></CircleLeft>
                <CircleCur strokeDasharray={`${2 * Math.PI * 75 * current / 10} ${2 * Math.PI * 75 * (10 - current) / 10}`}
                           transform={`rotate(-90, 100, 100)`}>
                </CircleCur>
                <CircleProg strokeDasharray={`${2 * Math.PI * 75 * correct / 10} ${2 * Math.PI * 75 * (10 - correct) / 10}`}
                            transform={`rotate(-90, 100, 100)`}>
                </CircleProg>
              </svg>
            </Progress>
            <Button onClick={Progression}> 제출하기 </Button>
          </ProgressArea>
          <QuestionArea>
            <Question>
              <QuestionNum> {current} </QuestionNum>
              <QuestionCntnt> Q{current}. </QuestionCntnt>
            </Question>
            <View> 보기 </View>
            <AnswerArea>
              <Answer> 답안 </Answer>
                <SelectionArea>
                <Selection>
                  <input type="radio" id="first" value="1" checked={option==="1"} onChange={(e)=>setOption(e.target.value)}></input>
                  <Label for="first"> first </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="second" value="2" checked={option==="2"} onChange={(e)=>setOption(e.target.value)}></input>
                  <Label for="second"> second </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="third" value="3" checked={option==="3"} onChange={(e)=>setOption(e.target.value)}></input>
                  <Label for="third"> third </Label>
                </Selection>
                <Selection>
                  <input type="radio" id="fourth" value="4" checked={option==="4"} onChange={(e)=>setOption(e.target.value)}></input>
                  <Label for="fourth"> fourth </Label>
                </Selection>
              </SelectionArea>
            </AnswerArea>
          </QuestionArea>
        </Section>
      </Centering>
    </TestSection>
  );
}

export default Test;



const TestSection = styled.div`
  display : flex;
  height : 100vh;
  justify-content : center;
  align-items : center;
`

const Centering = styled.div`
`

const Logo = styled.div`
  display : flex;

  img {
    margin : 0 auto 2.5rem 5rem;
    width : 9.775rem;
    height : 2.5rem;
  }
`

const Section = styled.div`
  display : flex;
`

const ProgressArea = styled.div`
  height : 27.5rem;
  width : 17.5rem;
  padding : 2.5rem 1.25rem;
  border-radius : 1.25rem;
  flex-direction : column;
  justify-content : center;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Time = styled.div`
  font-weight : bold;
  font-size : 1.25rem;
  color : rgba(0, 0, 0, 0.375);
`

const Progress = styled.div`
  margin : 2.5rem auto;
  padding : 0.75rem;
`

const CircleLeft = styled.circle`
  cx : 100;
  cy : 100;
  r : 75;
  fill : none;
  stroke : rgba(0, 0, 0, 0.125);
  stroke-width : 50;
`

const CircleCur = styled.circle`
  cx : 100;
  cy : 100;
  r : 75;
  fill : none;
  stroke : #8DF388;
  stroke-width : 50; 
`

const CircleProg = styled.circle`
  cx : 100;
  cy : 100;
  r : 75;
  fill : none;
  stroke : #00E5BA;
  stroke-width : 50;
`

const Button = styled.button`
  height : 3rem;
  border : none;
  width : 9.5rem;
  margin : 0 4rem;
  color : #FFFFFF;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 0.5rem;
`

const QuestionArea = styled.div`
  display : flex;
  width : 52.5rem;
  margin : 0 0 0 2.5rem;
  flex-direction : column;
`

const Question = styled.div`
  display : flex;
`

const QuestionNum = styled.div`
  height : 5.5rem;
  display : flex;
  width : 3.75rem;
  font-size : 1.25rem;
  align-items : center;
  border-radius : 1.25rem;
  justify-content : center;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const QuestionCntnt = styled.div`
  height : 5.5rem;
  width : 45rem;
  display : flex;
  font-size : 1.25rem;
  padding : 0 1.25rem;
  align-items : center;
  margin-left : 1.25rem;
  justify-content : left;
  border-radius : 1.25rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const View = styled.div`
  height : 15rem;
  padding : 1.25rem;
  font-weight : bold;
  font-size : 1.25rem;
  margin : 1.25rem 15rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const AnswerArea = styled.div`
  height : 5rem;
  width : 49.875rem;
  margin-top : 0.55rem;
  padding : 0.625rem 1.25rem;
  border-radius : 1.25rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Answer = styled.div`
  font-size : 1.25rem;
  padding-bottom : 0.625rem;
  color : rgba(0, 0, 0, 0.375);
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.375);
`

const SelectionArea = styled.div`
  display : flex;
  padding-top : 0.625rem;
`

const Selection = styled.div`
  display : flex;
  width : 7.5rem;
  margin : 0 auto;
  font-size : 1.25rem;

  input[type='radio'] {
    -webkit-appearance : none;
    -moz-appearance : none;
    appearance : none;
    width : 1.75rem;
    height : 1.75rem;
    border : 0.25rem solid rgba(0, 0, 0, 0.25);
    border-radius : 1rem;
    outline : none;
    cursor : pointer;
  }

  input[type='radio']:checked {
    background-color : #008BFF;
    border : 0.25rem solid #FFFFFF;
    box-shadow : 0 0 0 0.125rem #008BFF;
  }
`

const Label = styled.label`
  margin-top : 0.125rem;
  padding-left : 0.375rem;
`