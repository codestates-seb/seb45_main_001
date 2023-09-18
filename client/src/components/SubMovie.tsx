import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa';


const Moviform = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: #1D1D1D;
  padding: 30px;
  align-items: center;
`;


const Labeldiv = styled.div`
  background-color: #1D1D1D;
  color: white;
  font-size: 23px;
  font-weight: 500;
  margin-bottom: 15px;
  margin-top: 15px;
  text-align: center;
`;

const Gallery = styled.div`
  display: flex;
  overflow: hidden;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
`;

const GalleryContainer = styled.div`
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  scroll-behavior: smooth;
  margin-left: 10px;
  margin-right: 10px;
  width: calc(100% - 60px);
  max-width: 1000px;
  height: 160px;
`;

const GalleryItem = styled.img`
  width: 185px;
  height: 140px;
  margin-right: 15px;
  cursor: pointer;
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.1);
  }
`;

const ArrowButton = styled.button`
  background: none;
  border: none;
  font-size: 24px;
  color: #FFF;
  cursor: pointer;
  display: flex;
  align-items: center;
  outline: none;
`;

const VideoContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

interface StillCut {
  stillCut_url: string;
}

interface ApiResponse {
  detailsList: {
    stillCuts: StillCut[];
    trailers: string;
    youtubeReviews: string;
  };
}

const SubMovie: React.FC = () => {
  const [stillCuts, setStillCuts] = useState<StillCut[]>([]);
  const [trailers, setTrailers] = useState<string>('');
  const [startIndex, setStartIndex] = useState(0);
  const [youtube, setYoutube] = useState<string>('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<ApiResponse>('http://13.209.157.148:8080/details/52/mediaInfo');
        const data = response.data.detailsList;

        setStillCuts(data.stillCuts);
        setTrailers(data.trailers);
        setYoutube(data.youtubeReviews);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const prevSlide = () => {
    if (startIndex > 0) {
      setStartIndex(startIndex - 1);
    }
  };

  const nextSlide = () => {
    if (startIndex < stillCuts.length - 4) {
      setStartIndex(startIndex + 1);
    }
  };

  return (
    <Moviform>
      <VideoContainer>
        {/* Embed YouTube video using an iframe */}
        <iframe
          width="850"
          height="500"
          src={`https://www.youtube.com/embed/${trailers}`}
          title="YouTube video player"
          frameBorder="0"
          allowFullScreen
        ></iframe>
      </VideoContainer>
      <Labeldiv>유튜브 리뷰</Labeldiv>
      <VideoContainer>
        {/* Embed YouTube video using an iframe */}
        <iframe
          width="850"
          height="500"
          src={`https://www.youtube.com/embed/${youtube}`}
          title="YouTube video player"
          frameBorder="0"
          allowFullScreen
        ></iframe>
      </VideoContainer>
      <Labeldiv>포토</Labeldiv>
      <Gallery>
        <ArrowButton onClick={prevSlide}>
          <FaChevronLeft />
        </ArrowButton>
        <GalleryContainer>
          {stillCuts.slice(startIndex, startIndex + 4).map((stillCut, index) => (
            <GalleryItem
              key={index}
              src={stillCut.stillCut_url}
            />
          ))}
        </GalleryContainer>
        <ArrowButton onClick={nextSlide}>
          <FaChevronRight />
        </ArrowButton>
      </Gallery>
    </Moviform>
  );
};

export default SubMovie;