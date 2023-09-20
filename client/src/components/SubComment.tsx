import React, { useState, useEffect, ChangeEvent } from 'react';
import { styled } from 'styled-components';
import axios from 'axios';
import { useParams } from 'react-router-dom';

interface Comment {
    score: number;
    content: string;
}

const Textform = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    background-color: #1d1d1d;
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
`;

const Text1 = styled.span<{ active: boolean }>`
    color: ${(props) => (props.active ? 'red' : 'rgb(255, 255, 255)')};
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

const SubComment: React.FC = () => {
    const starRatings: number[] = [1, 2, 3, 4, 5];
    const [selectedRating, setSelectedRating] = useState<number | null>(null);
    const [comment, setComment] = useState<string>('');
    const { movieId } = useParams<{ movieId: string }>(); // URL 파라미터인 'movieId'를 읽어옵니다.
    const [commentsList, setCommentsList] = useState<Comment[]>([]);
    const [memberId, setMemberId] = useState<string | null>(null);

    const handleStarRating = (rating: number) => {
        setSelectedRating(rating);
    };

    const handleCommentInput = (e: ChangeEvent<HTMLInputElement>) => {
        setComment(e.target.value);
    };

    const fetchCommentsAndStarRatings = async () => {
        try {
            const response = await axios.get(`http://13.209.157.148:8080/api/comments/movie/${movieId}`);
            const commentsData = response.data;

            // commentsData가 댓글 객체의 배열이라고 가정합니다.
            setCommentsList(commentsData);
            console.log('댓글목록 가져오기:', commentsData);
        } catch (error) {
            console.error('댓글을 가져오는 중 오류 발생:', error);
        }
    };

    const handleCommentSubmit = async () => {
        if (selectedRating !== null) {
            const now = new Date();
            const timestamp = `${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;
            const newComment: Comment = {
                score: selectedRating,
                content: `${comment} - ${timestamp}`,
            };

            // memberId가 사용 가능한지 확인합니다 (즉, 사용자가 로그인한 경우)
            if (memberId) {
                try {
                    const jwtToken = localStorage.getItem('jwt');
                    const response = await axios.post(
                        `http://13.209.157.148:8080/api/comments?memberId=${memberId}&movieId=${movieId}`,
                        {
                            content: newComment.content, // 댓글 내용
                            score: newComment.score, // 별점
                        },
                        {
                            headers: {
                                Authorization: `${jwtToken}`,
                            },
                        },
                    );
                    console.log('POST 요청 응답:', response.data);
                    location.reload();
                    // 성공적인 댓글 제출 후 필요한 다른 작업을 수행합니다.

                    // 댓글 입력과 선택된 별점을 지웁니다.
                    setComment('');
                    setSelectedRating(null);
                } catch (error) {
                    console.error('POST 요청 오류:', error);
                }
            } else {
                // 사용자가 로그인하지 않았을 때 처리해야 할 경우
                alert('댓글을 제출하려면 로그인이 필요합니다.');
                // 사용자에게 로그인 페이지로 리디렉션하거나 다른 필요한 작업을 수행할 수 있습니다.
            }
        }
    };

    useEffect(() => {
        // JWT 토큰을 얻어온다고 가정

        // 컴포넌트가 마운트될 때 서버에서 memberId를 가져오도록 합니다.
        const jwtToken = localStorage.getItem('jwt');

        axios
            .get<{ data: { memberId: string } }>('http://13.209.157.148:8080/users/get', {
                headers: {
                    Authorization: `${jwtToken}`,
                },
            })
            .then((response) => {
                // memberId가 응답 데이터에 있는 것으로 가정하고 가져옵니다.
                const memberIdFromServer = response.data.data.memberId;
                setMemberId(memberIdFromServer);
                console.log('멤버스 ID:', memberIdFromServer);
            })
            .catch((error) => {
                // 요청 중 발생한 모든 오류를 처리합니다.
                console.error('memberId 가져오기 오류:', error);
            });
        fetchCommentsAndStarRatings();
    }, []);

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
                    <TextList>
                        {commentsList.map((commentData, index) => (
                            <TextListli key={index}>
                                {commentData.content} - 별점: {commentData.score}
                            </TextListli>
                        ))}
                    </TextList>
                </TextBox3>
            </Textform>
        </>
    );
};

export default SubComment;
