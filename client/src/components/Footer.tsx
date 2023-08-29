import React from 'react';
import { FooterStyle, Footerrow, Footercontent, Footerol, Footerli } from './styled';

const Footer = () => {
  return (
    <FooterStyle>
      <Footerrow>
        <Footercontent>일요시네마</Footercontent>
        <Footerol>
          <Footerli>FE-정진용 : jjyim1015@gmail.com</Footerli>
          <Footerli>FE-서강의 : olruck32@gmail.com</Footerli>
          <Footerli>FE-김희목 : as01050993729@gmail.co</Footerli>
          <Footerli>BE-박찬우 : http://local:3001.com</Footerli>
          <Footerli>BE-김동주 : hellscythes1004@gmail.com</Footerli>
          <Footerli>BE-장정규 : jeonggyu116@gmail.com</Footerli>
        </Footerol>
      </Footerrow>
    </FooterStyle>
  );
};

export default Footer;
