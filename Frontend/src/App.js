import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Mainpage from './components/Mainpage/index.jsx';
import Mypage_todo from './components/Mypage/index_1.jsx';
import Mypage_lecture from './components/Mypage/index_2.jsx';
import Mypage_question from './components/Mypage/index_3.jsx';
import Mypage_info from './components/Mypage/index_4.jsx';
import Chatbot from './components/Chatbot/index.jsx';
import Study_theory from './components/Study/index_theory.jsx'
import Study_training from './components/Study/index_training.jsx'
import Test from './components/Test/index.jsx';
import Test_ex from './components/Test/index_ex.jsx';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Mainpage />}></Route>
      <Route path="/test" element={<Test />}></Route>      
      <Route path="/test_ex" element={<Test_ex />}></Route>
      <Route path="/study_theory" element={<Study_theory />}></Route>
      <Route path="/study_training" element={<Study_training />}></Route>
      <Route path="/mypage/todo" element={<Mypage_todo />}></Route>
      <Route path="/mypage/lecture" element={<Mypage_lecture />}></Route>
      <Route path="/mypage/question" element={<Mypage_question />}></Route>
      <Route path="/mypage/info" element={<Mypage_info />}></Route>
      <Route path="/chatbot" element={<Chatbot />}></Route>
    </Routes>
  );
}

export default App;
