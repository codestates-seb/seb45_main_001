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
  height: 120px;
`;

const Text1 = styled.span`
  color: rgb(255, 255, 255);
  margin-left: 10px;
  font-size: 28px;
  cursor: pointer;
  &:hover {
    color: red;
  }
`;

const Text2 = styled.button`
  color: rgb(255, 255, 255);
  margin-right: 10px;
  background-color: #727171;
  border: 1px solid white;
  border-radius: 30px;
  font-size: 15px;
  width: 80px;
  height: 40px;
  cursor: pointer;
  margin-top: 5px;
`;

const TextInput = styled.input`
  height: 60px;
  padding: 5px;
  background-color: #1d1d1d;
  font-size: 15px;
  color: white;
  border: none;
  outline: none;
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
  const [starRating, setStarRating] = useState<number>(0);
  const [comment, setComment] = useState<string>('');
  const [commentsList, setCommentsList] = useState<{ starRating: number; comment: string }[]>([]);

  const handleStarRating = (rating: number) => {
    setStarRating(rating);
  };

  const handleCommentInput = (e: ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };

  const handleCommentSubmit = () => {
    const newComment = {
      starRating,
      comment,
    };
    setCommentsList([...commentsList, newComment]);
    setComment('');
  };

  return (
    <>
      <Textform>
        <TextBox>
          <TextBox2>
            <Text1 onClick={() => handleStarRating(5)}>☆☆☆☆☆</Text1>
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