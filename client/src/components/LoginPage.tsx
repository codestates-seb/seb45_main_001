import { styled, css } from 'styled-components';
import { useState } from 'react';
import { apiCall } from '../api/authapi';
import { useSelector, useDispatch } from 'react-redux';
import { DataState, updateName, updateMail, updateLogin } from '../slice/authslice';
import bcrypt from 'bcryptjs';

interface LoginPageProps {
    onClickToggleModal?: () => void;
    onClickToggleSignupModal?: () => void;
    children?: React.ReactNode;
}

function LoginPage({ onClickToggleModal, onClickToggleSignupModal }: LoginPageProps) {
    const dispatch = useDispatch();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [passwordError, setPasswordError] = useState<string>('');
    const [emailError, setEmailError] = useState<string>('');

    function isEmailValid(email: string): boolean {
        const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return regex.test(email);
    }

    function isPasswordValid(pw: string): boolean {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
        return regex.test(pw);
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;

        if (name === 'email') {
            setEmail(value);
            if (value === '') {
                setEmailError('');
            } else if (!isEmailValid(value)) {
                setEmailError('유효한 이메일 주소를 입력해주세요.');
            } else {
                setEmailError('');
            }
        } else if (name === 'password') {
            setPassword(value);
            if (value === '') {
                setPasswordError('');
            } else if (!isPasswordValid(value)) {
                setPasswordError('8~16글자의 알파벳,숫자,특수문자를 최소1개이상 포함한 비밀번호여야 합니다.');
            } else {
                setPasswordError('');
            }
        }

        // console.log('타자', e.target.value);
    };

    const handlelogintosignup = () => {
        try {
            onClickToggleModal?.();
            onClickToggleSignupModal?.();
        } catch (error) {
            console.log('썸띵 롱', error);
        }
    };

    const handleSubmit = () => {
        const myId = {
            email,
            password,
        };

        console.log('로그인 data 슛', myId);

        // try {
        //     const storedUsersJSON = sessionStorage.getItem('users');
        //     const storedUsers = storedUsersJSON ? JSON.parse(storedUsersJSON) : [];

        //     const existingUser = storedUsers.find((user: any) => user.email === myId.email);

        //     if (!existingUser) {
        //         console.error('로그인 실패: 이메일이 존재하지 않음');
        //         alert('로그인 할 수 없습니다. 이메일을 확인해주세요.');
        //         return;
        //     }

        //     const passwordWithPepper = myId.password + 'pepper';
        //     if (bcrypt.compareSync(passwordWithPepper, existingUser.hashedPassword)) {
        //         // 로그인 성공
        //         existingUser.islogin = true;
        //         sessionStorage.setItem('users', JSON.stringify(storedUsers));
        //         dispatch(updateLogin(true));
        //         dispatch(updateName(existingUser.nickname));
        //         dispatch(updateMail(existingUser.email));
        //         onClickToggleModal?.();
        //         console.log('로그인 성공', myId.email);
        //     } else {
        //         console.error('로그인 실패: 비밀번호 불일치');
        //         alert('로그인 할 수 없습니다. 비밀번호를 확인해주세요.');
        //     }
        // } catch (error) {
        //     console.error('로그인 실패', error);
        // }

        apiCall({
            method: 'POST',
            url: 'auth/login', // login - json 엔드포인트
            data: myId,
        })
            .then((response) => {
                dispatch(updateLogin(true));
                sessionStorage.setItem('jwt', response.data.accessToken);
                localStorage.setItem('jwtrefresh', response.data.refreshToken);
                return apiCall({
                    method: 'GET',
                    url: 'users/get', // user info endpoint
                });
            })
            .then((res) => {
                console.log("res data",res.data)
                console.log("User Name:", res.data.userName);
                console.log("typeof data",typeof res.data, res.data)
                console.log("object data",Object.keys(res.data))
                sessionStorage.setItem('userName', res.data.data.userName);
                sessionStorage.setItem('email', res.data.data.email);
                sessionStorage.setItem('memberId', res.data.data.memberId);

                dispatch(updateName(res.data.data.userName));
                dispatch(updateMail(res.data.data.email));

                onClickToggleModal?.();

                console.log('로그인 및 유저 정보 가져오기 성공');
            })
            .catch((error) => {
                if (error.response && error.response.status === 400) {
                    console.error('로그인 실패 비밀번호 혹은 아이디');
                    alert('로그인 할 수 없습니다. 비밀번호나 이메일을 확인해주세요.');
                } else if (error.message && error.message.includes('Network Error')) {
                    console.error('서버 안열림');
                } else {
                    console.error('로그인 에러', error);
                }
            });
    };

    return (
        <>
            <LoginModaltop>
                <LoginModalbackground onClick={onClickToggleModal}></LoginModalbackground>
                <LoginModalmain>
                    <LoginModalmain_low>
                        <LoginModallogo>일요시네마</LoginModallogo>
                        <LoginModallogin>로그인</LoginModallogin>
                        <LoginModalinput
                            placeholder="이메일"
                            name="email"
                            value={email}
                            onChange={handleInputChange}
                        ></LoginModalinput>
                        <LoginModalinput
                            placeholder="비밀번호"
                            name="password"
                            value={password}
                            onChange={handleInputChange}
                        ></LoginModalinput>
                        <LoginModalerrorwrap>
                            {emailError && <LoginModalerror>{emailError}</LoginModalerror>}
                            {passwordError && <LoginModalerror>{passwordError}</LoginModalerror>}
                        </LoginModalerrorwrap>
                        <LoginModalbuttonin
                            disabled={!isEmailValid(email) || !isPasswordValid(password)}
                            $isValid={isEmailValid(email) && isPasswordValid(password)}
                            onClick={handleSubmit}
                        >
                            로그인
                        </LoginModalbuttonin>
                        <LoginModalbuttonup onClick={handlelogintosignup}>회원가입</LoginModalbuttonup>
                        <LoginModalorwrap>
                            <LoginModalor>OR</LoginModalor>
                            <LoginModalline></LoginModalline>
                        </LoginModalorwrap>
                        <LoginModaloauthwrap>
                            <LoginModaloauth>Oauth</LoginModaloauth>
                        </LoginModaloauthwrap>
                    </LoginModalmain_low>
                </LoginModalmain>
            </LoginModaltop>
        </>
    );
}

