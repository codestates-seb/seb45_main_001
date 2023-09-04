import React, { useState } from 'react';
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
  &:hover{
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
  height: 60px; /* 줄어든 높이에 맞추기 위해 높이를 조정 */
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

`
const TextListli = styled.li`
  font-size: 15px;
  color: white;
  margin-bottom: 2px;
  text-align: start;
`

const SubComment = () => {
  const [starRating, setStarRating] = useState(0); // 별점 상태 관리
  const [comment, setComment] = useState(''); // 댓글 상태 관리

  // 별점 선택 시 호출되는 함수
  const handleStarRating = (rating: number) => {
    setStarRating(rating);
  };
  
  // 댓글 입력 시 호출되는 함수
  const handleCommentInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.target.value);
  };
  return (
    <>
      <Textform>
        <TextBox>
          <TextBox2>
            <Text1 onClick={() => handleStarRating(5)}>☆☆☆☆☆</Text1>
            <Text2>댓글등록</Text2>
          </TextBox2>
          <TextInput
            type="text"
            value={comment}
            onChange={handleCommentInput}
            placeholder="댓글을 입력하여 주세요."
          />
        </TextBox>
        <TextBox3>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: {comment}</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 유사 이래 왜구들의 노략질과 1592년 임진왜란, 그리고 한일병탄. 지긋지긋한 왜놈들의 지배를 벗어나게 해준 결정적 한방. 성층권을 향하여 의뭉스럽게 피어오르는 핵구름 속에서 한국의 과거 현재 미래를 본다. 다음 침략에선 우리는 다시 노예가 되지 않을수 있겠는가? 아니다라고 확실히 말할 자들이 없을 것이다.</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 한낱 과학자도 윤리적인 문제로 고민하고 있는 판국에 21세기에 전쟁도 불싸르겠다는 미친 나라 대표가 있다</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 개봉일 보고 10점 준다</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 이영화는 뼈속까지 일본인 윤,썩,렬,과 토.착.왜.구.국.짐.당.이 극도로 싫어하는 영화 입니다. 정삭적인 대한민국 국민은 전부 영화을 봐야합니다. 이제 3년 반 남았습니다...</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 히로시마 리틀보이의 콜래트럴 데미지로 조선인 2만명 사망(추정설)자에게 애도를 표하며.</p>
            </TextListli>
          </TextList>
          <TextList>
            <TextListli>
            <p>별점: {starRating}</p>
            <p>댓글: 놀란의 영화는 정말 미쳤다는 말밖에 안 나온다. 광복절을 맞이하여 이런 영화를 선사해준 놀란에게 경의를 표합니다.</p>
            </TextListli>
          </TextList>
        </TextBox3>
      </Textform>
    </>
  );
};

export default SubComment;