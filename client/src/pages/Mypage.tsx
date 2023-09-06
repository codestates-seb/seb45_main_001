import Header from '../components/Header';
import { styled, css } from 'styled-components';

const Bodymypage = styled.div`
    width: 100%;
    height: 100vh;
    background-color: #1d1d1d;
`;

const Uppermypage = styled.div`
    width: 100%;
    height: 100vh;
    padding-top: 56px;
`;

const Generalstyle = styled.div`
    color: white;
`;

function Mypage() {
    return (
        <>
            <Header />
            <Bodymypage>
                <Uppermypage>
                    <Generalstyle>마이페이지</Generalstyle>
                </Uppermypage>
            </Bodymypage>
        </>
    );
}

export default Mypage;
