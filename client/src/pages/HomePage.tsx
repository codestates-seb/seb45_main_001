import React from 'react';
import Header from '../components/Header';
import Banner from '../components/Banner/Banner';
import TopRow from '../components/Row/TopRow';
import GenreRow from '../components/Row/GenreRow';

const HomePage = () => {
  return (
    <>
      <Header />
      <Banner />
      <TopRow title="Top 10 Movies" id="top10"isLargeRow={true} />
      <GenreRow title="애니메이션" id="animation" genre="애니메이션" isLargeRow={true}/>
      <GenreRow title="액션" id="action" genre="액션" isLargeRow={true}/>
      <GenreRow title="판타지" id="fantasy" genre="판타지" isLargeRow={true}/>
      <GenreRow title="로맨스" id="romance" genre="멜로/로맨스" isLargeRow={true}/>
      <GenreRow title="공포" id="scary" genre="공포(호러)" isLargeRow={true}/>
    
    </>
  );  
};

export default HomePage;
