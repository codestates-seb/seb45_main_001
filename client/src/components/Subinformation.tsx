import React, { FC, useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';

const Subdiv = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #1d1d1d;
  padding: 20px;
`;

const SubTextform = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: #1D1D1D;
  padding: 40px;
  margin-bottom: 20px;
`;

const SubText1 = styled.p`
  font-size: 16px;
  color: white;
  margin-bottom: 15px;
  width: 900px;
  border: 1px solid white;
  padding: 50px;
  border-radius: 20px;
`;

const Labeldiv = styled.div`
  background-color: #1D1D1D;
  color: white;
  font-size: 23px;
  font-weight: 500;
  margin-bottom: 15px;
`;

const Imgform = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  background-image: #1D1D1D;
  overflow: hidden;
`;

const SubinforImg = styled.img`
  width: 160px;
  height: 250px;
  margin: 10px;
  object-fit: cover;
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.1);
  }
`;

const SliderWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const SliderButton = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  font-size: 20px;
  color: white;
  padding: 10px;
  outline: none;
`;

interface MovieInfo {
  id: number;
  overview: string;
}

interface Movie {
  movieId: number;
  posterUrl: string;
  rank: string;
  movieNm: string;
  genre: { genreNm: string }[];
  trailerUrl: string;
}

const Subinformation: FC = () => {
  const [movieInfo, setMovieInfo] = useState<MovieInfo | null>(null);
  const [top10Movies, setTop10Movies] = useState<Movie[]>([]);
  const [startIndex, setStartIndex] = useState(0);

  useEffect(() => {
    // 영화 개요 정보 가져오기
    const fetchMovieOverview = async () => {
      try {
        const response = await axios.get('http://13.209.157.148:8080/details/8/mainInfo');
        const movieData = response.data;
        const overview = movieData.detailsList.plot;
        setMovieInfo({ id: 3, overview });
      } catch (error) {
        console.error("영화 개요 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    // Top 10 영화 가져오기
    const fetchTop10Movies = async () => {
      try {
        const response = await axios.get('http://13.209.157.148:8080/top10');
        const top10MovieData = response.data.boxofficeList;
        setTop10Movies(top10MovieData);
      } catch (error) {
        console.error("Top 10 영화 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    if (movieInfo === null) {
      fetchMovieOverview();
    }

    fetchTop10Movies();
  }, [movieInfo]);

  const prevSlide = () => {
    if (startIndex > 0) {
      setStartIndex(startIndex - 1);
    }
  };

  const nextSlide = () => {
    if (startIndex < top10Movies.length - 5) {
      setStartIndex(startIndex + 1);
    }
  };

  return (
    <Subdiv>
      <SubTextform>
        <SubText1>{movieInfo?.overview}</SubText1>
      </SubTextform>
      <Labeldiv>Top 10 영화</Labeldiv>
      <SliderWrapper>
        <SliderButton onClick={prevSlide}>{'<'}</SliderButton>
        <Imgform>
          {top10Movies.slice(startIndex, startIndex + 5).map((movie) => (
            <SubinforImg key={movie.movieId} src={movie.posterUrl} alt={movie.movieNm} />
          ))}
        </Imgform>
        <SliderButton onClick={nextSlide}>{'>'}</SliderButton>
      </SliderWrapper>
    </Subdiv>
  );
};

export default Subinformation;