import { styled, css, keyframes } from 'styled-components';
import { useState, useEffect, useRef } from 'react';
import LoginPage from './auth/LoginPage';
import SignupPage from './auth/Signup';
import { useDispatch, useSelector } from 'react-redux';
import { DataState, updateName, updateLogin, updateMail, updateMagnifier, updateSearchData, updateQuery } from '../slice/authslice';
import type { AppDispatch } from '../store/authstore';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { apiCall } from '../api/authapi';

// const HeaderStyle = styled.header`
//     /* width: 100%;
//     height: 56px;
//     display: flex;
//     align-items: center;
//     z-index: 1001;
//     background-color: #1d1d1d;
//     background-color: transparent;
//     백그라운드는 나중에 투명으로 바꿀 것
//     position: fixed; */
//     position: fixed;
//     top: 0;
//     width: 100%;
//     height: 56px;
//     z-index: 1001;
//     padding: 20px;
//     display: flex;
//     align-items: center;
//     transition: all 0.5s;
//     font-size: 1rem;
// `;

const HeaderStyle = styled.header<{ $scrolled: boolean }>`
    position: fixed;
    top: 0;
    width: 100%;
    height: 56px;
    z-index: 1001;
    padding: 20px;
    display: flex;
    align-items: center;
    transition: all 0.5s;
    font-size: 1rem;
    background-color: ${(props) => (props.$scrolled ? '#1d1d1d' : 'transparent')};
`;

const Headerwrap = styled.div`
    width: 53%;
    max-width: 100%;
    min-width: 500px;
    height: 100%;
    display: flex;
    margin: 0 auto;
    align-items: center;
`;

const FlexCenter = styled.div`
    align-items: center;
    text-align: center;
    display: flex;
`;

const FlexCentercss = css`
    align-items: center;
    text-align: center;
    display: flex;
`;

const LogoStyle = styled(FlexCenter)`
    height: 100%;
    padding-left: 0px;
    padding-right: 6px;
    color: #d6a701;
    font-weight: 600;
    min-width: 80px;
    min-height: 24px;
`;

const CountryStyle = styled(FlexCenter)`
    margin-left: 10px;
    margin-right: 20px;
    min-width: 65px;
    min-height: 24px;
    gap: 6px;

    @media (max-width: 680px) {
        min-width: 55px;
    }
`;

const DomesticStyle = styled(FlexCenter)`
    color: white;
`;

const OverseasStyle = styled(FlexCenter)`
    color: white;
`;

const GeneralStyle = styled(FlexCenter)`
    color: white;
`;

const LoginbuttonStyle = styled.div`
    color: white;
    cursor: pointer;
`;

const SignupbuttonStyle = styled.div`
    color: white;
    cursor: pointer;
`;

// const SearchStyle = styled(FlexCenter)``;

const MagnifierStyle = styled.div`
    width: 21px;
    height: 21px;
    /* border: 1px solid gray; */
    cursor: pointer;
`;

const LogSignStyle = styled.nav`
    ${FlexCentercss}
    margin-left: auto;
    height: 100%;
    min-width: 110px;
    min-height: 24px;
    gap: 6px;
`;

const Templink = styled.div`
    display: none;
    position: absolute;
    top: 24px;
    width: 92px;
    height: 110px;
    /* border: 1px solid black; */
    flex-direction: column;
    color: black;
    background-color: white;
    border-radius: 24px;
    padding-top: 20px;
    gap: 12px;
`;

const TempStyle = styled.div`
    position: relative;
    color: white;
    cursor: pointer;

    &:hover {
        > ${Templink} {
            display: flex;
        }
    }
    @media (max-width: 680px) {
        display: none;
    }
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

const fadeIn = keyframes`
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
`;

const fadeOut = keyframes`
    from {
        opacity: 1;
    }
    to {
        opacity: 0;
    }
`;

const SearchbarStyle = styled.form<{ $isOpen: boolean, $isFinished: boolean }>`
    ${FlexCentercss}
    flex-grow: 1;
    margin-left: 10px;
    margin-right: 10px;
    
    opacity: ${({ $isOpen }) => ($isOpen ? '1' : '0')};
    display: ${({ $isOpen, $isFinished }) => ($isOpen || !$isFinished ? 'flex' : 'none')};

    ${({ $isOpen }) => $isOpen ? css`
        animation: ${fadeIn} 0.5s forwards;
    ` : css`
        animation: ${fadeOut} 0.5s forwards;
    `}

    
