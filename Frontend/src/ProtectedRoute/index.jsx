import { Navigate } from 'react-router-dom';

const getToken = () => {
    return localStorage.getItem('accessToken');
};

const ProtectedRoute = ({ component: Component }) => {
    const token = getToken();

    return token ? <Component />:<Navigate to="/" replace />;
};

export default ProtectedRoute;