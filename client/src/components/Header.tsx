import { styled, css } from 'styled-components';
import { useState } from 'react';
import LoginPage from './LoginPage';
import SignupPage from './Signup';

const HeaderStyle = styled.header`
    width: 100%;
    height: 56px;
    display: flex;
    align-items: center;
    z-index: 1001;
    background-color: #1d1d1d;
    /* background-color: transparent; */
    /* 백그라운드는 나중에 투명으로 바꿀 것 */
    position: fixed;
`;

const Headerwrap = styled.div`
    width: 53%;
    max-width: 100%;
    height: 100%;
    display: flex;
    margin: 0 auto;
    align-items: center;
`;

function Header() {
    const [isMagnifierClicked, setisMagnifierClicked] = useState<boolean>(false);

    const isTokenExpired = (token: string): boolean => {
        try {
            const decodedToken: any = JSON.parse(atob(token.split('.')[1]));
            const currentTime = Date.now() / 1000;
            return decodedToken.exp && currentTime > decodedToken.exp;
        } catch (error) {
            return true;
        }
    };

    const token = localStorage.getItem('jwt');
    const initialLoginState = token ? !isTokenExpired(token) : false;
    const [isLogin, setIsLogin] = useState<boolean>(initialLoginState);

    // const [서버세션확인데이터위치, set서버세션확인데이터위치] = useState(false);
    // const [유저, set유저] = useState(null);

    // useEffect(() => {
    //     axios
    //         .get('세션로그인유지엔드포인트') 
    //         .then((response) => {
    //             if (response.data.서버세션확인데이터위치) {
    //                 setIsAuthenticated(true);
    //                 setUser(response.data.유저);
    //             }
    //         })
    //         .catch((error) => {
    //             console.error('로그인 상태 확인 중 오류 발생:', error);
    //         });
    // }, []); 세션용 로그인 유지

    function handleMagnifierClick() {
        setisMagnifierClicked(!isMagnifierClicked);
        if (!isMagnifierClicked) {
            console.log('검색바 열림!');
        } else {
            console.log('검색바 닫힘!');
        }
    }

    const [isLoginModal, setLoginModal] = useState<boolean>(false);
    const [isSignupModal, setSignupModal] = useState<boolean>(false);

    function onClickToggleModal() {
        setLoginModal(!isLoginModal);
        if (!isLoginModal) {
            console.log('로그인 모달 열림!');
        } else {
            console.log('로그인 모달 닫힘!');
        }
    }

    function onClickToggleSignupModal() {
        setSignupModal(!isSignupModal);
        if (!isSignupModal) {
            console.log('회원가입 모달 열림!');
        } else {
            console.log('회원가입 모달 닫힘!');
        }
    }

    function onClickLogout() {
        setIsLogin(false);
        try {
            localStorage.removeItem('memberid');
            localStorage.removeItem('jwt');
            console.log('로그아웃 성공');
        } catch (error) {
            console.log('로그아웃 실패', error);
        }
    }

    return (
        <>
            <HeaderStyle>
                <Headerwrap>
                    <LogoStyle>
                        <div>일요시네마</div>
                    </LogoStyle>
                    <CountryStyle>
                        <DomesticStyle>국내</DomesticStyle>
                        <OverseasStyle>해외</OverseasStyle>
                    </CountryStyle>
                    <MagnifierStyle onClick={handleMagnifierClick}>
                        <img src="/Magnifier_white.png" alt="" style={{ width: '100%', height: '100%' }} />
                    </MagnifierStyle>
                    <SearchbarStyle $isOpen={isMagnifierClicked}>
                        <Relative>
                            <SearchinputStyle aria-label="" placeholder="검색..."></SearchinputStyle>
                        </Relative>
                    </SearchbarStyle>
                    <LogSignStyle>
                        {!isLogin && (
                            <>
                                <LoginbuttonStyle onClick={onClickToggleModal}>로그인</LoginbuttonStyle>
                                <SignupbuttonStyle onClick={onClickToggleSignupModal}>회원가입</SignupbuttonStyle>
                            </>
                        )}
                        {isLogin && (
                            <>
                                <GeneralStyle>${}</GeneralStyle>
                                <LoginbuttonStyle onClick={onClickLogout}>로그아웃</LoginbuttonStyle>
                            </>
                        )}
                    </LogSignStyle>
                </Headerwrap>
            </HeaderStyle>
            {isLoginModal && (
                <LoginPage
                    isLogin={isLogin}
                    setIsLogin={setIsLogin}
                    onClickToggleModal={onClickToggleModal}
                    onClickToggleSignupModal={onClickToggleSignupModal}
                ></LoginPage>
            )}
            {isSignupModal && (
                <SignupPage
                    isLogin={isLogin}
                    setIsLogin={setIsLogin}
                    onClickToggleModal={onClickToggleModal}
                    onClickToggleSignupModal={onClickToggleSignupModal}
                ></SignupPage>
            )}
        </>
    );
}

const FlexCenter = styled.div`
    align-items: center;
    text-align: center;
    display: flex;
`;

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    display: flex;
`;

const LogoStyle = styled(FlexCenter)`
    height: 100%;
    padding-left: 0px;
    padding-right: 6px;
    color: #d6a701;
    font-weight: 600;
`;

const CountryStyle = styled(FlexCenter)`
    margin-left: 10px;
    margin-right: 20px;
    gap: 6px;
`;

const DomesticStyle = styled(FlexCenter)`
    color: white;
`;

const OverseasStyle = styled(FlexCenter)`
    color: white;
`;

const GeneralStyle = styled(FlexCenter)`
    color: white;
`;

const LoginbuttonStyle = styled.div`
    color: white;
    cursor: pointer;
`;

const SignupbuttonStyle = styled.div`
    color: white;
    cursor: pointer;
`;

// const SearchStyle = styled(FlexCenter)``;

const SearchinputStyle = styled.input`
    border: 1px solid gray;
    background-color: white;
    width: 100%;
    max-width: 600px;
    border-radius: 3px;
    padding: 3px;
    padding-left: 6px;
`;

const MagnifierStyle = styled.div`
    width: 21px;
    height: 21px;
    border: 1px solid gray;
    cursor: pointer;
`;

const SearchbarStyle = styled.form<{ $isOpen: boolean }>`
    ${FlexCentercss}
    flex-grow: 1;
    margin-left: 10px;
    display: ${({ $isOpen }) => ($isOpen ? 'flex' : 'none')};
`;

const LogSignStyle = styled.nav`
    ${FlexCentercss}
    margin-left: auto;
    gap: 6px;
`;

const Relative = styled.div`
    ${FlexCentercss}
    position: relative;
    flex-grow: 1;
`;

export default Header;
