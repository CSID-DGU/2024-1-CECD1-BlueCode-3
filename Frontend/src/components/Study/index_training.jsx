import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';


function Study_training() {
  const [pyodide, setPyodide] = useState(null);
  const [code, setCode] = useState("");
  const [result, setResult] = useState("");
  
const codeSended =
`
import sys
import json

class PrintCollector:
    def __init__(self):
        self.output = []

    def write(self, text):
        self.output.append(text)

    def flush(self):
        pass

collector = PrintCollector()
sys.stdout = collector
sys.stderr = collector

def print_collected():
    return ''.join(collector.output)

${code}

print_collected()
`;

  useEffect(() => {
    const loadPyodide = async () => {
      const pyodide = await window.loadPyodide();
      setPyodide(pyodide);
    };
    loadPyodide();
  }, []);

  const runPythonCode = async () => {
    if (pyodide) {
      try {
        console.log(codeSended);
        const res = await pyodide.runPython(codeSended);
        setResult(res);
      } catch (err) {
        setResult(err.message);
      }
    }
  };


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


  const [gptValue, setGptValue] = useState(false);
  const [contentWidth, setContentWidth] = useState(width);
  const [navValue, setNavValue] = useState(false);
  const [dialog, setDialog] = useState("");

  const ShowGpt = () => {
    if (gptValue) {
      setGptValue(false);
      setContentWidth(contentWidth + 432);
    }
    else {
      setGptValue(true);
      setContentWidth(contentWidth - 432)
    }

    // 네비게이션 부분이 변동하지 않도록 추가적인 코드가 필요함.
  }

  const nav_style = {color : '#008BFF', background : 'rgba(0, 139, 255, 0.25)'};
  const type_style = {background : 'rgba(0, 139, 255, 0.25)'}
  const styled = {};
  const AddToNavContent = () => {
    if (navValue)
      setNavValue(false);
    else
      setNavValue(true);
  }

  const [div, setDiv] = useState([]);
  const AddDialog = () => {
    setDiv([...div, <Dialog> {dialog} </Dialog>]);
    setDialog("");
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
            <NavLink style={{ textDecoration : "none" }} to="/Mypage"><Nav> 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> 로그아웃 </Nav></NavLink>
          </Static>
          <Dynamic>
            <Nav id="1" style={navValue ? nav_style : styled} onClick={AddToNavContent}> 제 1장 </Nav>
            {navValue && (<NavContent>
              <NavItem> 목차 1 </NavItem>
              <NavItem> 목차 2 </NavItem> 
              <NavItem> 목차 3 </NavItem>
            </NavContent>)}
            <Nav> 제 2장 </Nav>
            <Nav> 제 3장 </Nav>
            <Nav> 제 4장 </Nav>
            <Nav> 제 5장 </Nav>
            <Nav> 제 6장 </Nav>
            <Nav> 제 7장 </Nav>
            <Nav> 제 8장 </Nav>
            <Nav> 제 9장 </Nav>
            <Nav> 제 10장 </Nav>
            <Nav> 제 11장 </Nav>
            <Nav> 제 12장 </Nav>
            <Nav> 제 13장 </Nav>
            <Nav> 제 14장 </Nav>
            <Nav> 제 15장 </Nav>
          </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <Instruction></Instruction>
          <Buttons>
            <Before> <img src={Left}></img> </Before>
            <After> <img src={Right}></img> </After>
            <GPT onClick={ShowGpt}> GPT </GPT>
          </Buttons>
          <Train height={height} width={contentWidth}>
          <CodeArea height={height} width={contentWidth} value={code} onChange={(e)=>setCode(e.target.value)}></CodeArea>
          <Buttons_>
            <Interpret onClick={runPythonCode}> 실행 </Interpret>
            <Submit> 제출 </Submit>
          </Buttons_>
          <CodeResult>
            <p>--- 코드 실행 결과 ---</p>
            <ResultPre>
              <Result width={contentWidth} > {result} </Result>
            </ResultPre>
          </CodeResult> 
        </Train>
      </ContentSection>
      {gptValue && (<ChatbotSection>
          <Chat height={height}>
          {div.map(div => div)}
          </Chat>
          <ChatType>
            <Type> #이론 </Type>
            <Type> #문법 </Type>
            <Type> #코드 </Type>
            <Type> #오류 </Type>
          </ChatType>
          <ChatInput>
            <InputArea value={dialog} onChange={(e)=>setDialog(e.target.value)}></InputArea>
            <InputButton onClick={AddDialog}> <img src={Input}></img> </InputButton>          
          </ChatInput>
        </ChatbotSection>)}
      </Content>
    </TestSection>
  );
}

