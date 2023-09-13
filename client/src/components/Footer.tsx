import React from 'react';
import { FooterStyle, Footerrow, Footercontent, Footerol, Footerli } from './styled';

const Footer = () => {

    return (
        <FooterStyle>
            <Footerrow>
                <Footercontent>일요시네마</Footercontent>
                <Footerol>
                    <Footerli>FE-정진용 : <a href='https://github.com/jinyong1015' target='_blank' rel="noreferrer">jinyong1015</a></Footerli>
                    <Footerli>FE-서강의 : <a href='https://github.com/ColmiismaL' target='_blank' rel="noreferrer">ColmiismaL</a></Footerli>
                    <Footerli>FE-김희목 : <a href='https://github.com/kjd43871' target='_blank' rel="noreferrer">kjd43871</a></Footerli>
                    <Footerli>BE-박찬우 : <a href='https://github.com/chanwoopark9301' target='_blank' rel="noreferrer">chanwoopark9301</a></Footerli>
                    <Footerli>BE-김동주 : <a href='https://github.com/fsnzsaber' target='_blank' rel="noreferrer">fsnzsaber</a></Footerli>
                    <Footerli>BE-장정규 : <a href='https://github.com/JangJeonggyu' target='_blank' rel="noreferrer">JangJeonggyu</a></Footerli>
                </Footerol>
            </Footerrow>
        </FooterStyle>
    );
}
export default Footer;