`;

const Relative = styled.div`
    ${FlexCentercss}
    width: 100%;
    position: relative;
`;

function Header() {
    const dispatch: AppDispatch = useDispatch();
    const navigate = useNavigate();
    const isLogin = useSelector((state: { data: DataState }) => state.data.isLogin);
    const globalName = useSelector((state: { data: DataState }) => state.data.globalname);
    const searchData = useSelector((state: { data: DataState }) => state.data.searchData);
    const query = useSelector((state: { data: DataState }) => state.data.query);
    const location = useLocation();

    const token = localStorage.getItem('jwt');

    useEffect(() => {
        if (token) {
            if (!isLogin && token) {
                console.log('자동로그인 시도');
                apiCall({
                    method: 'GET',
                    url: 'users/get', // user info endpoint
                    headers: { Authorization: `${localStorage.getItem('jwt')}` },
                })
                    .then((res) => {
                        dispatch(updateLogin(true));
                        sessionStorage.setItem('userName', res.data.data.userName);
                        sessionStorage.setItem('email', res.data.data.email);
                        sessionStorage.setItem('memberId', res.data.data.memberId);
                        dispatch(updateName(res.data.data.userName));
                        dispatch(updateMail(res.data.data.email));
                        console.log('로그인 및 유저 정보 가져오기 성공');
                    })
                    .catch((error) => {
                        if (error.response && error.response.status === 400) {
                            console.error('로그인 실패 400에러');
                        } else if (error.message && error.message.includes('Network Error')) {
                            console.error('서버 안열림');
                        } else {
                            console.error('로그인 에러', error);
                        }
                    });
            }
        } else {
            if (isLogin) {
                console.log('헤더 로그아웃 강제 작동');
                dispatch(updateLogin(false));
                sessionStorage.removeItem('userName');
                sessionStorage.removeItem('email');
                sessionStorage.removeItem('memberId');
                localStorage.removeItem('jwt');
                localStorage.removeItem('jwtrefresh');
            }
        }
    }, []);

    function onClickLogout() {
        try {
            dispatch(updateLogin(false));
            sessionStorage.removeItem('memberId');
            sessionStorage.removeItem('jwt');
            sessionStorage.removeItem('userName');
            sessionStorage.removeItem('email');
            sessionStorage.removeItem('password');
            localStorage.removeItem('jwtrefresh');
            localStorage.removeItem('jwt');
            navigate('/');
            console.log('로그아웃 성공');
        } catch (error) {
            console.log('로그아웃 실패', error);
        }
    }

    const [isLoginModal, setLoginModal] = useState<boolean>(false);
    const [isSignupModal, setSignupModal] = useState<boolean>(false);

    function onClickToggleModal() {
        setLoginModal(!isLoginModal);
        if (!isLoginModal) {
            console.log('로그인 모달 열림!');
        } else {
            console.log('로그인 모달 닫힘!');
        }
    }

    function onClickToggleSignupModal() {
        setSignupModal(!isSignupModal);
        if (!isSignupModal) {
            console.log('회원가입 모달 열림!');
        } else {
            console.log('회원가입 모달 닫힘!');
        }
    }

    interface Movie {
        movieNm: string;
        movieId: string;
        genre: string;
        posterUrl: string;
        [key: string]: any;  // 만약 추가적인 프로퍼티들이 있을 수 있다면 이 부분을 유지하시고, 그렇지 않다면 제거하셔도 됩니다.
    }

    useEffect(() => {
        apiCall(
            {
                method: 'GET',
                url: 'http://13.209.157.148:8080/top10',
            },
            false,
        )
            .then((response) => {
                console.log('검색 리스폰스', response);

                const genreMovieList =
                    response.data.genreMovieList?.map((item: Movie) => ({
                        movieNm: item.movieNm,
                        movieId: item.movieId,
                        genre: item.genre,
                        posterUrl: item.posterUrl
                    })) || [];

                const uniqueList = genreMovieList.reduce((acc: Movie, cur: Movie) => {
                    const isDuplicate = acc.some((item: Movie) => item.movieId === cur.movieId);
                    if (!isDuplicate) {
                        acc.push(cur);
                    }
                    return acc;
                }, []);
                const sortedList = uniqueList.sort((a: Movie, b: Movie) => a.movieNm.localeCompare(b.movieNm));
                dispatch(updateSearchData(sortedList));
            })
            .catch((error) => {
                console.error('응답실패', error);
            });
    }, []);

    const [scrolled, setScrolled] = useState(false);

    const handleScroll = () => {
        const offset = window.scrollY;
        if (offset > 20) {
            setScrolled(true);
        } else {
            setScrolled(false);
        }
    };

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, []);
    const [isAnimationFinished, setIsAnimationFinished] = useState(false);
    const isMagnifierClicked = useSelector((state: { data: DataState }) => state.data.isMagnifierClicked);

    function handleMagnifierClick() {
        dispatch(updateMagnifier(!isMagnifierClicked));
        setIsAnimationFinished(false);
        if (!isMagnifierClicked) {
            console.log('검색바 열림!');
        } else {
            console.log('검색바 닫힘!');
        }
    }

    const filteredData = searchData.filter((item) => {
        if (query.trim() === '') {
            return false;
        }
        return item.movieNm.toLowerCase().includes(query.toLowerCase());
    });

    useEffect(() => {
        if (location.pathname !== "/search" && isMagnifierClicked) {
            dispatch(updateMagnifier(false));
            console.log('검색바 닫힘!');
        }
    }, [location, isMagnifierClicked, dispatch]);

    

    const onAnimationEnd = () => {
        if (!isMagnifierClicked) {
            setIsAnimationFinished(true);
        }
    };

    return (
        <>
            <HeaderStyle $scrolled={scrolled}>
                <Headerwrap>
                    <LogoStyle>
                        <Link to="/">
                            <div>일요시네마</div>
                        </Link>
                    </LogoStyle>
                    <CountryStyle>
                        <DomesticStyle>
                            <Link to="/korea">국내</Link>
                        </DomesticStyle>
                        <OverseasStyle>
                            <Link to="/foreign">해외</Link>
                        </OverseasStyle>
                        {/* <TempStyle>
                            임시링크
                            <Templink>
                                <Link to="/submain">toSub</Link>
                                <Link to="/mypage">toMypage</Link>
                            </Templink>
                        </TempStyle> */}
                    </CountryStyle>
                    <MagnifierStyle onClick={handleMagnifierClick}>
                        <Link to="/search">
                            <img src="/Magnifier_white.png" alt="" style={{ width: '100%', height: '100%' }} />
                        </Link>
                    </MagnifierStyle>
                    <SearchbarStyle $isOpen={isMagnifierClicked} $isFinished={isAnimationFinished} onAnimationEnd={onAnimationEnd}>
                        <Relative>
                            <SearchinputStyle
                                aria-label=""
                                placeholder="장르, 영화제목 검색... "
                                value={query}
                                onChange={(e) => dispatch(updateQuery(e.target.value))}
                            ></SearchinputStyle>
                        </Relative>
                    </SearchbarStyle>
                    <LogSignStyle>
                        {!isLogin && (
                            <>
                                <LoginbuttonStyle onClick={onClickToggleModal}>로그인</LoginbuttonStyle>
                                <SignupbuttonStyle onClick={onClickToggleSignupModal}>회원가입</SignupbuttonStyle>
                            </>
                        )}
                        {isLogin && (
                            <>
                                <GeneralStyle>
                                    <Link to="/mypage">{globalName}</Link>
                                </GeneralStyle>
                                <LoginbuttonStyle onClick={onClickLogout}>로그아웃</LoginbuttonStyle>
                            </>
                        )}
                    </LogSignStyle>
                </Headerwrap>
            </HeaderStyle>
            {isLoginModal && (
                <LoginPage
                    onClickToggleModal={onClickToggleModal}
                    onClickToggleSignupModal={onClickToggleSignupModal}
                ></LoginPage>
            )}
            {isSignupModal && (
                <SignupPage
                    onClickToggleModal={onClickToggleModal}
                    onClickToggleSignupModal={onClickToggleSignupModal}
                ></SignupPage>
            )}
        </>
    );
}

export default Header;
