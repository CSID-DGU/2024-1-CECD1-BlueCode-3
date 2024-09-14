import axios from 'axios';
import cookie from 'react-cookies';

const URL = "http://3.37.159.243:8080"

// 액세스 토큰을 로컬 스토리지에서 가져오는 함수
const getAccessToken = () => {
    return localStorage.getItem('accessToken');
};

// 리프레시 토큰으로 액세스 토큰 재발급
const refreshAccessToken = async () => {
    try {
      const refresh_token = cookie.load('refresh_token');
      const refresh_token_json = {
        'refreshToken': refresh_token
      };
      const config = {
        headers: {
          'Content-Type': 'application/json'
        }
      };
      const response = await axios.post(URL + '/api/token', refresh_token_json, config);
      return response.data.accessToken;
    } catch (error) {
      throw new Error('Failed to refresh access token');
    }
};
  


const axiosInstance = axios.create({
    baseURL: 'http://3.37.159.243:8080', // 백엔드 API 기본 URL 설정
});


// 요청 인터셉터 설정
axiosInstance.interceptors.request.use(
    (config) => {
      const token = getAccessToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);


// 응답 인터셉터 설정
axiosInstance.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;
      if (error.response && error.response.status === 401) {
        if (error.response.data === 'Token is expired') {
          try {
            const newAccessToken = await refreshAccessToken();
            localStorage.setItem('accessToken', newAccessToken);
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            return axiosInstance(originalRequest);
          } catch (refreshError) {
            console.error('Failed to refresh access token', refreshError);
            // 리프레시 토큰이 만료되었거나 유효하지 않은 경우, 로그아웃 처리
            localStorage.removeItem('accessToken');
            //로그아웃 처리
          }
        } else {
          // 그 외의 401 에러는 로그아웃 처리
          console.log("로그아웃 처리");
          localStorage.removeItem('accessToken');
          //로그아웃 처리
        }
      }
      return Promise.reject(error);
    }
  );

export default axiosInstance;