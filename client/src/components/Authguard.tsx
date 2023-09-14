import { Outlet, useNavigate } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import { useEffect } from 'react';

const Authguard = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const userId = sessionStorage.getItem('memberid');

        if (!userId) {
            navigate('/');
        }
    }, [navigate]);
    return <Outlet />;
};

export default Authguard;
