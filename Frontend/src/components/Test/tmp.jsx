import React, { useState } from 'react';
import axios from 'axios';
import cookie from 'react-cookies';
import axiosInstance from '../../axiosInstance'


function App() {
  
  const [loginData, setLoginData] = useState({ id: 'g7donut', password: 'qwer1234!' });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

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
    'refreshToken': refresh_token
  };
  const config = {
    headers: {
      'Content-Type': 'application/json'
    }
  };
  const accestTokenRecallTestApiClick = () => {
    axios.post('/api/api/token',refresh_token_json,config)
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const loginClick = () => {
    axios.post('/api/api/auth/login', loginData, config)
      .then((response) => {
        const accessToken = response.data.accessToken;
        localStorage.setItem("userid",response.data.userid);
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("rootid", 1);
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error); 
      });
  };



const getCurriculumInfoWithAuth = async () => {
    try {
      const response = await axiosInstance.get('/curriculum/curriculum/1');
      console.log(response.data);
    } catch (error) {
      console.error(error); 
    }
  };

  const tmpfunc = async () =>{
    try {
      const userid = localStorage.getItem('id');
      //const res = await axiosInstance.get(`/checkAuth/checkAuth/getUserInfo/${userid}`);
      const res = await axiosInstance.get(`/checkAuth/checkAuth/getUserInfo/g7donut2`);

      console.log(res.data);

    } catch (error) {
      console.error(error); 
    }
  }

  return (
    <div className="App">
      <div>
        <input
          type="text"
          name="id"
          value={loginData.id}
          onChange={handleInputChange}
          placeholder="ID"
        />
        <input
          type="text"
          name="password"
          value={loginData.password}
          onChange={handleInputChange}
          placeholder="Password"
        />
        <button onClick={loginClick}>Login</button>
      </div>
      <button onClick={handleTestApiClick}>Test API</button>
      <button onClick={handleUserInfoClick}>Get User Info</button>
      <button onClick={accestTokenRecallTestApiClick}>Get new Access Token</button>
      <button onClick={getCurriculumInfoWithAuth}>Auth get curriculum Info</button>
      <button onClick={tmpfunc}>tmp func</button>

    </div>
  );
}

export default App;