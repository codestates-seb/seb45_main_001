import { styled } from 'styled-components';
// import { useEffect, useState } from 'react';
// import { apiCall } from '../../api/authapi';
// import { useDispatch, useSelector } from 'react-redux';
// import { DataState, updateName, updateLogin, updateMail } from '../../slice/authslice';

// const OauthGooglewrap = styled.div``;

// const OauthGoogleimg = styled.img`
//     width: 32px;
//     height: 32px;
//     cursor: pointer;

//     &:hover {
//         opacity: 0.6;
//     }
// `;

// function OauthGoogle() {
//     const dispatch = useDispatch();
//     const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;
//     const redirectUri = process.env.REACT_APP_GOOGLE_REDIRECT_URI;
//     const scope = 'openid profile email';
//     const responseType = 'code';

//     const authUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&response_type=${responseType}`;

//     const [attemptedLogin, setAttemptedLogin] = useState(false);

//     // const handleGoogleLogin = () => {
//     //     try {
//     //         apiCall({
//     //             method: 'GET',
//     //             url: 'oauth2/authorization/google', // 백엔드 엔드포인트
//     //             // data: { code },
//     //         })
//     //             .then((response) => {
//     //                 console.log("오오쓰 리스폰스", response)
//     //                 localStorage.setItem('jwt', response.data.accessToken);
//     //                 localStorage.setItem('jwtrefresh', response.data.refreshToken);
//     //                 dispatch(updateName(response.data.userName));
//     //                 dispatch(updateMail(response.data.email));
//     //                 dispatch(updateLogin(true));
//     //                 setAttemptedLogin(false);
//     //             })
//     //             .catch((error) => {
//     //                 console.error('Error:', error);
//     //             });
//     //     } catch (error) {
//     //         console.error('구글 접속 실패', error);
//     //     }
//     // };

//     // const handleGoogleLogin = () => {
//     //     try {
//     //         setAttemptedLogin(true);
//     //         window.location.href = 'http://ec2-13-209-157-148.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google';
//     //     } catch (error) {
//     //         console.error('구글 접속 실패', error);
//     //     }
//     // };

//     // function handleGoogleLogin() {
//     //     apiCall({
//     //                     method: 'GET',
//     //                     url: 'http://ec2-13-209-157-148.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google', // 백엔드 엔드포인트
//     //                     // data: { code },
//     //                 }, false)
//     //                     .then((response) => {
//     //                         console.log("오오쓰 리스폰스", response)
//     //                         // localStorage.setItem('jwt', response.data.accessToken);
//     //                         // localStorage.setItem('jwtrefresh', response.data.refreshToken);
//     //                         // dispatch(updateName(response.data.userName));
//     //                         // dispatch(updateMail(response.data.email));
//     //                         // dispatch(updateLogin(true));
//     //                         // setAttemptedLogin(false);
//     //                     })
//     //                     .catch((error) => {
//     //                         console.error('Error:', error);
//     //                     });
//     // }

//     // 로그인 버튼을 클릭하면 OAuth2 로그인 요청을 보냅니다.

//     // useEffect(() => {
//     //     if (!attemptedLogin) return;

//     //     const params = new URL(document.location.toString()).searchParams;
//     //     const code = params.get('code');
//     //     console.log("코드",code)

//     //     if (code) {
//     //         // 전달받은 `code`를 백엔드로 POST 요청
//     //         apiCall({
//     //             method: 'GET',
//     //             url: 'oauth2/authorization/google', // 백엔드 엔드포인트
//     //             data: { code },
//     //         })
//     //             .then((response) => {
//     //                 console.log("오오쓰 리스폰스", response)
//     //                 localStorage.setItem('jwt', response.data.accessToken);
//     //                 localStorage.setItem('jwtrefresh', response.data.refreshToken);
//     //                 dispatch(updateName(response.data.userName));
//     //                 dispatch(updateMail(response.data.email));
//     //                 dispatch(updateLogin(true));
//     //                 setAttemptedLogin(false);
//     //             })
//     //             .catch((error) => {
//     //                 console.error('Error:', error);
//     //             });
//     //     }
//     // }, [attemptedLogin]);

//     // http://localhost:8080/oauth2/authorization/google

//     // useEffect(() => {
//     //     const params = new URL(document.location.toString()).searchParams;
//     //     const code = params.get('code');

//     //     if (code) {
//     //         const GOOGLE_SECRET = process.env.REACT_APP_GOOGLE_CLIENT_SECRET;
//     //         const grantType = 'authorization_code';

//     //         apiCall(
//     //             {
//     //                 method: 'POST',
//     //                 url: `https://oauth2.googleapis.com/token?code=${code}&client_id=${clientId}&redirect_uri=${redirectUri}&grant_type=${grantType}&client_secret=${GOOGLE_SECRET}`,
//     //                 headers: { 'Content-type': 'application/x-www-form-urlencoded;charset=utf-8' },
//     //             },
//     //             false,
//     //         )
//     //             .then((res) => {
//     //                 console.log(res.data.access_token);
//     //                 // localStorage.setItem('jwt', res.data.access_token);
//     //                 return apiCall(
//     //                     {
//     //                         method: 'GET',
//     //                         url: `https://www.googleapis.com/oauth2/v2/userinfo?access_token=${res.data.access_token}`,
//     //                         headers: {
//     //                             Authorization: `Bearer ${res.data.access_token}`,
//     //                             'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
//     //                         },
//     //                     },
//     //                     false,
//     //                 );
//     //             })
//     //             .then((res) => {
//     //                 console.log(res);
//     //                 console.log(res.data.name);
//     //                 console.log(res.data.email);
//     //                 dispatch(updateName(res.data.name));
//     //                 dispatch(updateMail(res.data.email));
//     //                 dispatch(updateLogin(true));
//     //             })
//     //             .catch((error) => {
//     //                 console.error('Error:', error);
//     //             });
//     //     }
//     // }, [dispatch]);

//     return (
//         <>
//             <OauthGooglewrap>
//                 {/* <a href='http://13.209.157.148:8080/oauth2/authorization/google'> */}
//                 <OauthGoogleimg src="/google.png" alt="google oauth logo" onClick={handleGoogleLogin}></OauthGoogleimg>
//                 {/* </a> */}
//             </OauthGooglewrap>
//         </>
//     );
//     }


// export default OauthGoogle;

import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';

const OauthGoogleimg = styled.img`
    width: 32px;
    height: 32px;
    cursor: pointer;

    &:hover {
        opacity: 0.6;
    }
`;

const OauthGoogle = () => {
    const clientId = `${process.env.REACT_APP_GOOGLE_CLIENT_ID}`;

    return (
        <>
            <GoogleOAuthProvider clientId={clientId}>
                <GoogleLogin
                    onSuccess={(credentialResponse) => {
                        console.log(credentialResponse);
                    }}
                    onError={() => {
                        console.log('Login Failed');
                    }}
                />
            </GoogleOAuthProvider>
        </>
    );
};

export default OauthGoogle;
