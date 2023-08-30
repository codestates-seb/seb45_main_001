import { styled } from 'styled-components';
import logo from '../assets/port.jpg';
import Header from './Header'

const SubcontainerStyle = styled.div`
    position: relative;
    width: 100%;
    display: flex;
    height: 100vh;
    margin: 0 auto;
    flex-direction: column;
    background-color: #1D1D1D;
`;


const PosterStyle = styled.div`
    display: flex;
    height: 550px;
    background-image: url(${logo});
    background-size : cover;
    background-position: center 18%;
`


const Submain = () => {
    return (
        <>
            <SubcontainerStyle>
            <PosterStyle>
                <Header />
          
                </PosterStyle>
            </SubcontainerStyle>
        </>
    );
};
export default Submain;
