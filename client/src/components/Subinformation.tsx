import { styled } from 'styled-components';
import test1 from '../assets/test1.png';
import test2 from '../assets/test2.png';
import test3 from '../assets/test3.png';
import test4 from '../assets/test4.png';

const Subdiv = styled.div`
    width: 100%;
    display: flex;
    margin: 0 auto;
    flex-direction: column;
    background-color: #1d1d1d;

`;
const SubTextform = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-image: #1D1D1D;
    padding: 40px;
`;

const SubText1 = styled.p`
    font-size: 16px;
    color: white;
    margin-bottom: 15px;
`;

const Line3tap = styled.div`
    border-bottom: 1px solid white;
    width: 780px;
    position: relative;
    left: 60px;
    margin-bottom: 15px;
`;
const Labeldiv = styled.div`
   background-color: #1D1D1D;
    color: white;
    font-size: 23px;
    font-weight: 500;
    margin-bottom: 15px;
    position: relative;
    right: 37%;
`
const Imgform = styled.div`
  display: flex;
  flex-direction: row;
  margin-bottom: 15px;
  background-image: #1D1D1D;
`
const SubinforImg = styled.img`
  width: 135px;
  height: 201px;
  margin-left: 30px;
  position: relative;
  left: 20px;
`
const SubTextform2 = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    background-image: #1D1D1D;
`;

const AcotrName = styled.p`
  font-size: 15px;
  color: white;
  margin-left: 45px;
  right: 27px;
  position: relative;
`

const Subinformation = () => {
    return (
        <>
            <Subdiv>
                <SubTextform>
                    <SubText1>나는 이제 죽음이요, 세상의 파괴자가 되었다.</SubText1>
                    <SubText1>
                        세상을 구하기 위해 세상을 파괴할지도 모르는 선택을 해야 하는 천재 과학자의 핵개발 프로젝트.
                    </SubText1>
                </SubTextform>
                <Labeldiv>출연진</Labeldiv>
                <Line3tap />
                <Imgform>
                <SubinforImg src={test1} />
                <SubinforImg src={test2} />
                <SubinforImg src={test3} />
                <SubinforImg src={test4} />
                <SubinforImg src={test1} />
                </Imgform>
                <SubTextform2>
                <AcotrName>감독: 크리스토퍼놀란</AcotrName>
                <AcotrName>주연: 맷 데이먼</AcotrName>
                <AcotrName>조연: 조쉬 하트넷</AcotrName>
                <AcotrName>조연: 케네스 브래너</AcotrName>
                <AcotrName>주연: 킬리언 머피</AcotrName>
                </SubTextform2>
            </Subdiv>
        </>
    );
};
export default Subinformation;
