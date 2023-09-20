import { styled, css } from 'styled-components';
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateName, updateMail, DataState, updateLogin } from '../../slice/authslice';
import { apiCall } from '../../api/authapi';
import { useNavigate } from 'react-router-dom';

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    justify-content: center;
    display: flex;
`;

const My_lowertap = styled.div`
    width: 100%;
    color: white;
`;

const My_lowertap_blank = styled.div`
    height: 100px;
`;

const My_lowertap_namewrap = styled.div`
    ${FlexCentercss}
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 50px;
`;
const My_lowertap_name = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 18px;
    margin-left: 52px;
    font-weight: 500;
`;

const My_lowertap_realname = styled.div`
    font-size: 14px;
`;

const My_lowertap_nameblank = styled.div`
    font-size: 14px;
    margin-right: 52px;
`;

const My_lowertap_mailwrap = styled.div`
    ${FlexCentercss}
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 50px;
`;
const My_lowertap_mail = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 18px;
    margin-left: 52px;
    font-weight: 500;
`;

const My_lowertap_realwrap = styled.div`
    ${FlexCentercss}
    position: relative;
    flex-direction: row;
    gap: 12px;
    min-width: 150px;
    text-align: left;
    justify-content: left;
`;

const My_lowertap_realnamewrap = styled.div`
    font-size: 14px;
`;

const My_lowertap_mailblank = styled.div`
    font-size: 14px;
    margin-right: 52px;
`;

const Pencil = styled.img`
    width: 14px;
    height: 14px;
    margin-top: 3px;
    cursor: pointer;
`;

const Xbutton = styled.img`
    width: 20px;
    height: 20px;
    margin-top: 3px;
    cursor: pointer;
`;

const Exit = styled.img`
    width: 20px;
    height: 20px;
    margin-top: 3px;
    cursor: pointer;
`;

const Vaildwrap = styled.div`
    position: absolute;
    display: flex;
    width: 100%;
    justify-items: center;
    justify-content: center;
`;

const Vaild = styled.div`
    position: absolute;
    top: 25px;
    left: auto;
    right: auto;
    font-size: 0.7rem;
    width: max-content;
`;

const Patch = styled.input`
    background-color: transparent;
    color: white;
    text-align: center;
    border: 0px;
    border-bottom: 1px solid gray;
