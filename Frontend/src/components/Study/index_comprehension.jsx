import styled from 'styled-components';
import BCODE from '../../logo_w.png'
import Left from '../../left.png';
import Right from '../../right.png';
import Input from '../../input.png';
import { useRef, useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';


const extractAndReplaceInputs = (str, replacements) => {
  // 정규 표현식으로 모든 input() 내의 문자열을 추출
  const regex = /input\(([^)]*)\)/g;
  const extractedContents = [];
  
  // 모든 매치를 찾고 대체
  let replaceIndex = 0;
  const replacedStr = str.replace(regex, (fullMatch, group1) => {
    extractedContents.push(group1);
    const replacement = replacements[replaceIndex] || fullMatch; // 대체 문자열이 없으면 원본 유지
    replaceIndex++;
    return replacement;
  });

  return { extracted: extractedContents, replaced: replacedStr };
};



function Study_training() {
  const [answer, setAnswer] = useState('');

  const [pyodide, setPyodide] = useState(null);
  const [code, setCode] = useState("");
  const [result, setResult] = useState("");

  const [savedCode, setSavedCode] = useState("");
  const [output, setOutput] = useState({ extracted: [], replaced: '' });
  const [replacements, setReplacements] = useState([]);

  const replaceCode = () => {
    // 모든 input() 부분에 대해 대체 문자열을 입력받음
    const newReplacements = [];
    const regex = /input\(([^)]*)\)/g;
    let match;
    while ((match = regex.exec(savedCode)) !== null) {
      const replacement = prompt(`Replace ${match[0]} with:`);
      newReplacements.push("'" + replacement + "'");
    }
    setReplacements(newReplacements);

    const result = extractAndReplaceInputs(savedCode, newReplacements);
    setOutput(result);
  };

  
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
        const res = await pyodide.runPython(codeSended);
        setResult(res);
        setSavedCode(codeSended);
      } catch (err) {
        setResult(err.message);
      }
    }
  };

  const checkCode = async () => {
    if (pyodide) {
      try {
        const res = await pyodide.runPython(output.replaced);

        if(Number(res) === 5 || res === "5") {
          alert("정답입니다.");
        }
        else {
          alert("오답입니다.");
        }
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
  const styled = {};
  const AddToNavContent = () => {
    if (navValue)
      setNavValue(false);
    else
      setNavValue(true);
  }

  const [dialog, setDialog] = useState("");
  const [dialogs, setDiv] = useState([]);
  const AddDialog = () => {
    if(dialog) {
      setDiv([...dialogs, <Dialog> {dialog} </Dialog>]);
      setDialog("");
    }
  }
  
  const chat = useRef();
  const scrollToBottom = () => {
    chat.current?.scrollIntoView();
  };

  useEffect(()=>{
    scrollToBottom();
  }, [dialogs]);

  const [divValue, setDivValue] = useState('');
  const borderStyle = { borderRadius : "0.5rem", borderBottom : "0.25rem solid #008BFF" };
  const getDivValue = (divVal) => {
    setDivValue(divVal);
  }

  const [qtype, setQtype] = useState(3);
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
        </Logo>
      </SectionBar>
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={{ textDecoration : "none" }} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/mypage/todo"><Nav> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={{ textDecoration : "none" }} to="/"><Nav> ㅇ 로그아웃 </Nav></NavLink>
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
          <InstructionSection status={type}>  
            <Instruction></Instruction>
            <Buttons>
              <Before> <img src={Left}></img> </Before>
              <After> <img src={Right}></img> </After>
              <GPT onClick={ShowGpt}> GPT </GPT>
            </Buttons>
          </InstructionSection>
          {qtype !== 3 && (<ChangingSection>
            {qtype === 1 && (<SelectionArea width={contentWidth}>
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
            {qtype === 2 && (<WritingArea width={contentWidth} onChange={(e)=>setAnswer(e.target.value)}></WritingArea>)}
            <Submit_> 제출 </Submit_>
          </ChangingSection>)}
          {qtype === 3 && <TrainSection>
            <Train height={height} width={contentWidth}>
              <CodeArea height={height} width={contentWidth} value={code} onChange={(e)=>setCode(e.target.value)}></CodeArea>
              <CodeResultSection>
                <CodeResult>
                  <p> --- 실행 결과 --- </p>
                  <Buttons_>
                    <Interpret onClick={runPythonCode}> 실행 </Interpret>
                    <Save onClick={replaceCode}> 저장 </Save>
                    <Submit onClick={checkCode}> 제출 </Submit>
                  </Buttons_>
                </CodeResult>
                <ResultPre>
                  <Result width={contentWidth}> {result} </Result>
                </ResultPre>
              </CodeResultSection>
            </Train>
          </TrainSection>}
        </ContentSection>
        {gptValue && (<ChatbotSection>
          <Chat height={height}>
            {dialogs.map(div => div)}
            <div ref={chat}></div>
          </Chat>
          <ChatType>
            <Type style={divValue === "개념"?borderStyle:{}} onClick={()=>getDivValue("개념")}> #개념 </Type>
            <Type style={divValue === "코드"?borderStyle:{}} onClick={()=>getDivValue("코드")}> #코드 </Type>
            <Type style={divValue === "오류"?borderStyle:{}} onClick={()=>getDivValue("오류")}> #오류 </Type>
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

const Nav = styled.div`
  display : flex;
  font-weight : bold;
  padding : 0.625rem;
  align-items : left;
  flex-direction : column;
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
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  padding : 0.375rem 0.375rem 0.375rem 1.75rem;

  &:hover {
    color : #008BFF;
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
  display : flex;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 304) / 16}rem`};
`

const InstructionSection = styled.div`

  ${({status}) => {
    switch (status) {
      case '객관식':
        return 'width : 30rem';
      case '주관식':
        return 'width : 30rem';
      case '서술식':
        return 'width : 23.75rem';
    }
  }}
`

const ChangingSection = styled.div`
  display : flex;
  flex-direction : column;
`

const SelectionArea = styled.div`
  width : ${(props) => `${(props.width - 840) / 16}rem`};
`

const Selection = styled.div`
  display : flex;
  width : 7.5rem;
  margin : 1rem;
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

const WritingArea = styled.textarea`
  resize : none;
  padding : 1rem;
  font-size : 1.25rem;
  width : ${(props) => `${(props.width - 840) / 16}rem`};
  height : 1.5rem;
  margin-top : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Submit_ = styled.button`
  width : 4rem;
  height : 2rem;
  color : #008BFF;
  margin : 1rem auto 0rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Instruction = styled.div`
  height : 34.25rem;
  margin : 1rem 1rem 0.5rem;
  background : rgba(0, 0, 0, 0.25);
`

const Buttons = styled.div`
  float : right;
  padding : 0 1rem;
  margin-bottom : 0.5rem;
`

const Before = styled.button`
  width : 2rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  margin : 0 0.25rem;
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
  width : 2rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  margin : 0 0.25rem;
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
  width : 4rem;
  border : none;
  height : 2rem;
  color : #FFFFFF;
  font-size : 1rem;
  font-weight : bold;
  background : #008BFF;
  margin-left : 0.5rem;
  border-radius : 1rem;
`

const TrainSection = styled.div`

`

const Train = styled.div`
  margin : 1rem;
  height : ${(props) => `${(props.height - 700) / 16}rem`};
`

const CodeArea = styled.textarea`
  resize : none;
  padding : 1rem;
  font-size : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 750) / 16}rem`};
  height : ${(props) => `${(props.height - 346) / 16}rem`};
`

const CodeResultSection = styled.div`
  height : 9rem;
  display : flex;
  background : #FFFFFF;
  flex-direction : column;
  border : 1px solid rgba(0, 0, 0, 0.5);
`

const CodeResult = styled.div`
  display : relative;

  p {
    float : left;
    margin : 0.5rem 1rem 0rem;
    color : rgba(0, 0, 0, 0.5);
  }
`

const Buttons_ = styled.div`
  float : right;
  margin : 0.425rem 0.025rem 0rem;
`

const Interpret = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Save = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  font-weight : bold;
  margin-left : 0.5rem;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const Submit = styled.button`
  width : 4rem;
  border : none;
  height : 2rem;
  color : #008BFF;
  margin : 0 0.5rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #FFFFFF;
  border-radius : 1rem;
  border : 0.125rem solid #008BFF;
`

const ResultPre = styled.pre`
  height : 6.375rem;
`

const Result = styled.div`
  padding : 0 1rem;
  font-size : 1rem;
  height : 5.375rem;
  overflow : scroll;

  &::-webkit-scrollbar {
    display : none;
  }
`

const ChatbotSection = styled.div`
  width : 25rem;
  border-radius : 1rem;
  margin : 2rem 2rem 2rem 0;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
`

const Chat = styled.div`
  margin : 1rem;
  display : flex;
  overflow : scroll;
  align-items : flex-end;
  flex-direction : column;
  height : ${(props) => `${(props.height - 277.5) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const Dialog = styled.p`
  margin : 0.5rem 0;
  width : fit-content;
  background : #FFFFFF;
  padding : 0.75rem 1rem;
  word-break : break-word;
  overflow-wrap : break-word;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  border-radius : 1.5rem 1.5rem 0rem 1.5rem;
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
  color : #008BFF;
  font-weight : bold;
  margin : 0.5rem 1rem;
  padding : 0.25rem 0.5rem;

  &:hover {
    border-radius : 0.5rem;
    border-bottom : 0.25rem solid rgba(0, 139, 255, 0.375);
  }
`

const ChatInput = styled.div`
  display : flex;
  height : 3.5rem;
  background : #008BFF;
  border-radius : 0 0 1rem 1rem;
`

const InputArea = styled.input`
  border : none;
  height : 2.5rem;
  width : 18.25rem;
  padding : 0 1rem;
  font-size : 1rem;
  border-radius : 1.25rem;
  margin : 0.375rem 0.75rem;
`

const InputButton = styled.button`
  width : 2.5rem;
  color : #FFFFFF;
  height : 2.5rem;
  font-weight : bold;
  margin : 0.375rem 0;
  font-size : 1.25rem;
  background : #008BFF;
  border-radius : 1.25rem;
  border : 0.125rem solid #FFFFFF;

  img {
    margin : auto 0;
    width : 0.625rem;
  }
`