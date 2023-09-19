import { Outlet, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { DataState } from '../../slice/authslice';

const Authguard = () => {
    const navigate = useNavigate();
    const isLogin = useSelector((state: { data: DataState }) => state.data.isLogin);

    // useEffect(() => {
    //     const jwt = localStorage.getItem('jwt');

    //     if (!jwt) {
    //         navigate('/');
    //     }
    // }, [navigate]);

    useEffect(() => {
        if (!isLogin) {
            navigate('/');
        }
    }, [navigate]);

    return <Outlet />;

};


export default Authguard;
