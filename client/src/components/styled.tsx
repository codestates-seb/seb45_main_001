import { styled } from 'styled-components';

export const FooterStyle = styled.footer`
    display: block;
    height: 200px;
    position: fixed;
    bottom: 0;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #1d1d1d;
`;

export const Footerrow = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
`;

export const Footercontent = styled.p`
    font-size: 20px;
    color: #d6a701;
    font-weight: bold;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100px;
    margin: 0px 50px;
`;
export const Footerol = styled.ol`
    display: flex;
    flex-direction: column;
    position: relative;
    width: 240px;
    list-style-type: none;
`;

export const Footerli = styled.li`
    font-size: 13px;
    color: #ffffff;
    font-style: italic;
    margin-top: 2px;
`;

export const ContainerStyle = styled.div`
    position: relative;
    width: 100%;
    display: flex;
    height: 1048px;
    margin: 0 auto;
    border: 1px solid orange;
`;
