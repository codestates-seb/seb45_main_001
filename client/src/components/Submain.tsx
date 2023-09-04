import { styled } from 'styled-components';
import logo from '../assets/port.jpg';
import logo2 from '../assets/오펜포스터.jpg';
import Header from './Header';
import { useState } from 'react';
import Subinformation from './Subinformation';
import SubMovie from './SubMovie';
import SubComment from './SubComment';


const SubcontainerStyle = styled.div`
    width: 100%;
    display: flex;
    margin: 0 auto;
    flex-direction: column;
    background-color: #1d1d1d;
`;

const PosterStyle = styled.div`
    display: flex;
    height: 550px;
    background-image: url(${logo});
    background-size: cover;
    background-position: center 18%;
`;
const MainStyle = styled.div`
    display: flex;
    flex-direction: column;
    padding: 70px 30px 60px 0;
    align-items: center;
    justify-content: center;
    @media (max-width: 780px) {
        position: relative;
        left: 25%;
    }
`;
const Moivetextform = styled.div`
    display: flex;
    flex-direction: row;
    margin: 5px;
`;
const MoivePoster = styled.img`
    width: 210px;
    height: 308px;
`;
const Movidetail = styled.div`
    display: flex;
    flex-direction: column;
    padding: 8px 60px;
    @media (max-width: 780px) {
        padding: 8px 30px;
    }
`;

const TextForm = styled.div`
    display: flex;
    flex-direction: column;
`;
const Texth3 = styled.span`
    font-size: 30px;
    color: white;
    @media (max-width: 780px) {
        font-size: 20px;
    }
`;
const Buttondiv = styled.span`
    background-color: red;
    display: inline-block;
    height: 26px;
    padding: 0px 10px;
    margin: 5px 0 0 8px;
    position: relative;
    bottom: 8px;
    border: 1px solid #e92130;
    border-radius: 28px;
    font-weight: 28px;
    font-size: 13px;
    color: #fff;
    text-align: center;
`;

const Texth4 = styled.p`
    font-size: 24px;
    color: #dddada;
`;
const TextForm2 = styled.div`
    display: flex;
    flex-direction: row;
    padding: 20px 0 48px;
    align-items: flex-start;
`;
const Textdetail1 = styled.div`
    display: flex;
    flex-direction: column;
    margin: 15px 60px 0 0px;
`;
const Text5 = styled.p`
    font-size: 14px;
    width: 150px;
    color: white;
    margin-bottom: 10px;
`;
const Ulform = styled.div`
    display: flex;
    flex-direction: column;
`;

const Ultap = styled.ul`
    margin: 0 auto;
    list-style: none;
    margin-block-start: 1em;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
`;
const Litap = styled.li`
    list-style: none;
    margin: 0px 75px;
    font-size: 20px;
    width: 150px;
    height: 50px;
    text-align: center;
    border-radius: 30px;
    color: white;
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    cursor: pointer;
    transition: background-color 0.3s;

    &:hover {
        background-color: white;
        border: 1px solid white;
        border-radius: 30px;
        color: black;
        font-weight: 600;
    }

    &.active {
        background-color: white;
        border: 1px solid white;
        border-radius: 30px;
        color: black;
        font-weight: 600;
    }
    @media (max-width: 780px) {
        width: 100px;
        font-size: 15px;
    }
`;

const Line3tap = styled.div`
    border-bottom: 1px solid white;
    margin-top: 10px;
    width: 780px;
    position: relative;
    left: 60px;
`;

const Desc = styled.div`
  text-align: center;
`;

const Submain = () => {
    const [currentTab, clickTab] = useState(0);
    const menuArr = [
        { name: '주요 정보', content: <Subinformation /> },
        { name: '영상/포토', content: <SubMovie /> },
        { name: '댓글/평점', content: <SubComment /> },
    ];

    const selectMenuHandler = (index : number) => {
        // parameter로 현재 선택한 인덱스 값을 전달해야 하며, 이벤트 객체(event)는 쓰지 않는다
        // 해당 함수가 실행되면 현재 선택된 Tab Menu 가 갱신.
        clickTab(index);
    };

    return (
        <>
            <SubcontainerStyle>
                <PosterStyle>
                    <Header />
                </PosterStyle>
                <MainStyle>
                    <Moivetextform>
                        <MoivePoster src={logo2}></MoivePoster>
                        <Movidetail>
                            <TextForm>
                                <Texth3>
                                    오펜하이머 <Buttondiv>상영중</Buttondiv>
                                </Texth3>
                                <Texth4>Oppenheimer, 2023</Texth4>
                            </TextForm>
                            <TextForm2>
                                <Textdetail1>
                                    <Text5>개봉 : 2023.08.15</Text5>
                                    <Text5>장르 : 스릴러/드라마</Text5>
                                    <Text5>국가 : 미국,영국</Text5>
                                    <Text5>등급 : 15세이상관람가</Text5>
                                    <Text5>러닝타임 : 180분</Text5>
                                </Textdetail1>
                                <Textdetail1>
                                    <Text5>평점 : 4.8</Text5>
                                    <Text5>누적관객 : 2,485,183명</Text5>
                                    <Text5>박스오피스 : 1위</Text5>
                                </Textdetail1>
                            </TextForm2>
                        </Movidetail>
                    </Moivetextform>
                    <Ulform>
                        <Ultap>
                        {menuArr.map((el, index) => (
                             <Litap key={index} className={index === currentTab ? 'active' : ''}
                                 onClick={() => selectMenuHandler(index)}>{el.name}</Litap>
                                 ))}
                        </Ultap>
                        <Line3tap />
                            <Desc>
                            <div>{menuArr[currentTab].content}</div>
                            </Desc>
                    </Ulform>
                </MainStyle>
            </SubcontainerStyle>
        </>
    );
};
export default Submain;