const LoginModaltop = styled.div`
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0%;
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
`;

const LoginModalbackground = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 5;
    background-color: rgba(77, 77, 77, 0.5);
    /* 마지막이 투명도 조절 */
`;

const LoginModalmain = styled.div`
    position: relative;
    width: 370px;
    height: 570px;
    z-index: 10;
    /* border: 1px solid black; */
    padding-bottom: 12px;
    padding-top: 36px;
    border-radius: 10px;
    background-color: white;
`;

const LoginModalmain_low = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
`;

const LoginModallogo = styled.div`
    font-size: 24px;
    font-weight: 700;
    color: #d6a701;
`;
const LoginModallogin = styled.div`
    font-size: 18px;
    font-weight: 500;
    color: black;
    margin-top: 36px;
    margin-bottom: 36px;
`;

const LoginModalwidth = css`
    width: 320px;
`;

const LoginModalinput = styled.input`
    font-size: 16px;
    font-weight: 500;
    color: #505050;
    margin-top: 6px;
    margin-bottom: 6px;
    border: 0px solid black;
    background-color: #d9d9d973;
    height: 42px;
    padding: 6px;
    padding-left: 12px;
    ${LoginModalwidth}
`;

const LoginModalerrorwrap = styled.div`
    position: relative;
    height: 12px;
    justify-content: center;
    align-items: center;
`;

const LoginModalerror = styled.div`
    position: relative;
    font-size: 8px;
    color: #797979;
    text-align: center;
`;

const LoginModalbuttonin = styled.button<{ $isValid: boolean }>`
    font-size: 14px;
    font-weight: 500;
    color: ${({ $isValid }) => ($isValid ? '#000000' : '#ffffff')};
    margin-top: 36px;
    margin-bottom: 4px;
    border: 0px solid black;
    border-radius: 20px;
    background-color: ${({ $isValid }) => ($isValid ? '#ffe800' : '#838383')};
    ${LoginModalwidth}
    height: 42px;
    cursor: pointer;

    ${({ $isValid }) =>
        $isValid &&
        css`
            &:hover {
                background-color: #fff9b4;
            }
        `}
`;

const LoginModalbuttonup = styled.button`
    font-size: 14px;
    font-weight: 500;
    color: white;
    margin-top: 4px;
    margin-bottom: 48px;
    border: 0px solid black;
    border-radius: 20px;
    background-color: #04d218;
    ${LoginModalwidth}
    height: 42px;
    cursor: pointer;

    &:hover {
        background-color: #309e3b;
    }
`;

const LoginModalorwrap = styled.div`
    position: relative;
    margin-top: 0px;
    margin-bottom: 0px;
    /* ${LoginModalwidth} */
    width: 100%;
`;

const LoginModalor = styled.div`
    position: relative;
    font-size: 15px;
    font-weight: 500;
    font-style: italic;
    text-align: center;
    margin: 0 auto;
    height: 22px;
    width: 68px;
    background-color: white;
    color: black;
    z-index: 3;
`;

const LoginModalline = styled.div`
    position: absolute;
    top: 50%;
    width: 100%;
    border: 1px solid #797979;
    z-index: 2;
`;

const LoginModaloauthwrap = styled.div`
    margin-top: 28px;
`;

const LoginModaloauth = styled.div``;

export default LoginPage;
