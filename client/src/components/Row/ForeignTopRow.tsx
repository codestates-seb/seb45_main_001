import { styled } from 'styled-components';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import SwiperCore, { Navigation, Pagination, Scrollbar, A11y } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import '../../css/Row.css';

SwiperCore.use([Navigation, Pagination, Scrollbar, A11y]);

const RowLayout = styled.section`
    margin-left: 20px;
    // margin-top: 10px;
    color: white;

    h2 {
        padding-left: 20px;
        // padding-bottom: 15px;
    }
`;

const RowPosters = styled.div`
    display: flex;
    overflow-y: hidden;
    overflow-x: scroll;
    padding: 20px 0 20px 20px;
    scroll-behavior: smooth;

    ::-webkit-scrollbar {
        display: none;
    }
`;

const RowPoster = styled.img`
    object-fit: contain;
    width: 100%;
    max-height: 144px;
    margin-right: 10px;
    transition: transform 450ms;
    border-radius: 4px;
    padding: 25px 0;
    box-sizing: content-box;
    cursor: pointer;

    :hover {
        transform: scale(1.08);
    }

    @media screen and (min-width: 1200px) {
        max-height: 160px;
    }

    @media screen and (max-width: 768px) {
        max-height: 100px;
    }
`;

const RowPosterLarge = styled.img`

  max-width: 100%;
  max-height: 320px;
  padding: 25px 0;
  box-sizing: content-box;
  cursor: pointer;
  
  :hover {
    transform: scale(1.1);
    opacity: 1;
  }

  @media screen and (min-width: 1200px) {
    max-height: 360px;
  }
  
  @media screen and (max-width: 768px) {
    max-height: 280px;
  }

`;

interface ForeignTopRow {
    movieId: number;
    movieNm?: string;
    posterUrl: string;
    rank: number;
}

interface ForeignTopRowProps {
    isLargeRow: boolean;
    title: string;
    id: string;
}

const ForeignTopRow = ({ isLargeRow, title, id }: ForeignTopRowProps) => {
    const navigate = useNavigate();
    const [movies, setMovies] = useState<ForeignTopRow[]>([]);
    const url = process.env.REACT_APP_API_URL;

    useEffect(() => {
        fetchMovieData();
    }, []);

    const fetchMovieData = async () => {
        const response = await axios.get(`${url}/top10Foreign`);
        console.log('response', response);
        const top10Movies = response.data.boxofficeList.slice(0, 10);
        setMovies(top10Movies);
    };

    const navigateTo = (movieId: number) => {
        navigate(`/Submain/${movieId}`);
    };

    return (
        <RowLayout>
            <h2>{title}</h2>
            <Swiper
                modules={[Navigation, Pagination, Scrollbar, A11y]}
                loop={true}
                breakpoints={{
                    1378: {
                        slidesPerView: 6, // 한번에 보이는 슬라이드 개수
                        slidesPerGroup: 6, // 몇개씩 슬라이드 할지
                    },
                    998: {
                        slidesPerView: 5,
                        slidesPerGroup: 5,
                    },
                    625: {
                        slidesPerView: 4,
                        slidesPerGroup: 4,
                    },
                    0: {
                        slidesPerView: 3,
                        slidesPerGroup: 3,
                    },
                }}
                navigation // arrow 버튼 사용 유무
                pagination={{ clickable: true }} // 페이지 버튼 보이게 할지
            >
                <RowPosters id={id}>
                    {movies.map((movie) => (
                        // eslint-disable-next-line react/jsx-key
                        <SwiperSlide>
                            <React.Fragment key={movie.rank}>
                                {isLargeRow ? (
                                    <RowPosterLarge
                                        src={movie.posterUrl}
                                        alt={movie.movieNm}
                                        onClick={() => navigateTo(movie.movieId)}
                                    />
                                ) : (
                                    <RowPoster
                                        src={movie.posterUrl}
                                        alt={movie.movieNm}
                                        onClick={() => navigateTo(movie.movieId)}
                                    />
                                )}
                            </React.Fragment>
                        </SwiperSlide>
                    ))}
                </RowPosters>
            </Swiper>
        </RowLayout>
    );
};

export default ForeignTopRow;
