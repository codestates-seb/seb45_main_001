import { styled, css, keyframes } from 'styled-components';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { apiCall } from '../api/authapi';
import { useDispatch, useSelector } from 'react-redux';
import { DataState, updateMagnifier, updateQuery } from '../slice/authslice';
import type { AppDispatch } from '../store/authstore';

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    display: flex;
`;

const slideDown = keyframes`
    from {
        opacity: 0;
        transform: translateY(-20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
`;

const BodySearch = styled.div`
    width: 100%;
    min-height: 98vh;
    background-color: #1d1d1d;
    ${FlexCentercss}
    align-items: flex-start;
`;

const Wrapmypage = styled.div`
    width: 1052px;
    height: 100%;
    padding-top: 56px;
    margin: 0 auto;
`;

const SearchfilterStyle = styled.ul`
    list-style: none;
    padding: 0;
    margin: 0 auto;
    width: 80%;

    & > li + li {
        margin-top: 10px;
    }

    a {
        animation: ${slideDown} 0.5s ease-out forwards;
        text-align: left;
        text-decoration: none;
        color: white;
        font-size: 16px;
        line-height: 1.5;
        display: block;
        padding: 5px 10px;
        border-radius: 5px;

        &:nth-child(n) {
            animation-delay: calc(0.1s * n);
        }

        &:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }
    }
`;

const MoviePoster = styled.div.attrs<{ $y: number; $x: number; $posterUrl: string }>((props) => ({
    style: {
        backgroundImage: `url(${props.$posterUrl})`,
        top: `${props.$y + 15}px`,
        left: `${props.$x + 15}px`,
    },
}))`
    width: 180px;
    height: 270px;

    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;
    background-color: white;

    z-index: 300;

    visibility: hidden;

    position: fixed; // 마우스 위치에 따라 이동하기 위해 fixed로 설정합니다

    pointer-events: none;
`;

const SearchfilterliStyle = styled.li`
    width: 100%;
    padding-left: 6px;
    margin-bottom: 3px;
    position: relative;

    &:hover ${MoviePoster} {
        visibility: visible;
    }
`;

const Moviewrap = styled.li`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
`;

const Moviename = styled.li``;

const Moviegenre = styled.li`
    min-width: 80px;
    text-align: center;
`;

export function Searchcomponent() {
    const dispatch: AppDispatch = useDispatch();
    const isMagnifierClicked = useSelector((state: { data: DataState }) => state.data.isMagnifierClicked);
    const searchData = useSelector((state: { data: DataState }) => state.data.searchData);
    const query = useSelector((state: { data: DataState }) => state.data.query);
    const [mousePosition, setMousePosition] = useState<{ x: number; y: number }>({ x: 0, y: 0 });

    const handleMouseMove = (e: React.MouseEvent) => {
        setMousePosition({ x: e.clientX, y: e.clientY });
    };

    const filteredData = searchData.filter((item) => {
        if (query.trim() === '') {
            return false;
        }
        return (
            item.movieNm.toLowerCase().includes(query.toLowerCase()) ||
            item.genre.toLowerCase() === query.toLowerCase()
        );
    });

    return (
        <>
            <BodySearch>
                <Wrapmypage>
                    <SearchfilterStyle>
                        {filteredData.map(
                            (
                                item: { movieNm: string; movieId: string; genre: string; posterUrl: string },
                                index: number,
                            ) => (
                                <SearchfilterliStyle key={index} onMouseMove={handleMouseMove}>
                                    <MoviePoster
                                        $x={mousePosition.x}
                                        $y={mousePosition.y}
                                        $posterUrl={item.posterUrl}
                                    />
                                    <Link
                                        to={`/submain/${item.movieId}`}
                                        onClick={() => {
                                            dispatch(updateMagnifier(false));
                                            dispatch(updateQuery(''));
                                        }}
                                    >
                                        <Moviewrap>
                                            <Moviename>{item.movieNm}</Moviename>
                                            <Moviegenre>{item.genre}</Moviegenre>
                                        </Moviewrap>
                                    </Link>
                                </SearchfilterliStyle>
                            ),
                        )}
                    </SearchfilterStyle>
                </Wrapmypage>
            </BodySearch>
        </>
    );
}

export default Searchcomponent;
