import React, { useEffect, useState } from 'react';

import axios from 'axios';
import cookie from 'react-cookies';
import axiosInstance from '../../axiosInstance'
import { SHA256 } from 'crypto-js';

function App() {
  const [curriculumIds, setCurriculumIds] = useState([]);
  const [currentcurriculumId, setcurrentcurriculumId] = useState(0);
  const [loginData, setLoginData] = useState({ id: 'g7donut', password: 'qwer1234!' });
  const [createUserData, setcreateUserData] = useState({ create_username: 'g7donut', create_email: 'qwer1234!', create_id: 'g7donut', create_password: 'qwer1234!', create_birth: '19991103' });


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });
  };

  const handleInputChange_Create = (a) => {
    const { name, value } = a.target;
    setcreateUserData({
      ...createUserData,
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
    axios.post(process.env.REACT_APP_SPRING + '/api/token',refresh_token_json,config)
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const loginClick = () => {

    const hash_password=SHA256(loginData.password).toString();
    const LoginCallDto = {
      'id' : loginData.id,
      'password' : hash_password
    };
    axios.post(process.env.REACT_APP_SPRING + '/api/auth/login', LoginCallDto, config)
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


  const nonHashloginClick = () => {
    const LoginCallDto = {
      'id' : loginData.id,
      'password' : loginData.password
    };
    axios.post(process.env.REACT_APP_SPRING + '/api/auth/login', LoginCallDto, config)
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

  const createUserClick = async () => {

    const hash_passwd = SHA256(createUserData.create_password).toString();
    const UserAddCallDto = {
      'username' : createUserData.create_username,
      'email' : createUserData.create_email,
      'id' : createUserData.create_id,
      'password' : hash_passwd,
      'birth' :createUserData.create_birth
    };
    
    const res=await axios.post(process.env.REACT_APP_SPRING + "/user/create", UserAddCallDto);
    const userTableId=res.data;
    //초기 미션 할당
    try {
      const UserMissionDataCallDto = {
        'userId' : userTableId
        };
      await axios.post(process.env.REACT_APP_SPRING + '/mission/init', UserMissionDataCallDto);
    }
    catch(err){
      console.log(err);
    }
  }

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

  useEffect(() => {

    const getCurriculumIdData =  () => {
      const storedData = JSON.parse(localStorage.getItem('chapters'));
      if (storedData && Array.isArray(storedData)){
        // 상위 리스트의 curriculumId만 추출 (subChapters는 무시)
        const topLevelIds = storedData.map(item => item.curriculumId);
        
        // 추출된 ID 배열을 상태에 저장
        setCurriculumIds(topLevelIds);
      }
    };

    getCurriculumIdData();
  }, []);

  const getChapterQuiz =  async () =>{
    const userid = localStorage.getItem('userid');
    console.log("불러올려고 하는 curri id "+ curriculumIds[currentcurriculumId])
      const DataCallDto = {
        'userId': userid,
        'curriculumId': "3"
      };
      try {
        const res = await axiosInstance.post('/test/create/init', DataCallDto);
        console.log(res);
      } catch (err) {
        console.error(err);
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
        <button onClick={nonHashloginClick}>nonhashLogin</button>

      </div>
      <div>
        <input
          type="text"
          name="create_username"
          value={createUserData.create_username}
          onChange={handleInputChange_Create}
          placeholder="create_username"
        />
        <input
          type="text"
          name="create_email"
          value={createUserData.create_email}
          onChange={handleInputChange_Create}
          placeholder="create_email"
        />
        <input
          type="text"
          name="create_id"
          value={createUserData.create_id}
          onChange={handleInputChange_Create}
          placeholder="create_id"
        />
        <input
          type="text"
          name="create_password"
          value={createUserData.create_password}
          onChange={handleInputChange_Create}
          placeholder="create_password"
        />
        <input
          type="text"
          name="create_birth"
          value={createUserData.create_birth}
          onChange={handleInputChange_Create}
          placeholder="create_birth"
        />                        
        <button onClick={createUserClick}>create User</button>
      </div>
      <button onClick={handleTestApiClick}>Test API</button>
      <button onClick={handleUserInfoClick}>Get User Info</button>
      <button onClick={accestTokenRecallTestApiClick}>Get new Access Token</button>
      <button onClick={getCurriculumInfoWithAuth}>Auth get curriculum Info</button>
      <button onClick={getChapterQuiz}>get quiz</button>
      <button onClick={tmpfunc}>tmp func</button>

    </div>
  );
}

export default App;