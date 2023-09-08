import React from 'react';
import Header from '../components/Header';
import Banner from '../components/Banner';
import Row from '../components/Row';
import requests from '../api/requests';

const HomePage = () => {
  return (
    <>
      <Header />
      <Banner />
      <Row title="Top Rated" id="TR" fetchURl={requests.fetchTopRated} isLargeRow />
      <Row title="Trending Now" id="TN" fetchURl={requests.fetchTrending} isLargeRow={false} />
      <Row title ="NETFLIX ORIGINALS"id="NO" fetchURl={requests.fetchNetflixOriginals} isLargeRow={false} /> 
      <Row title="Action Movies" id="AM" fetchURl={requests.fetchActionMovies} isLargeRow={false} />
      <Row title="Comedy Movies" id="CM" fetchURl={requests.fetchComedyMovies} isLargeRow={false} />
    </>
  );  
};

export default HomePage;
