import { Outlet, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const Authguard = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const jwt = sessionStorage.getItem('jwt');

        if (!jwt) {
            navigate('/');
        }
    }, [navigate]);
    return <Outlet />;
};


export default Authguard;
