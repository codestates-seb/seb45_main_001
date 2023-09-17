import React, { FC, useEffect, useState } from 'react';
import styled from 'styled-components';
import Header from './Header';
import Subinformation from './Subinformation';
import SubMovie from './SubMovie';
import SubComment from './SubComment';
import axios from 'axios';

// Styles and components

const SubcontainerStyle = styled.div`
  width: 100%;
  display: flex;
  margin: 0 auto;
  flex-direction: column;
  background-color: #1d1d1d;
`;

const PosterStyle = styled.div`
  display: flex;
  height: 550px;
  background-size: cover;
  background-position: center 45%;
`;

const MainStyle = styled.div`
  display: flex;
  flex-direction: column;
  padding: 70px 30px 60px 0;
  align-items: center;
  justify-content: center;
  @media (max-width: 780px) {
    position: relative;
    left: 25%;
  }
`;

const Moivetextform = styled.div`
  display: flex;
  flex-direction: row;
  margin: 5px;
`;

const MoivePoster2 = styled.img`
  width: 210px;
  height: 308px;
`;

const Movidetail = styled.div`
  display: flex;
  flex-direction: column;
  padding: 8px 60px;
  @media (max-width: 780px) {
    padding: 8px 30px;
  }
`;

const TextForm = styled.div`
  display: flex;
  flex-direction: column;
`;

const Texth3 = styled.span`
  font-size: 30px;
  color: white;
  @media (max-width: 780px) {
    font-size: 20px;
  }
`;

const Buttondiv = styled.span`
  background-color: red;
  display: inline-block;
  height: 26px;
  padding: 0px 10px;
  margin: 5px 0 0 8px;
  position: relative;
  bottom: 8px;
  border: 1px solid #e92130;
  border-radius: 28px;
  font-weight: 28px;
  font-size: 13px;
  color: #fff;
  text-align: center;
`;

const Texth4 = styled.p`
  font-size: 24px;
  color: #dddada;
`;

const TextForm2 = styled.div`
  display: flex;
  flex-direction: row;
  padding: 20px 0 48px;
  align-items: flex-start;
`;

const Textdetail1 = styled.div`
  display: flex;
  flex-direction: column;
  margin: 15px 60px 0 0px;
`;

const Text5 = styled.p`
  font-size: 14px;
  width: 150px;
  color: white;
  margin-bottom: 10px;
`;

const Ulform = styled.div`
  display: flex;
  flex-direction: column;
`;

const Ultap = styled.ul`
  margin: 0 auto;
  list-style: none;
  margin-block-start: 1em;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

const Litap = styled.li`
  list-style: none;
  margin: 0px 75px;
  font-size: 20px;
  width: 150px;
  height: 50px;
  text-align: center;
  border-radius: 30px;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: white;
    border: 1px solid white;
    border-radius: 30px;
    color: black;
    font-weight: 600;
  }

  &.active {
    background-color: white;
    border: 1px solid white;
    border-radius: 30px;
    color: black;
    font-weight: 600;
  }
  @media (max-width: 780px) {
    width: 100px;
    font-size: 15px;
  }
`;

const Desc = styled.div`
  text-align: center;
`;

interface MovieData {
  detailsList: {
    openDt: string;
    rank: number;
    movieNm: string;
    movieNmEn: string;
    audiAcc: string;
    poster: string[];
    genre: string[];
    nation: string[];
    watchGradeNm: string;
  };
}

interface MovieInfo {
  id: number;
  title: string;
  original_title: string;
  release_date: string;
  rank: number;
  genres: string;
  production_countries: string;
  certification: string;
  vote_average: number;
  vote_count: string;
  poster_path: string;
}

const Submain: FC = () => {
  const [posterImageUrl, setPosterImageUrl] = useState("");
  const [currentTab, clickTab] = useState(0);
  const [stillCuts, setStillCuts] = useState<string[]>([]);
  const [movieInfo, setMovieInfo] = useState<MovieInfo | null>(null);
  const menuArr = [
    { name: '주요 정보', content: <Subinformation /> },
    { name: '영상/포토', content: <SubMovie /> },
    { name: '댓글/평점', content: <SubComment /> },
  ];

  const selectMenuHandler = (index: number) => {
    clickTab(index);
  };

  useEffect(() => {
    // Fetch movie data
    const fetchMovieData = async () => {
      try {
        const response = await axios.get<MovieData>('http://13.209.157.148:8080/details/8');
        const movieData = response.data.detailsList;

        // Convert genre data to a string if it's an array
        const genres = Array.isArray(movieData.genre) ? movieData.genre.join(', ') : movieData.genre;

        // Convert nation data to a string if it's an array
        const production_countries = Array.isArray(movieData.nation) ? movieData.nation.join(', ') : movieData.nation;

        // Update state to match the new movie data structure
        setMovieInfo({
          id: 3,
          title: movieData.movieNm,
          original_title: movieData.movieNmEn,
          release_date: movieData.openDt,
          rank: movieData.rank,
          genres,
          production_countries,
          certification: movieData.watchGradeNm,
          vote_average: 0,
          vote_count: movieData.audiAcc,
          poster_path: movieData.poster[0]
        });

        // Set poster image URL
        setPosterImageUrl(movieData.poster[0]);
      } catch (error) {
        console.error("Error fetching movie data:", error);
      }
    };

    if (movieInfo === null) {
      fetchMovieData();
    }

    // Fetch still cuts
    axios
      .get('http://13.209.157.148:8080/top10/58')
      .then((response) => {
        const { backDrop } = response.data.genreMovieList;
        setStillCuts(backDrop);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, [movieInfo]);

  return (
    <>
      <SubcontainerStyle>
        {/* Use the first still cut image as the background image */}
        <PosterStyle style={{ backgroundImage: `url(${stillCuts[1]})` }}>
          <Header />
        </PosterStyle>
        <MainStyle>
          <Moivetextform>
            <MoivePoster2 src={posterImageUrl}></MoivePoster2>
            <Movidetail>
              <TextForm>
                <Texth3>
                  {movieInfo?.title} <Buttondiv>상영중</Buttondiv>
                </Texth3>
                <Texth4>{movieInfo?.original_title}, {movieInfo?.release_date}</Texth4>
              </TextForm>
              <TextForm2>
                <Textdetail1>
                  <Text5>개봉 : {movieInfo?.release_date}</Text5>
                  <Text5>장르 : {movieInfo?.genres}</Text5>
                  <Text5>국가 : {movieInfo?.production_countries}</Text5>
                  <Text5>등급 : {movieInfo?.certification}</Text5>
                </Textdetail1>
                <Textdetail1>
                  <Text5>평점 : {movieInfo?.vote_average}</Text5>
                  <Text5>누적관객 : {movieInfo?.vote_count}명</Text5>
                  <Text5>박스오피스 : {movieInfo?.rank}위</Text5>
                </Textdetail1>
              </TextForm2>
            </Movidetail>
          </Moivetextform>
          <Ulform>
            <Ultap>
              {menuArr.map((el, index) => (
                <Litap key={index} className={index === currentTab ? 'active' : ''}
                  onClick={() => selectMenuHandler(index)}>{el.name}</Litap>
              ))}
            </Ultap>
            <Desc>
              <div>{menuArr[currentTab].content}</div>
            </Desc>
          </Ulform>
        </MainStyle>
      </SubcontainerStyle>
    </>
  );
};

export default Submain;