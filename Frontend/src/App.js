import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Mainpage from './components/Mainpage/index.jsx';
import Study_theory from './components/Study/index_theory.jsx'
import Study_training from './components/Study/index_training.jsx'
import Test from './components/Test/index.jsx';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Mainpage />}></Route>
      <Route path="/test" element={<Test />}></Route>
      <Route path="/study_theory" element={<Study_theory />}></Route>
      <Route path="/study_training" element={<Study_training />}></Route>
    </Routes>
  );
}

export default App;
