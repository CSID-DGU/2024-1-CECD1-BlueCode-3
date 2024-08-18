import React, { useRef, useState, useEffect } from 'react';


const useChatbot = () => {
    const [dialog, setDialog] = useState("");
    const [dialogs, setDialogs] = useState([]);
    const AddDialog = () => {
        if(dialog) {
        setDialogs([...dialogs, <Dialog> {dialog} </Dialog>]);
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

}

export default useChatbot;