`;

function Mymytap() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const globalName = useSelector((state: { data: DataState }) => state.data.globalname);
    const globalMail = useSelector((state: { data: DataState }) => state.data.globalmail);

    function isPasswordValid(pw: string): boolean {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
        return regex.test(pw);
    }

    function isNameValid(name: string): boolean {
        const regex = /^[가-힣a-zA-Z0-9]{2,8}$/;
        return regex.test(name);
    }

    const [isPasswordEditing, setIsPasswordEditing] = useState<boolean>(false);
    const [tempPassword, setTempPassword] = useState('');

    const [isNameEditing, setIsNameEditing] = useState<boolean>(false);
    const [tempName, setTempName] = useState(globalName);

    const [passwordError, setPasswordError] = useState<string>('');
    const [nameError, setNameError] = useState<string>('');

    const [isDeleteEditing, setIsDeleteEditing] = useState<boolean>(false);
    const [tempDeleteInput, setTempDeleteInput] = useState<string>('');

    // function getUserpassword() {
    //     return sessionStorage.getItem('password');
    // }

    const handlePasswordUpdate = () => {
        const sendPassword = {
            password: tempPassword,
        };
        apiCall({
            method: 'PATCH',
            url: 'users/patch', // users/patch
            data: sendPassword,
            headers: { Authorization: `${localStorage.getItem('jwt')}` },
        })
            .then(() => {
                console.log('보냈나?', sendPassword);
                setIsPasswordEditing(false);
            })
            .catch((error) => {
                console.error('err', error);
            });
    };

    // const handleNameUpdate = () => {
    //     dispatch(updateName(tempName));
    //     setIsNameEditing(false);
    // };

    const handleNameUpdate = () => {
        // const passworddd = getUserpassword();
        if (isNameValid(tempName)) {
            const sendName = {
                userName: tempName,
            };
            apiCall({
                method: 'PATCH',
                url: 'users/patch', // users/patch
                data: sendName,
                headers: { Authorization: `${localStorage.getItem('jwt')}` },
            })
                .then(() => {
                    console.log('보냈나?', sendName);
                    dispatch(updateName(tempName));
                    setIsNameEditing(false);
                })
                .catch((error) => {
                    console.error('err', error);
                });
        }
    };

    const handleDelete = () => {
        if (tempDeleteInput === '영구 삭제') {
            apiCall({
                method: 'DELETE',
                url: 'users/delete', // users/delete
                headers: { Authorization: `${localStorage.getItem('jwt')}` },
            })
                .then(() => {
                    console.log('했나?');
                    dispatch(updateLogin(false));
                    sessionStorage.removeItem('userName');
                    sessionStorage.removeItem('email');
                    sessionStorage.removeItem('memberId');
                    localStorage.removeItem('jwt');
                    localStorage.removeItem('jwtrefresh');
                    setIsDeleteEditing(false);
                    setTempDeleteInput('');
                    navigate('/');
                })
                .catch((error) => {
                    console.error('err', error);
                });
        } else {
            alert("입력한 내용이 '영구 삭제'와 일치하지 않습니다.");
        }
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;

        if (name === 'tempName') {
            setTempName(value);
            if (value === '') {
                setNameError('');
            } else if (!isNameValid(value)) {
                setNameError('2~8글자의 한글,영어,숫자이어야 합니다.');
            } else {
                setNameError('');
            }
        } else if (name === 'tempPassword') {
            setTempPassword(value);
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

    useEffect(() => {
        setTempName(globalName);
    }, [globalName]);

    return (
        <>
            <My_lowertap>
                <My_lowertap_blank></My_lowertap_blank>
                <My_lowertap_namewrap>
                    <My_lowertap_mail>이메일</My_lowertap_mail>
                    <My_lowertap_realwrap>
                        <My_lowertap_realname>{globalMail}</My_lowertap_realname>
                    </My_lowertap_realwrap>
                    <My_lowertap_mailblank></My_lowertap_mailblank>
                </My_lowertap_namewrap>
                <My_lowertap_namewrap>
                    <My_lowertap_name>이름</My_lowertap_name>
                    <My_lowertap_realwrap>
                        {isNameEditing ? (
                            <>
                                <Patch
                                    type="text"
                                    name="tempName"
                                    value={tempName}
                                    onChange={handleInputChange}
                                ></Patch>
                                <Pencil src="/pencil.png" alt="" onClick={handleNameUpdate} />
                                <Xbutton src="/x.png" alt="" onClick={() => setIsNameEditing(false)} />
                            </>
                        ) : (
                            <>
                                <My_lowertap_realname>{globalName}</My_lowertap_realname>
                                <Pencil src="/pencil.png" alt="" onClick={() => setIsNameEditing(true)} />
                            </>
                        )}
                        <Vaildwrap>{nameError && isNameEditing && <Vaild>{nameError}</Vaild>}</Vaildwrap>
                    </My_lowertap_realwrap>
                    <My_lowertap_nameblank></My_lowertap_nameblank>
                </My_lowertap_namewrap>
                <My_lowertap_namewrap>
                    <My_lowertap_mail>비밀번호</My_lowertap_mail>
                    <My_lowertap_realwrap>
                        {isPasswordEditing ? (
                            <>
                                <Patch
                                    type="password"
                                    name="tempPassword"
                                    value={tempPassword}
                                    onChange={handleInputChange}
                                />
                                <Pencil src="/pencil.png" alt="" onClick={handlePasswordUpdate} />
                                <Xbutton src="/x.png" alt="" onClick={() => setIsPasswordEditing(false)} />
                            </>
                        ) : (
                            <>
                                <My_lowertap_realname>********</My_lowertap_realname>
                                <Pencil src="/pencil.png" alt="" onClick={() => setIsPasswordEditing(true)} />
                            </>
                        )}
                        <Vaildwrap>{passwordError && isPasswordEditing && <Vaild>{passwordError}</Vaild>}</Vaildwrap>
                    </My_lowertap_realwrap>
                    <My_lowertap_mailblank></My_lowertap_mailblank>
                </My_lowertap_namewrap>
                <My_lowertap_namewrap>
                    <My_lowertap_mail>회원탈퇴</My_lowertap_mail>
                    <My_lowertap_realwrap>
                        {isDeleteEditing ? (
                            <>
                                <Patch
                                    type="text"
                                    name="tempDeleteInput"
                                    value={tempDeleteInput}
                                    placeholder="영구 삭제라고 입력하세요."
                                    onChange={(e) => setTempDeleteInput(e.target.value)}
                                />
                                <Pencil src="/pencil.png" alt="" onClick={handleDelete} />
                                <Xbutton src="/x.png" alt="" onClick={() => setIsDeleteEditing(false)} />
                            </>
                        ) : (
                            <>
                                <Exit src="/exit.png" alt="" onClick={() => setIsDeleteEditing(true)} />
                            </>
                        )}
                    </My_lowertap_realwrap>
                    <My_lowertap_mailblank></My_lowertap_mailblank>
                </My_lowertap_namewrap>
            </My_lowertap>
        </>
    );
}

export default Mymytap;
