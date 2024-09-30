import React from 'react';
import Tmp from './components/Test/tmp.jsx';
import ProtectedRoute from './ProtectedRoute';
import Test from './components/Test/index.jsx';
import { Routes, Route } from 'react-router-dom';
import Mainpage from './components/Mainpage/index.jsx';
import Mypage_todo from './components/Mypage/index_1.jsx';
import Mypage_lecture from './components/Mypage/index_2.jsx';
import Mypage_question from './components/Mypage/index_3.jsx';
import Study_theory from './components/Study/index_theory.jsx';
import Study_training from './components/Study/index_training.jsx';
import Study_comprehension from './components/Study/index_comprehension.jsx';


function App() {
  return (
    <Routes>
      <Route path="/tmp" element={<Tmp />}></Route>
      <Route path="/" element={<Mainpage />}></Route>
      <Route path="/test" element={<ProtectedRoute component={Test} />}></Route>
      <Route path="/mypage/todo" element={<ProtectedRoute component={Mypage_todo} />}></Route>
      <Route path="/mypage/lecture" element={<ProtectedRoute component={Mypage_lecture} />}></Route>
      <Route path="/mypage/question" element={<ProtectedRoute component={Mypage_question} />}></Route>
      <Route path="/study/theory/:subChapId/:text" element={<ProtectedRoute component={Study_theory} />}></Route>
      <Route path="/study/training/:subChapId/:text" element={<ProtectedRoute component={Study_training} />}></Route>
      <Route path="/study/comprehension/:chapId" element={<ProtectedRoute component={Study_comprehension} />}></Route>
    </Routes>
  );
}

export default App;
