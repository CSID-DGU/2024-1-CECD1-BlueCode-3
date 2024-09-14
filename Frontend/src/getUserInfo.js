import axiosInstance from './axiosInstance'

const URL = "http://3.37.159.243:8080"

const getUserInfo = async () => {
    try {
        const userid = localStorage.getItem('userid');
        const UserIdDto = {
            'userId' : userid
        };
        const res= await axiosInstance.post('/checkAuth/getUserInfo', UserIdDto)
        return res.data;
    }
    catch (err) {
         console.error(err); 
    }
};

export default getUserInfo;
  