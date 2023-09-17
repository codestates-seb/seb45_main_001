import { styled } from 'styled-components';
import React, { useEffect } from 'react';
import { apiCall } from '../../api/authapi';
import { useDispatch, useSelector } from 'react-redux';
import { DataState, updateName, updateLogin, updateMail } from '../../slice/authslice';

const OauthGooglewrap = styled.div``;

const OauthGoogleimg = styled.img`
    width: 32px;
    height: 32px;
    cursor: pointer;

    &:hover {
        opacity: 0.6;
    }
`;

function OauthGoogle() {
    const dispatch = useDispatch();
    const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;
    const redirectUri = process.env.REACT_APP_GOOGLE_REDIRECT_URI;
    const scope = 'openid profile email';
    const responseType = 'code';

    const authUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&response_type=${responseType}`;

    const handleGoogleLogin = () => {
        try {
            window.location.href = authUrl;
        } catch (error) {
            console.error('구글 접속 실패', error);
        }
    };

    useEffect(() => {
        const params = new URL(document.location.toString()).searchParams;
        const code = params.get('code');

        if (code) {
            const GOOGLE_SECRET = process.env.REACT_APP_GOOGLE_CLIENT_SECRET;
            const grantType = 'authorization_code';

            apiCall(
                {
                    method: 'POST',
                    url: `https://oauth2.googleapis.com/token?code=${code}&client_id=${clientId}&redirect_uri=${redirectUri}&grant_type=${grantType}&client_secret=${GOOGLE_SECRET}`,
                    headers: { 'Content-type': 'application/x-www-form-urlencoded;charset=utf-8' },
                },
                false,
            )
                .then((res) => {
                    console.log(res.data.access_token);
                    // localStorage.setItem('jwt', res.data.access_token);
                    return apiCall(
                        {
                            method: 'GET',
                            url: `https://www.googleapis.com/oauth2/v2/userinfo?access_token=${res.data.access_token}`,
                            headers: {
                                Authorization: `Bearer ${res.data.access_token}`,
                                'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
                            },
                        },
                        false,
                    );
                })
                .then((res) => {
                    console.log(res);
                    console.log(res.data.name);
                    console.log(res.data.email);
                    // dispatch(updateName(res.data.name));
                    // dispatch(updateMail(res.data.email));
                    // dispatch(updateLogin(true));
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
    }, []);

    return (
        <>
            <OauthGooglewrap>
                <OauthGoogleimg src="/google.png" alt="google oauth logo" onClick={handleGoogleLogin}></OauthGoogleimg>
            </OauthGooglewrap>
        </>
    );
}

export default OauthGoogle;
