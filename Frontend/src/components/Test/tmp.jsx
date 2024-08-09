import React from 'react';
import axios from 'axios';
import cookie from 'react-cookies';

function App() {
  const handleTestApiClick = () => {
    axios.get('/api/api/auth/test')
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };


  const handleUserInfoClick = () => {
    axios.get('/user/user/getUserInfo/testId2')
      .then((response) => {
        console.log(response.data); 
      })
      .catch((error) => {
        console.error(error); 
      });
  };

  const refresh_token = cookie.load('refresh_token');
  const refresh_token_json = {
    'refreshToken' : refresh_token
  };
  const config = {"Content-Type": 'application/json'};
  const accestTokenRecallTestApiClick = () => {
    axios.post('/api/api/token',refresh_token_json,config)
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const login_test_data = {
    'id' : 'tester',
    'password' : '1234'
  };
  const loginClick = () => {
    axios.post('/api/api/auth/login',login_test_data,config)
      .then((response) => {
        const accessToken = response.data.accessToken;
        localStorage.setItem("accessToken", accessToken);
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error); 
      });

  };

  return (
    <div className="App">
      <button onClick={handleTestApiClick}>Test API</button>
      <button onClick={handleUserInfoClick}>Get User Info</button>
      <button onClick={loginClick}>Login</button>
      <button onClick={accestTokenRecallTestApiClick}>Get new Access Token</button>
    </div>
  );
}

export default App;