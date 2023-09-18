import React, { useState, useEffect, ChangeEvent } from 'react';
import { styled } from 'styled-components';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const Textform = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: #1D1D1D;
  padding: 15px;
  align-items: center;
  justify-content: center;
`;

const TextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border: 1px solid white;
  width: 780px;
  height: 180px;
`;

const TextBox2 = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: 1px solid white;
  width: 780px;
  height: 55px;
`;

const Starbox = styled.div`
  display: flex;
  margin-left: 10px;
  justify-content: center;
  align-items: center;
`

const Text1 = styled.span<{ active: boolean }>`
  color: ${props => (props.active ? 'red' : 'rgb(255, 255, 255)')};
  font-size: 28px;
  margin-left: 3px;
  cursor: pointer;
  &:hover {
    color: red;
  }
`;

const Text2 = styled.button`
  color: rgb(255, 255, 255);
  background-color: #727171;
  border: 1px solid white;
  border-radius: 30px;
  font-size: 15px;
  width: 80px;
  height: 40px;
  margin-top: 5px;
  margin-left: 10px;
  margin-right: 10px;
  cursor: pointer;
  margin-top: 5px;
`;

const TextInput = styled.input`
  height: 135px;
  padding: 5px;
  background-color: #1d1d1d;
  font-size: 15px;
  color: white;
  border: none;
  outline: none;
  display: flex;
  justify-content: flex-start;
`;

const TextBox3 = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border: 1px solid white;
  width: 780px;
  border: 1px solid white;
  margin-top: 20px;
`;

const TextList = styled.ul`
  display: flex;
  flex-direction: column;
  list-style: none;
  border-bottom: 1px solid white;
  padding: 8px;
`;

const TextListli = styled.li`
  font-size: 15px;
  color: white;
  margin-bottom: 2px;
  text-align: start;
`;

const SubComment = () => {
  const starRatings: number[] = [1, 2, 3, 4, 5];
  const [selectedRating, setSelectedRating] = useState<number | null>(null);
  const [comment, setComment] = useState<string>('');
  const { movieId } = useParams(); // URL 파라미터인 'movieId'를 읽어옵니다.
  const [commentsList, setCommentsList] = useState<{ starRating: number; comment: string }[]>([]);

  const handleStarRating = (rating: number) => {
    setSelectedRating(rating);
  };

  const handleCommentInput = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  

  const handleCommentSubmit = async () => {
    if (selectedRating !== null) {
      const now = new Date();
      const timestamp = `${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;
      const newComment = {
        starRating: selectedRating,
        comment: `${comment} - ${timestamp}`,
      };
  

      // 인증 토큰을 가져오기
      const authToken = localStorage.getItem("jwt");
  
      // 인증 토큰을 헤더에 추가
      const config = {
        headers: {
          Authorization: `Bearer ${authToken}`,
        },
      };
  
      // API 엔드포인트 및 데이터 전송
      try {
        await axios.post(
          `http://localhost:8080/api/comments/${movieId}=1&userId=1`,
          newComment,
          config
        );
  
        // 성공적으로 댓글이 추가되었을 때 처리
        console.log('댓글이 성공적으로 추가되었습니다.');
  
        // 댓글 목록 업데이트 등의 작업 수행
        setCommentsList(prevComments => [...prevComments, newComment]);
        setComment('');
        setSelectedRating(null);
      } catch (error) {
        console.error('댓글 추가 중 오류 발생:', error);
        // 오류 처리
      }
    }
  };

  // 컴포넌트가 처음 렌더링될 때 댓글을 가져오는 useEffect
  useEffect(() => {
    // 댓글을 가져오는 API 엔드포인트 주소
    const apiUrl = `http://13.209.157.148:8080/api/comments/movie/${movieId}`;

    // API 요청 보내기
    axios.get(apiUrl)
      .then(response => {
        // 성공적으로 댓글을 가져왔을 때 댓글 목록 업데이트
        setCommentsList(response.data);
      })
      .catch(error => {
        console.error('댓글 가져오기 오류:', error);
        // 오류 처리
      });
  }, [movieId]); // movieId가 변경될 때마다 useEffect가 다시 실행됨

  // 나머지 함수들은 이전과 동일하게 유지

  return (
    <>
      <Textform>
        <TextBox>
          <TextBox2>
            <Starbox>
              {starRatings.map((rating, index) => (
                <Text1
                  key={index}
                  onClick={() => handleStarRating(rating)}
                  active={rating <= (selectedRating ?? 0)}
                >
                  ☆
                </Text1>
              ))}
            </Starbox>
            <Text2 onClick={handleCommentSubmit}>댓글등록</Text2>
          </TextBox2>
          <TextInput
            type="text"
            value={comment}
            onChange={handleCommentInput}
            placeholder="댓글을 입력하여 주세요."
          />
        </TextBox>
        <TextBox3>
          {commentsList.map((item, index) => (
            <TextList key={index}>
              <TextListli>
                <p>별점: {item.starRating}</p>
                <p>댓글: {item.comment}</p>
              </TextListli>
            </TextList>
          ))}
        </TextBox3>
      </Textform>
    </>
  );
};

export default SubComment;