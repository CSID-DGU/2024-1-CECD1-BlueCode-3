import React, { useRef, useState, useEffect } from 'react';


const useChatbot = () => {
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