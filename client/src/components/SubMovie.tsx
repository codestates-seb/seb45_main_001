import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa';


type MediaInfoResponse = {
  detailsList: {
    stillCuts: string[];
    trailers: { vodClass: string; trailer_url: string }[];
    youtubeReviews: string[];
  };
};

const Moviform = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: #1D1D1D;
  padding: 30px;
  align-items: center;
`;

const SubMovieElement = styled.video`
  width: 780px;
  height: 400px;
  margin-bottom: 50px;
`;

const Labeldiv = styled.div`
  background-color: #1D1D1D;
  color: white;
  font-size: 23px;
  font-weight: 500;
  margin-bottom: 15px;
  text-align: center;
`;

const TrailerDiv = styled.div`
  display: flex;
  flex-direction: row;
`

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

const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ModalContent = styled.div`
  background-color: transparent;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  position: relative;
`;

const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
`;

const ModalImage = styled.img`
  width: 800px;
  height: 600px;
  object-fit: contain;
`;

const YoutubeReviewThumbnail = styled.img`
  width: 255px;
  height: 150px;
  object-fit: cover;
  margin-right: 10px;
  cursor: pointer;
`;

const API_URL = 'http://13.209.157.148:8080/details/8/mediaInfo';

const SubMovie: React.FC = () => {
  const [stillCuts, setStillCuts] = useState<string[]>([]);
  const [trailers, setTrailers] = useState<{ vodClass: string; trailer_url: string }[]>([]);
  const [youtubeReviews, setYoutubeReviews] = useState<string[]>([]);
  const [startIndex, setStartIndex] = useState(0);
  const [modalImage, setModalImage] = useState<string | null>(null);

  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    axios
      .get<MediaInfoResponse>(API_URL)
      .then((response) => {
        const { stillCuts, trailers, youtubeReviews } = response.data.detailsList;
        setStillCuts(stillCuts);
        setTrailers(trailers);
        setYoutubeReviews(youtubeReviews);
      })
      .catch((error) => {
        console.error('데이터를 가져오는 동안 오류 발생:', error);
      });
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

  const openModal = (imageUrl: string) => {
    setModalImage(imageUrl);
  };

  const closeModal = () => {
    setModalImage(null);
  };

  const closeModalOutside = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      closeModal();
    }
  };

  const extractVideoId = (url: string) => {
    const videoIdMatch = url.match(/v=([^&]+)/);
    return videoIdMatch ? videoIdMatch[1] : null;
  };

  return (
    <Moviform>
      <VideoContainer>
        {trailers.length > 0 && (
          <SubMovieElement
            ref={videoRef}
            controls
            autoPlay
            muted
            loop
            width="100%"
            height="100%"
          >
            Your browser does not support the video tag.
          </SubMovieElement>
        )}
      </VideoContainer>
      <Labeldiv>포토</Labeldiv>
      <Gallery>
        <ArrowButton onClick={prevSlide}>
          <FaChevronLeft />
        </ArrowButton>
        <GalleryContainer>
          {stillCuts.slice(startIndex, startIndex + 4).map((imageUrl, index) => (
            <GalleryItem
              key={index}
              src={imageUrl}
              alt={`Still Cut ${index}`}
              onClick={() => openModal(imageUrl)}
            />
          ))}
        </GalleryContainer>
        <ArrowButton onClick={nextSlide}>
          <FaChevronRight />
        </ArrowButton>
      </Gallery>
      {modalImage && (
        <Modal onClick={closeModalOutside}>
          <ModalContent>
            <CloseButton onClick={closeModal}>닫기</CloseButton>
            <ModalImage src={modalImage} alt="큰 이미지" />
          </ModalContent>
        </Modal>
      )}
      <Labeldiv>유튜브 리뷰</Labeldiv>
      <TrailerDiv>
        {youtubeReviews.slice(0, 3).map((review, index) => {
          const videoId = extractVideoId(review);
          const thumbnailUrl = videoId
            ? `https://img.youtube.com/vi/${videoId}/0.jpg`
            : '';

          return (
            <div key={index}>
              <a href={review} target="_blank" rel="noopener noreferrer">
                <YoutubeReviewThumbnail
                  src={thumbnailUrl}
                  alt={`리뷰 섬네일 ${index}`}
                  onClick={() => window.open(review, '_blank')}
                />
              </a>
            </div>
          );
        })}
      </TrailerDiv>
    </Moviform>
  );
};

export default SubMovie;