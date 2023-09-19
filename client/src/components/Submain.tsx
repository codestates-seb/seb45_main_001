import React, { FC, useEffect, useState} from 'react';
import styled from 'styled-components';
import Header from './Header';
import Subinformation from './Subinformation';
import SubMovie from './SubMovie';
import SubComment from './SubComment';
import axios from 'axios';
import { useParams } from 'react-router-dom'; // react-router-dom에서 useParams를 가져옵니다.
// Styles and components

const SubcontainerStyle = styled.div`
  width: 100%;
  display: flex;
  margin: 0 auto;
  flex-direction: column;
  background-color: #1d1d1d;
`;

const PosterStyle = styled.div<{ backgroundImage: string }>`
  display: flex;
  height: 550px;
  background-size: cover;
  background-position: center 45%;
  background-image: url(${(props) => props.backgroundImage});
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
  font-weight: bold;
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
    font-weight: bold;
  }

  &.active {
    background-color: white;
    border: 1px solid white;
    border-radius: 30px;
    color: black;
    font-weight: bold;
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
    movieId: number;
    openDt: string;
    rank: number;
    movieNm: string;
    movieNmEn: string;
    audiAcc: string;
    poster: string;
    genre: string[];
    nation: string[];
    watchGradeNm: string;
    backDrop: string; 

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
  vote_count: string;
  poster_path: string;
  banner:string;
}

const Submain: FC = () => {
  const [posterImageUrl, setPosterImageUrl] = useState<string>("");
  const [posterImageUrl2, setPosterImageUrl2] = useState<string>("");
  const [currentTab, setCurrentTab] = useState<number>(0);
  const [movieInfo, setMovieInfo] = useState<MovieInfo | null>(null);
  const [averageRating, setAverageRating] = useState<number | null>(null); // New state for average rating
  const { movieId } = useParams();
  const menuArr = [
    { name: '주요 정보', content: <Subinformation /> },
    { name: '영상/포토', content: <SubMovie /> },
    { name: '댓글/평점', content: <SubComment /> },
  ];

  const selectMenuHandler = (index: number) => {
    setCurrentTab(index);
  };

  useEffect(() => {
    const fetchMovieData = async () => {
      try {
        const response = await axios.get<MovieData>(`http://13.209.157.148:8080/details/${movieId}`);
        const movieData = response.data.detailsList;
        const genres = Array.isArray(movieData.genre) ? movieData.genre.join(', ') : movieData.genre;
        const production_countries = Array.isArray(movieData.nation) ? movieData.nation.join(', ') : movieData.nation;

        setMovieInfo({
          id: movieData.movieId,
          title: movieData.movieNm,
          original_title: movieData.movieNmEn,
          release_date: movieData.openDt,
          rank: movieData.rank,
          genres,
          production_countries,
          certification: movieData.watchGradeNm,
          vote_count: movieData.audiAcc,
          poster_path: movieData.poster,
          banner: movieData.backDrop 
        });

        setPosterImageUrl(movieData.poster);
        setPosterImageUrl2(movieData.backDrop);
      } catch (error) {
        console.error('Error fetching movie data:', error);
      }
    };


      fetchMovieData();

  }, [movieId]);

  // Fetch average rating when movieInfo.id changes
  useEffect(() => {
    const fetchAverageRating = async () => {
      try {
        const response = await axios.get<number>(`http://13.209.157.148:8080/api/comments/movie/${movieInfo?.id}/average-rating`);
        const avgRating = response.data;

        setAverageRating(avgRating);
      } catch (error) {
        console.error('Error fetching average rating:', error);
      }
    };

    if (movieInfo?.id) {
      fetchAverageRating();
    }
  }, [movieInfo?.id]);

  return (
    <>
      <SubcontainerStyle>
        <PosterStyle backgroundImage={posterImageUrl2}>
          <Header />
        </PosterStyle>
        <MainStyle>
          <Moivetextform>
            <MoivePoster2 src={posterImageUrl} alt="Movie Poster" />
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
                <Text5>평점 : {averageRating !== null ? averageRating.toFixed(1) : 'N/A'}</Text5>
                  <Text5>누적관객 : {movieInfo?.vote_count}명</Text5>
                  <Text5>박스오피스 : {movieInfo?.rank}위</Text5>
                </Textdetail1>
              </TextForm2>
            </Movidetail>
          </Moivetextform>
          <Ulform>
            <Ultap>
              {menuArr.map((el, index) => (
                <Litap
                  key={index}
                  className={index === currentTab ? 'active' : ''}
                  onClick={() => selectMenuHandler(index)}
                >
                  {el.name}
                </Litap>
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