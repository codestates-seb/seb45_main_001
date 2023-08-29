import { styled, css } from 'styled-components';
import { useState } from 'react';

const HeaderStyle = styled.header`
    width: 100%;
    height: 56px;
    display: flex;
    align-items: center;
    z-index: 9999;
    background-color: #1d1d1d;
    /* 백그라운드는 나중에 투명으로 바꿀 것 */
    /* position: fixed; */
`;

const Headerwrap = styled.div`
    width: 90%;
    max-width: 100%;
    height: 100%;
    display: flex;
    margin: 0 auto;
    align-items: center;
`;

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
    padding-left: 12px;
    padding-right: 6px;
    color: #d6a701;
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

// const SearchStyle = styled(FlexCenter)``;

const SearchinputStyle = styled.input`
    border: 1px solid gray;
    background-color: white;
    width: 100%;
    max-width: 400px;
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

function Header() {
    const [magnifierClick, setMagnifierClick] = useState(false);

    function handleMagnifierClick() {
        setMagnifierClick(!magnifierClick);
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
                    <SearchbarStyle $isOpen={magnifierClick}>
                        <Relative>
                            <SearchinputStyle aria-label="" placeholder="검색..."></SearchinputStyle>
                        </Relative>
                    </SearchbarStyle>
                    <LogSignStyle>
                        <GeneralStyle>로그인</GeneralStyle>
                        <GeneralStyle>회원가입</GeneralStyle>
                    </LogSignStyle>
                </Headerwrap>
            </HeaderStyle>
        </>
    );
}
export default Header;
