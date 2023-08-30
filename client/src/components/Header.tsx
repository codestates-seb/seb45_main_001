import { styled, css } from 'styled-components';
import { useState } from 'react';
import LoginPage from '../pages/LoginPage';

const HeaderStyle = styled.header`
    width: 100%;
    height: 56px;
    display: flex;
    align-items: center;
    z-index: 1001;
    background-color: #1d1d1d;
    /* background-color: transparent; */
    /* 백그라운드는 나중에 투명으로 바꿀 것 */
    /* position: fixed; */
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

    function handleMagnifierClick() {
        setisMagnifierClicked(!isMagnifierClicked);
        if (!isMagnifierClicked) {
            console.log("검색바 열림!")
        } else {
            console.log("검색바 닫힘!")
        }
    }

    const [isLoginModal, setLoginModal] = useState<boolean>(false);

    function onClickToggleModal() {
        setLoginModal(!isLoginModal);
        if (!isLoginModal) {
            console.log("모달 열림!")
        } else {
            console.log("모달 닫힘!")
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
                        <LoginbuttonStyle onClick={onClickToggleModal}>로그인</LoginbuttonStyle>
                        <SignupbuttonStyle>회원가입</SignupbuttonStyle>
                    </LogSignStyle>
                </Headerwrap>
            </HeaderStyle>
            {isLoginModal && (
                <LoginPage onClickToggleModal={onClickToggleModal} >
                </LoginPage>
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

// const GeneralStyle = styled(FlexCenter)`
//     color: white;
// `;

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
