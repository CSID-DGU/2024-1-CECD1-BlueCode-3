import "./xterm.css";
import { Terminal } from "xterm";
import styled from 'styled-components';
import { languages } from "../languages";
import Editor from '@monaco-editor/react';
import { FitAddon } from "xterm-addon-fit";
import React, { useEffect, useRef, useState } from "react";



const TerminalJsx = () => {
  const terminalRef = useRef(null);
  const [term, setTerm] = useState(null);
  const [ws, setWs] = useState(null);
  const [connected, setConnected] = useState(false);
  const wsRef = useRef(null);
  const connectedRef = useRef(false);
  const [text, setText] = useState('');
  const [selectedLanguage, setSelectedLanguage] = useState("Python3"); // languages.js에 존재하는 언어로 설정하면 됨(key에 해당하는 String: ex. Bash, Python3, java, ...)
  const [args, setArgs] = useState('');
  const [termStr, setTermStr] = useState("");
  const [count, setCount] = useState(null);
  const [isHereDoc, setIsHereDoc] = useState(false);
  const filename = "main";
  const fitAddon = useRef(new FitAddon());

  const HEREDOC_BEGIN = "cat << 'BC_EOF'";
  const regex = /BC_EOF(?!')/;

  useEffect(() => {
    connectedRef.current = connected;
    wsRef.current = ws;
  }, [connected, ws]);

  useEffect(() => {
    if (!terminalRef.current) {
      const terminal = new Terminal({ cursorBlink: true, rows: 15, theme: { background: "#1e1e1e" } });
      terminal.loadAddon(fitAddon.current);
      terminal.open(document.getElementById("terminal"));
      terminal.onData((data) => {
        console.log("data: " + data.toString());
        console.log("input connected: " + connectedRef.current.toString());
  
        if (connectedRef.current && wsRef.current) {
          wsRef.current.send('1' + data);
        } else {
          console.warn("WebSocket is not connected or initialized.");
        }
      });
      terminal.onResize(e => {
        if (connectedRef.current && wsRef.current) {
          wsRef.current.send(`2 ${e.cols} ${e.rows}`);
        }
      });
      terminalRef.current = terminal;
      setTerm(terminal);
    }
  }, []);
  
  const connect = (callback) => {
    if (connectedRef.current) {
      return;
    }

    term.clear();
    term.write("Connecting to server...\r\n");
    const socket = new WebSocket(`ws://3.37.159.243:5000/terminal`);
    console.log(socket !== null)
    socket.onopen = () => {
      term.write("Connected.\r\nPlease wait...\r\n");
      wsRef.current = socket; 
      connectedRef.current = true; 

      console.log("init (after setting connected): " + connectedRef.current.toString());

      if (typeof callback === 'function') {
        callback();
      }
    };

    socket.onmessage = (d) => {
      const data = d.data.substring(1);
      if (isHereDoc === true) {
        if (regex.exec(data) !== null) {
          setIsHereDoc(false);
          const last = data.toString().split(regex);
          if (last[1]) {
            setTermStr(last[1]);
            term.write(last[1]);
          }
        }
        return;
      }

      if (data.indexOf(HEREDOC_BEGIN) !== -1) {
        if (regex.exec(data) === null) {
          setIsHereDoc(true);
          term.write('uploadingFile...\r\n');
        }
        return;
      }

      switch(d.data[0]) {
        case '1':
          setTermStr(data.substr(-10));
          term.write(data);
          break;
        case '2':
          setCount(data);
          break;
      }
    };

    socket.onerror = (ev) => {
      term.write(`Error: ${ev}\r\n`);
    };

    socket.onclose = () => {
      term.write("\r\nDisconnected.\r\n\r\n");
      setWs(null);
      setConnected(false);
    };
  };

  const executeCheck = (command) => {
    console.log("executeCheck connected: " + connectedRef.current);
    if (!connectedRef.current) {
      console.log("not connected try connect: " + connectedRef.current)
      connect(() => {
        if (!connectedRef.current) {
          term.write("Failed to connect. Please try again.\r\n");
          return;
        }
        console.log("finish reconnect");
        executeCheck(command);
      });
      return;
    }

    console.log("connected == true passed")
    if (command) {
      execute(true, command);
      return;
    }

    if (!languages[selectedLanguage]?.command) {
      execute(true);
      return;
    }

    execute(true, command);
  };

  const execute = (isAll, command) => {

    console.log("execute command: " + command);

    if (!connectedRef.current) {
      connect(() => executeCheck(isAll, command));
      return;
    }

    if (!command) {
      if (isAll) {
        const ext = languages[selectedLanguage].ext;
        let args = languages[selectedLanguage]?.args || "";

        command = ("cat << 'BC_EOF' > {FILENAME}.{EXT}\n{SOURCE}\nBC_EOF\nhistory -c\n" +
          languages[selectedLanguage].command)
          .replace(/{ARGS}/g, args)
          .replace(/{FILENAME}/g, filename.replace(`.${ext}`, ''))
          .replace(/{EXT}/g, ext)
          .replace('{SOURCE}', text)
          .replace(/\t/g, '    ');

          // 예: 파이썬 커맨드
          // command: cat << 'BC_EOF' > main.py
          // print("hello")
          // BC_EOF
          // history -c
          // python3 main.py
      }
    }

    console.log("execute connectedRef: " + connectedRef.current);
    if (connectedRef.current && wsRef.current) {
      term.scrollToBottom();
      console.log("ws === null : " + wsRef.current === null);
      wsRef.current.send(`1${command}\r`);
    } else {
      term.write(`Connect first.\r\n`);
    }
  };

  return (
    <div style={{height : "100%", backgroundColor : "#000000"}}>
      <div>
        <Editor height="16.25rem"
                theme="vs-dark"
                defaultLanguage="python"
                value={text} onChange={(value)=>setText(value)}>
        </Editor>
        <Buttons>
          <Button onClick={() => connect()}> 연결 </Button>
          <Button onClick={() => executeCheck()}> 실행 </Button>
        </Buttons>
      </div>
      <TerminalSection id="terminal" />
    </div>
  );
};

export default TerminalJsx;



const Buttons = styled.div`
  display : flex;
  justify-content : right;
`

const Button = styled.button`
  width : 4rem;
  height : 2rem;
  color : #FFFFFF;
  cursor : pointer;
  margin : 0.25rem;
  font-weight : bold;
  font-size : 0.875rem;
  background : #000000;
  border-radius : 1rem;
  border : 0.125rem solid #FFFFFF;
`

const TerminalSection = styled.div`
  display : flex;
  overflow : scroll;
  
  &::-webkit-scrollbar {
    display : none;
  }
`