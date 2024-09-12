import axiosInstance from './axiosInstance'


const getUserInfo = async () => {
    try {
        const userid = localStorage.getItem('userid');
        const UserIdDto = {
            'userId' : userid
        };
        const res = await axiosInstance.post('/checkAuth/checkAuth/getUserInfo', UserIdDto);
        return res.data;
    }
    catch (err) {
         console.error(err); 
    }
};

export default getUserInfo;
  