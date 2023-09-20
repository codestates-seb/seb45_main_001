import { styled, css } from 'styled-components';

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    justify-content: center;
    display: flex;
`;

const My_lowertap = styled.div`
    ${FlexCentercss}
    width: 100%;
    color: white;
`;

const My_lowertap_blank = styled.div`
    height: 300px;
`;

function Mybookmarktap() {
    return (
        <>
            <My_lowertap>
                <My_lowertap_blank></My_lowertap_blank>
                <div>북마크</div>
            </My_lowertap>
        </>
    );
}

export default Mybookmarktap;
