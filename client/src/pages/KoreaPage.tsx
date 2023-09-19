import React from 'react';
import Header from '../components/Header';
import KrBanner from '../components/Banner/KrBanner';
import KoreaTopRow from '../components/Row/KoreaTopRow';
import KoreaGenreRow from '../components/Row/KoreaGenreRow';

const KoreaPage = () => {
  return (
    <>
      <Header />
      <KrBanner />
      <KoreaTopRow title="Top 10 Movies" id="top10"isLargeRow={true} />
      {/* <KoreaGenreRow title="애니메이션" id="animation" genre="애니메이션" isLargeRow={true}/> */}
      <KoreaGenreRow title="액션" id="action" genre="액션" isLargeRow={true}/>
      {/* <KoreaGenreRow title="판타지" id="fantasy" genre="판타지" isLargeRow={true}/> */}
      <KoreaGenreRow title="로맨스" id="romance" genre="멜로/로맨스" isLargeRow={true}/>
      {/* <KoreaGenreRow title="공포" id="scary" genre="공포(호러)" isLargeRow={true}/> */}
    
    </>
  );  
};

export default KoreaPage;
