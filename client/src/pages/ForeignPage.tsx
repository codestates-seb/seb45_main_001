import React from 'react';
import ForeignTopRow from '../components/Row/ForeignTopRow';
import ForeignGenreRow from '../components/Row/ForeignGenreRow';
import FoBanner from '../components/Banner/FoBanner';

const ForeignPage = () => {
    return (
        <>
            <FoBanner />
            <ForeignTopRow title="Top 10 Movies" id="top10" isLargeRow={true} />
            <ForeignGenreRow title="애니메이션" id="animation" genre="애니메이션" isLargeRow={true} />
            <ForeignGenreRow title="액션" id="action" genre="액션" isLargeRow={true} />
            <ForeignGenreRow title="판타지" id="fantasy" genre="판타지" isLargeRow={true} />
            <ForeignGenreRow title="로맨스" id="romance" genre="멜로/로맨스" isLargeRow={true} />
            <ForeignGenreRow title="공포" id="scary" genre="공포(호러)" isLargeRow={true} />
        </>
    );
};

export default ForeignPage;
