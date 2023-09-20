import { styled, css } from 'styled-components';
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

const BodySearch = styled.div`
    width: 100%;
    height: 98vh;
    background-color: #1d1d1d;
    ${FlexCentercss}
`;

const SearchinputStyle = styled.input`
    border: 1px solid gray;
    background-color: white;
    width: 100%;
    max-width: 600px;
    border-radius: 3px;
    padding: 3px;
    padding-left: 6px;
    font-size: 0.875rem;
    box-sizing: border-box;
`;

const SearchfilterStyle = styled.ul`
    position: absolute;
    width: 100%;
    max-width: 600px;
    list-style: none;
    top: 29px;
    background-color: white;
    border-radius: 5px;
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    text-align: left;
    box-sizing: border-box;
`;

const SearchfilterliStyle = styled.ul`
    width: 100%;
    padding-left: 6px;
    margin-bottom: 3px;
`;

const SearchbarStyle = styled.form<{ $isOpen: boolean }>`
    ${FlexCentercss}
    flex-grow: 1;
    margin-left: 10px;
    margin-right: 10px;
    display: ${({ $isOpen }) => ($isOpen ? 'flex' : 'none')};

    @media (max-width: 460px) {
        position: absolute;
        width: 300px;
        top: 56px;
        left: auto;
        right: auto;
    }
`;

const Relative = styled.div`
    ${FlexCentercss}
    width: 100%;
    position: relative;
`;

export function Searchcomponent() {
    const dispatch: AppDispatch = useDispatch();
    const isMagnifierClicked = useSelector((state: { data: DataState }) => state.data.isMagnifierClicked);
    const searchData = useSelector((state: { data: DataState }) => state.data.searchData);
    const query = useSelector((state: { data: DataState }) => state.data.query);

    interface MovieItem {
        movieNm: string;
        movieId: string;
        [key: string]: any;
    }

    const filteredData = searchData.filter((item) => {
        if (query.trim() === '') {
            return false;
        }
        return item.movieNm.toLowerCase().includes(query.toLowerCase());
    });

    return (
        <>
            <BodySearch>
                <SearchfilterStyle>
                    {filteredData.map((item: { movieNm: string; movieId: string }, index: number) => (
                        <SearchfilterliStyle key={index}>
                            <Link
                                to={`/submain/${item.movieId}`}
                                onClick={() => {
                                    dispatch(updateMagnifier(false));
                                    dispatch(updateQuery(''));
                                }}
                            >
                                {item.movieNm}
                            </Link>
                        </SearchfilterliStyle>
                    ))}
                </SearchfilterStyle>
            </BodySearch>
        </>
    );
}

export default Searchcomponent;
