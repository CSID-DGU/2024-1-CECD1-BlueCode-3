import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import React, { useState } from 'react';

function Study_theory() {
  const [option, setOption] = useState('');

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


    // 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.

  return (
    <TestSection>
      <SectionBar>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
        </Logo>
      </SectionBar>
      <Content>
        <NavSection height={height}>
          <Dynamic>
            <Nav> 1번 문제 </Nav>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <Question> 1번. 구체적인 문제 </Question>
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

const ContentSection = styled.div`
  margin : 2rem;
  display : flex;
  padding : 2rem;
  align-items : center;
  border-radius : 1rem;
  flex-direction : column;
  
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const Question = styled.div`
  width : 45rem;
  font-size : 1.125rem;
  padding : 0.75rem 0 0.5rem;
  border-bottom : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 368) / 16}rem`};
`

const View = styled.div`
  width : 42.5rem;
  margin : 1.5rem 0;
  height : 18.75rem;
  padding : 1.25rem;
  font-weight : bold;
  font-size : 1.125rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const AnswerArea = styled.div`
  height : 5rem;
  width : 42.5rem;
  padding :  1rem 1.25rem 0.25rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Answer = styled.div`
  font-size : 1.125rem;
  padding-bottom : 0.5rem;
  color : rgba(0, 0, 0, 0.5);
  border-bottom : 0.05rem solid rgba(0, 0, 0, 0.375);
`

const SelectionArea = styled.div`
  display : flex;
  padding-top : 0.625rem;
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

const Label = styled.label`
  margin-top : 0.125rem;
  padding-left : 0.375rem;
`