import { styled } from 'styled-components';
import text from '../assets/text.mp4';
import imageData from '../assets/data';

const Moviform = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    background-color: #1D1D1D;
    padding: 30px;
    align-items: center;
    justify-content: center;
`;
const SubMovieElement = styled.video`
    width: 780px;
    height: 400px;
    margin-bottom: 50px;
`;

const Line3tap = styled.div`
    border-bottom: 1px solid white;
    width: 780px;
    position: relative;
    margin-bottom: 15px;
`;

const Labeldiv = styled.div`
   background-color: #1D1D1D;
    color: white;
    font-size: 23px;
    font-weight: 500;
    margin-bottom: 15px;
    position: relative;
    right: 39%;
`

const Imgform = styled.div`
  display: flex;
  flex-direction: row;
  margin-bottom: 15px;
  background-image: #1D1D1D;
`
const SubinforImg = styled.img`
  width: 185px;
  height: 140px;
  position: relative;
  right: 8px;
  margin-left: 15px;
`

const SubMovie = () => {
  
    const renderImages = imageData.map((image, index) => (
        <SubinforImg key={index} src={image.url} alt={image.alt} />
    ));

    return (
        <Moviform>
            <SubMovieElement controls muted loop >
                <source src={text} type="video/mp4" />
                Your browser does not support the video tag.
            </SubMovieElement>
            <Labeldiv>포토</Labeldiv>
            <Line3tap />
            <Imgform>
          {renderImages}
            </Imgform>
        </Moviform>
    );
};

export default SubMovie;
