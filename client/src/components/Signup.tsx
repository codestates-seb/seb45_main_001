import { styled, css } from 'styled-components';
import { useState } from 'react';
import { apiCall } from '../api/authapi';

interface LoginPageProps {
    onClickToggleModal?: () => void;
    onClickToggleSignupModal?: () => void;
    isLogin: boolean;
    setIsLogin: React.Dispatch<React.SetStateAction<boolean>>;
    children?: React.ReactNode;
}

function SignupPage({ onClickToggleModal, onClickToggleSignupModal, isLogin, setIsLogin }: LoginPageProps) {
    const [nickName, setNickName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [nameError, setNameError] = useState<string>('');
    const [passwordError, setPasswordError] = useState<string>('');
    const [emailError, setEmailError] = useState<string>('');

    function isNameValid(name: string): boolean {
        const regex = /^[가-힣]{2,8}$/;
        return regex.test(name);
    }

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
        } else if (name === 'nickName') {
            setNickName(value);
            if (value === '') {
                setNameError('');
            } else if (!isNameValid(value)) {
                setNameError('2~8글자의 한글이어야 합니다.');
            } else {
                setNameError('');
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
            nickName,
            email,
            password,
        };
        console.log('회원가입 data 슛', myId);
        apiCall({
            method: 'POST',
            url: 'host/signup',
            data: myId,
        })
            .then((response) => {
                onClickToggleSignupModal?.();
                handlelogintosignup?.();
                console.log('회원가입 성공', response);
            })
            .catch((error) => {
                console.error('회원가입 에러', error);
            });
    };

    return (
        <>
            <LoginModaltop>
                <LoginModalmain>
                    <LoginModalmain_low>
                        <LoginModallogo>일요시네마</LoginModallogo>
                        <LoginModallogin>회원가입</LoginModallogin>
                        <LoginModalinput
                            placeholder="이름"
                            name="nickName"
                            value={nickName}
                            onChange={handleInputChange}
                        ></LoginModalinput>
                        <LoginModalinput
                            placeholder="이메일"
                            name="email"
                            value={email}
                            onChange={handleInputChange}
                        ></LoginModalinput>
                        <LoginModalinput
                            // type="password"
                            placeholder="비밀번호"
                            name="password"
                            value={password}
                            onChange={handleInputChange}
                        ></LoginModalinput>
                        <LoginModalerrorwrap>
                            {nameError && <LoginModalerror>{nameError}</LoginModalerror>}
                            {emailError && <LoginModalerror>{emailError}</LoginModalerror>}
                            {passwordError && <LoginModalerror>{passwordError}</LoginModalerror>}
                        </LoginModalerrorwrap>
                        <LoginModalbuttonin
                            disabled={!isNameValid(nickName) || !isEmailValid(email) || !isPasswordValid(password)}
                            $isValid={isNameValid(nickName) && isEmailValid(email) && isPasswordValid(password)}
                            onClick={handleSubmit}
                        >
                            회원가입
                        </LoginModalbuttonin>
                        <LoginModalbuttonup onClick={handlelogintosignup}>로그인</LoginModalbuttonup>
                        <LoginModalorwrap>
                            <LoginModalor>OR</LoginModalor>
                            <LoginModalline></LoginModalline>
                        </LoginModalorwrap>
                        <LoginModaloauthwrap>
                            <LoginModaloauth>Oauth</LoginModaloauth>
                        </LoginModaloauthwrap>
                    </LoginModalmain_low>
                </LoginModalmain>
                <LoginModalbackground onClick={onClickToggleSignupModal}></LoginModalbackground>
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
    height: 600px;
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
    margin-top: 24px;
    margin-bottom: 24px;
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
    color: white;
    margin-top: 36px;
    margin-bottom: 4px;
    border: 0px solid black;
    border-radius: 20px;
    background-color: ${({ $isValid }) => ($isValid ? '#04d218' : '#838383')};
    ${LoginModalwidth}
    height: 42px;
    cursor: pointer;

    ${({ $isValid }) => $isValid && css`
        &:hover {
            background-color: #95ffa0;
        }
    `}
`;

const LoginModalbuttonup = styled.button`
    font-size: 14px;
    font-weight: 500;
    color: black;
    margin-top: 4px;
    margin-bottom: 48px;
    border: 0px solid black;
    border-radius: 20px;
    background-color: #ffe800;
    ${LoginModalwidth}
    height: 42px;
    cursor: pointer;

    &:hover {
        background-color: #cfc54e;
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

export default SignupPage;