export default Study_training;



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
  width : 15rem;
  display : flex;
  flex-direction : column;
  border-right : 0.125rem solid rgba(0, 0, 0, 0.125);
  height : ${(props) => `${(props.height - 68) / 16}rem`};
`

const Static = styled.div`
  padding : 0.625rem;
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const Nav = styled.div`
  display : flex;
  flex-direction : column;
  font-weight : bold;
  padding : 0.625rem;
  align-items : center;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
    background : rgba(0, 139, 255, 0.25);
  }
`

const NavContent = styled.div`

`

const NavItem = styled.div`
  display : flex;
  flex-direction : column;
  font-weight : bold;
  padding : 0.375rem;
  align-items : left;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
  }
`

const Dynamic = styled.div`
  padding : 0.625rem;
  overflow : scroll;
  &::-webkit-scrollbar {
    display : none;
  }
`

const ContentSection = styled.div`
  margin : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};
`

const Instruction = styled.div`
  background : rgba(0, 0, 0, 0.25);
  margin : 1rem 1rem 0.5rem;
  height : 3.75rem;
`

const Buttons = styled.div`
  float : right;
  padding : 0 1rem;
  margin-bottom : 0.5rem;
`

const Before = styled.button`
  border : none;
  width : 2rem;
  margin : 0 0.25rem;
  color : #008BFF;
  height : 2rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;

  img {
    width : 0.5rem;
  }
`

const After = styled.button`
  border : none;
  width : 2rem;
  color : #008BFF;
  margin : 0 0.25rem;
  height : 2rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;

  img {
    width : 0.5rem;
  }
`

const GPT = styled.button`
  border : none;
  width : 4rem;
  color : #FFFFFF;
  height : 2rem;
  font-weight : bold;
  font-size : 1rem;
  background : #008BFF;
  border-radius : 1rem;
  margin-left : 0.5rem;
`

const Train = styled.div`
  margin : 3.25rem 1rem 1rem;
  height : ${(props) => `${(props.height - 278) / 16}rem`};
`

const CodeArea = styled.textarea`
  resize : none;
  font-size : 1rem;
  padding : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
  height : ${(props) => `${(props.height - 464) / 16}rem`};
`

const Buttons_ = styled.div`
  float : right;
  margin : 0.425rem 0.025rem;
`

const Interpret = styled.button`
  border : none;
  width : 4rem;
  color : #008BFF;
  height : 2rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Submit = styled.button`
  border : none;
  width : 4rem;
  margin : 0 0.5rem;
  color : #008BFF;
  height : 2rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const CodeResult = styled.div`
  width : ${(props) => `${(props.width - 304) / 16}rem`};
  height : 9rem;
  border : 1px solid rgba(0, 0, 0, 0.5);
  background : #FFFFFF;

  p {
    color : rgba(0, 0, 0, 0.5);
    margin : 0.5rem 1rem 0rem;
  }
`

const ResultPre = styled.pre`
  height : 6.375rem;
`

const Result = styled.div`
  padding : 0 1rem;
  font-size : 1rem;
  width : ${(props) => `${(props.width - 388) / 16}rem`};
  height : 5.375rem;
  overflow : scroll;
  &::-webkit-scrollbar {
    display : none;
  }
`

const ChatbotSection = styled.div`
  margin : 2rem 2rem 2rem 0;
  width : 25rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Chat = styled.div`
  margin : 1rem;
  height : ${(props) => `${(props.height - 277.5) / 16}rem`};
  overflow : scroll;
  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog = styled.p`
  margin : 0.5rem 0;
  padding : 0.875rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  border-radius : 1rem 0rem 1rem 1rem;
  background : #FFFFFF;
`

const ChatType = styled.div`
  height : 3rem;
  display : flex;
  align-item : center;
  justify-content : center;
  border-top : 0.25rem solid #008BFF;
  border-bottom : 0.25rem solid #008BFF;
`

const Type = styled.div`
  font-weight : bold;
  padding : 0.25rem 0.5rem;
  margin : 0.5rem 1rem;
  color : #008BFF;

  &:hover {
    background : rgba(0, 139, 255, 0.25);
  }
`

const ChatInput = styled.div`
  display : flex;
  height : 3.5rem;
  background : #008BFF;
  border-radius : 0 0 1rem 1rem;
`

const InputArea = styled.input`
  margin : 0.375rem 0.75rem;
  font-size : 1rem;
  padding : 0 1rem;
  height : 2.5rem;
  width : 18.25rem;
  border : none;
  border-radius : 1.25rem;
`

const InputButton = styled.button`
  border : 0.125rem solid #FFFFFF;
  width : 2.5rem;
  color : #FFFFFF;
  margin : 0.375rem 0;
  height : 2.5rem;
  font-weight : bold;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`