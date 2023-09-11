import { styled, css } from 'styled-components';
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateName, DataState } from '../../slice/authslice';

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
    flex-direction: row;
    gap: 12px;
`;

const My_lowertap_realmail = styled.div`
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

function Mymytap() {
    const [isEmailEditing, setIsEmailEditing] = useState<boolean>(false);
    const [email, setEmail] = useState('colruck32@gmail.com');

    const dispatch = useDispatch();
    const globalName = useSelector((state: { data: DataState }) => state.data.globalname);
    const [isNameEditing, setIsNameEditing] = useState<boolean>(false);

    const [tempName, setTempName] = useState(globalName);

    const handleNameUpdate = () => {
        dispatch(updateName(tempName));
        setIsNameEditing(false);
    };

    useEffect(() => {
        setTempName(globalName);
    }, [globalName]);

    return (
        <>
            <My_lowertap>
                <My_lowertap_blank></My_lowertap_blank>
                <My_lowertap_namewrap>
                    <My_lowertap_name>이름</My_lowertap_name>
                    <My_lowertap_realwrap>
                        {isNameEditing ? (
                            <>
                                <input
                                    type="text"
                                    value={tempName}
                                    onChange={(e) => setTempName(e.target.value)}
                                />
                                <Pencil src="/pencil.png" alt="" onClick={handleNameUpdate} />
                            </>
                        ) : (
                            <>
                                <My_lowertap_realname>{globalName}</My_lowertap_realname>
                                <Pencil src="/pencil.png" alt="" onClick={() => setIsNameEditing(true)} />
                            </>
                        )}
                    </My_lowertap_realwrap>
                    <My_lowertap_nameblank></My_lowertap_nameblank>
                </My_lowertap_namewrap>
                <My_lowertap_mailwrap>
                    <My_lowertap_mail>이메일</My_lowertap_mail>
                    <My_lowertap_realwrap>
                        {isEmailEditing ? (
                            <input
                                type="text"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                onBlur={() => setIsEmailEditing(false)}
                            />
                        ) : (
                            <>
                                <My_lowertap_realmail>{email}</My_lowertap_realmail>
                                <Pencil src="/pencil.png" alt="" onClick={() => setIsEmailEditing(true)} />
                            </>
                        )}
                    </My_lowertap_realwrap>
                    <My_lowertap_mailblank></My_lowertap_mailblank>
                </My_lowertap_mailwrap>
            </My_lowertap>
        </>
    );
}

export default Mymytap;
