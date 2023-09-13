import { styled, css } from 'styled-components';

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

const My_lowertap_nswrap = styled.div`
    ${FlexCentercss}
    flex-direction: row;
`;

const My_lowertap_name = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 18px;
    margin-left: 52px;
    font-weight: 500;
`;

const My_lowertap_nameblank = styled.div`
    font-size: 14px;
    margin-right: 52px;
`;

const My_lowertap_star = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 18px;
    margin-left: 52px;
    font-weight: 500;
`;

const My_lowertap_blank2 = styled.div`
    height: 30px;
`;

const My_lowertap_commentwrap = styled.ul`
    display: flex;
    flex-direction: column;
`;

const My_lowertap_commentwrapli = styled.li`
    ${FlexCentercss}
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 28px;
`;

const My_lowertap_nswrap2 = styled.div`
    ${FlexCentercss}
    flex-direction: row;
`;

const My_lowertap_name2 = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 12px;
    margin-left: 52px;
    font-weight: 500;
`;

const My_lowertap_nameblank2 = styled.div`
    margin-right: 52px;
`;

const My_lowertap_star2 = styled.div`
    width: 100px;
    margin-left: 52px;
    font-size: 12px;
    margin-left: 52px;
    font-weight: 500;
`;

function Mycommenttap() {
    return (
        <>
            <My_lowertap>
                <My_lowertap_blank></My_lowertap_blank>
                <My_lowertap_namewrap>
                    <My_lowertap_nswrap>
                        <My_lowertap_name>영화</My_lowertap_name>
                        <My_lowertap_star>평점</My_lowertap_star>
                    </My_lowertap_nswrap>
                    <My_lowertap_name>댓글</My_lowertap_name>
                    <My_lowertap_nameblank></My_lowertap_nameblank>
                </My_lowertap_namewrap>
                <My_lowertap_blank2></My_lowertap_blank2>
                <My_lowertap_commentwrap>
                    <My_lowertap_commentwrapli>
                        <My_lowertap_nswrap2>
                            <My_lowertap_name2>더미</My_lowertap_name2>
                            <My_lowertap_star2>더미</My_lowertap_star2>
                        </My_lowertap_nswrap2>
                        <My_lowertap_name2>댓글 더미들</My_lowertap_name2>
                        <My_lowertap_nameblank2></My_lowertap_nameblank2>
                    </My_lowertap_commentwrapli>
                    <My_lowertap_commentwrapli>
                        <My_lowertap_nswrap2>
                            <My_lowertap_name2>더미</My_lowertap_name2>
                            <My_lowertap_star2>더미</My_lowertap_star2>
                        </My_lowertap_nswrap2>
                        <My_lowertap_name2>댓글 더미들</My_lowertap_name2>
                        <My_lowertap_nameblank2></My_lowertap_nameblank2>
                    </My_lowertap_commentwrapli>
                    <My_lowertap_commentwrapli>
                        <My_lowertap_nswrap2>
                            <My_lowertap_name2>더미</My_lowertap_name2>
                            <My_lowertap_star2>더미</My_lowertap_star2>
                        </My_lowertap_nswrap2>
                        <My_lowertap_name2>댓글 더미들</My_lowertap_name2>
                        <My_lowertap_nameblank2></My_lowertap_nameblank2>
                    </My_lowertap_commentwrapli>
                </My_lowertap_commentwrap>
            </My_lowertap>
        </>
    );
}

export default Mycommenttap;
