import Header from '../components/Header';
import { useState, useEffect } from 'react';
import { styled, css } from 'styled-components';
import Mymytap from '../components/Mytap';
import Mycommenttap from '../components/Mycommenttap';
import Mybookmarktap from '../components/Mybookmarktap';
import { useSelector } from 'react-redux';
import { DataState } from '../slice/authslice';

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    justify-content: center;
    display: flex;
`;

const Bodymypage = styled.div`
    width: 100%;
    height: 98vh;
    background-color: #1d1d1d;
    ${FlexCentercss}
`;

const Wrapmypage = styled.div`
    width: 1052px;
    height: 100%;
    padding-top: 56px;
    margin: 0 auto;

    /* @media (max-width: 대형) {
            width: 640px;
    }

    @media (max-width: 중형) {
            width: 320px;
    } */
`;

const Uppermypage = styled.div`
    position: relative;
    width: 100%;
    min-width: 400px;
    color: white;
    ${FlexCentercss}
    flex-direction: column;
`;

const Upper_upper = styled.div`
    width: 100%;
    height: 56px;
    margin: 30px 0px;
`;

const Upper_lower = styled.div`
    width: 100%;
    height: 48px;
    border-bottom: 1px solid white;
    margin: 10px 0px;
    padding: 52px 0px;
    ${FlexCentercss}
    flex-direction: row;
    justify-content: space-between;
    font-weight: 600;
`;

const Ul_text = styled.div`
    font-size: 24px;
    width: 100px;
    margin-left: 52px;

    /* @media (max-width: 중형) {
            font-size: 16px;
    } */
`;

const Tap = styled.div`
    font-size: 16px;
    ${FlexCentercss}
    flex-direction: row;
    margin-right: 52px;

    /* @media (max-width: 중형) {
            font-size: 10px;
    } */
`;

const Mytap = styled.div<{ $isActive: boolean }>`
    margin-left: 10px;
    margin-right: 10px;
    cursor: pointer;
    border-bottom: ${({ $isActive }) => ($isActive ? '1px solid white' : 'none')};
`;

const Commenttap = styled.div<{ $isActive: boolean }>`
    margin-left: 10px;
    margin-right: 10px;
    cursor: pointer;
    border-bottom: ${({ $isActive }) => ($isActive ? '1px solid white' : 'none')};
`;

const Bookmarktap = styled.div<{ $isActive: boolean }>`
    margin-left: 10px;
    margin-right: 10px;
    cursor: pointer;
    border-bottom: ${({ $isActive }) => ($isActive ? '1px solid white' : 'none')};
`;

const Lowermypage = styled.div`
    position: relative;
    width: 100%;
    min-width: 400px;
    color: white;
    ${FlexCentercss}
    flex-direction: column;
`;

function Mypage() {
    const [activeTab, setActiveTab] = useState<string>('My');
    const globalName = useSelector((state: { data: DataState }) => state.data.name);

    const handleTabClick = (tabName: string) => {
        setActiveTab(tabName);
    };

    return (
        <>
            <Header />
            <Bodymypage>
                <Wrapmypage>
                    <Uppermypage>
                        <Upper_upper></Upper_upper>
                        <Upper_lower>
                            <Ul_text>{globalName} 님</Ul_text>
                            <Tap>
                                <Mytap $isActive={activeTab === 'My'} onClick={() => handleTabClick('My')}>My</Mytap>
                                <Commenttap $isActive={activeTab === 'Comment'} onClick={() => handleTabClick('Comment')}>작성 댓글</Commenttap>
                                <Bookmarktap $isActive={activeTab === 'Bookmark'} onClick={() => handleTabClick('Bookmark')}>북마크</Bookmarktap>
                            </Tap>
                        </Upper_lower>
                    </Uppermypage>
                    <Lowermypage>
                        {activeTab === 'My' && <Mymytap />}
                        {activeTab === 'Comment' && <Mycommenttap />}
                        {activeTab === 'Bookmark' && <Mybookmarktap />}
                    </Lowermypage>
                </Wrapmypage>
            </Bodymypage>
        </>
    );
}

export default Mypage;
