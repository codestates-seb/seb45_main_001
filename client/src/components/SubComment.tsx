import React, { useState, ChangeEvent } from 'react';
import { styled } from 'styled-components';


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
  const starRatings: number[] = [1, 2, 3, 4, 5]; // 별점 옵션 배열
  const [selectedRating, setSelectedRating] = useState<number | null>(null);
  const [comment, setComment] = useState<string>('');

  const handleStarRating = (rating: number) => {
    setSelectedRating(rating);
  };

  const handleCommentInput = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  const handleCommentSubmit = () => {
    if (selectedRating !== null) {
      const now = new Date();
      const timestamp = `${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;
      const newComment = {
        starRating: selectedRating,
        comment: `${comment} - ${timestamp}`, // 댓글과 시간을 결합하여 저장
      };
      setCommentsList(prevComments => [...prevComments, newComment]);
      setComment('');
      setSelectedRating(null);
    }
  };

  const [commentsList, setCommentsList] = useState<{ starRating: number; comment: string }[]>([]);

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
                active={rating <= (selectedRating ?? 0)} // null 별점 처리
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