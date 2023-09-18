import { styled, css } from 'styled-components';
import { useState, useEffect } from 'react';
import LoginPage from './auth/LoginPage';
import SignupPage from './auth/Signup';
import { useDispatch, useSelector } from 'react-redux';
import { DataState, updateName, updateLogin, updateMail } from '../slice/authslice';
import type { AppDispatch } from '../store/authstore';
import { Link, useNavigate } from 'react-router-dom';
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
    min-width: 130px;
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

const SearchinputStyle = styled.input`
    border: 1px solid gray;
    background-color: white;
    width: 100%;
    max-width: 600px;
    border-radius: 3px;
    padding: 3px;
    padding-left: 6px;
    font-size: 0.875rem;
`;

const SearchfilterStyle = styled.ul`
    position: absolute;
    width: 100%;
    list-style: none;
    top: 29px;
    background-color: white;
    border-radius: 5px;
    border-top-left-radius: 0px;
    border-top-right-radius: 0px;
    text-align: left;
`;

const SearchfilterliStyle = styled.ul`
    width: 100%;
    padding-left: 6px;
    margin-bottom: 3px;
`;

const MagnifierStyle = styled.div`
    width: 21px;
    height: 21px;
    border: 1px solid gray;
    cursor: pointer;
`;

const SearchbarStyle = styled.form<{ $isOpen: boolean }>`
    ${FlexCentercss}
    flex-grow: 1;
    margin-left: 10px;
    margin-right: 10px;
    display: ${({ $isOpen }) => ($isOpen ? 'flex' : 'none')};

    @media (max-width: 680px) {
        position: absolute;
        width: 300px;
        top: 56px;
        left: 150px;
        right: auto;
    }
`;

const LogSignStyle = styled.nav`
    ${FlexCentercss}
    margin-left: auto;
    height: 100%;
    min-width: 110px;
    min-height: 24px;
    gap: 6px;
`;

const Relative = styled.div`
    ${FlexCentercss}
    position: relative;
    flex-grow: 1;
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

// const [isLogin, setIsLogin] = useState<boolean>(false);
//     const [sessionUser, setSessionUser] = useState<any>(null); //받아오는 데이타의 세션유저이름

//     useEffect(() => {
//         axios
//             .get(`${lastUrl}/check-session`)
//             .then((response) => {
//                 if (response.data.isLogin) {
//                     setIsLogin(true);
//                     setSessionUser(response.data.sessionUser);
//                 }
//             })
//             .catch((error) => {
//                 console.error('로그인 상태 확인 중 오류 발생:', error);
//             });
//     }, []);

//     const onClickLogout = () => {
//         // 서버에 로그아웃 요청을 보냅니다.
//         axios
//             .post(`${lastUrl}/api/logout`)
//             .then((response) => {
//                 if (response.data.success) {
//                     setIsLogin(false);
//                     setSessionUser(null);
//                     sessionStorage.removeItem('memberid');
//                     sessionStorage.removeItem('jwt');
//                     sessionStorage.removeItem('membername');
//                     sessionStorage.removeItem('membermail');
//                     navigate('/');
//                     console.log('로그아웃 성공');
//                 }
//             })
//             .catch((error) => {
//                 console.error('로그아웃 실패', error);
//             });
//     };

// const isTokenExpired = (token: string): boolean => {
//     try {
//         const decodedToken: any = JSON.parse(atob(token.split('.')[1]));
//         const currentTime = Date.now() / 1000;
//         return decodedToken.exp && currentTime > decodedToken.exp;
//     } catch (error) {
//         return true;
//     }
// };

// const users = useSelector((state: RootState) => state.data.users);
// const memberId = sessionStorage.getItem('memberid');
// const user = memberId ? users?.[memberId] : undefined;
// const token = sessionStorage.getItem('jwt');

// const [isLogin, setIsLogin] = useState<boolean>(() => {
//     return token ? !isTokenExpired(token) : false;
// });

// console.log('Member ID:', memberId);
// console.log('User Data:', user);

// function onClickLogout() {
//     setIsLogin(false);
//     try {
//         sessionStorage.removeItem('memberid');
//         sessionStorage.removeItem('jwt');
//         sessionStorage.removeItem('membername');
//         sessionStorage.removeItem('membermail');
//         navigate('/');
//         console.log('로그아웃 성공');
//     } catch (error) {
//         console.log('로그아웃 실패', error);
//     }
// }

// const [sessionUser, setSessionUser] = useState<any>(null); //받아오는 데이타의 세션유저이름

// useEffect(() => {
//     apiCall({
//         method: 'GET',
//         url: 'host/check-session', // membership/signin - 우리 엔드포인트 // login - json 엔드포인트 host/signin 우리 새로운 엔드포인트
//     })
//         .then((response) => {
//             if (response.data === true) {
//                 dispatch(updateLogin(true));
//             } else {
//                 dispatch(updateLogin(false));
//             }
//         })
//         .catch((error) => {
//             console.error('로그인 상태 확인 중 오류 발생:', error);
//         });
// }, []);

// function getCookie(name: string): string | null | undefined {
//     const value = `; ${document.cookie}`;
//     const parts = value.split(`; ${name}=`);
//     if (parts.length === 2) return parts.pop()!.split(';').shift();
//     return null;
// }

// useEffect(() => {
//     const sessionId = getCookie('SESSION_COOKIE_NAME');
//     if (sessionId) {
//         console.log('세션아이디 쿠키존재여부', sessionId);
//     }
// }, []);

// const onClickLogout = () => {
//     // 서버에 로그아웃 요청을 보냅니다.
//     axios
//         .post(`${lastUrl}/api/logout`)
//         .then((response) => {
//             if (response.data.success) {
//                 setIsLogin(false);
//                 setSessionUser(null);
//                 sessionStorage.removeItem('memberid');
//                 sessionStorage.removeItem('jwt');
//                 sessionStorage.removeItem('membername');
//                 sessionStorage.removeItem('membermail');
//                 navigate('/');
//                 console.log('로그아웃 성공');
//             }
//         })
//         .catch((error) => {
//             console.error('로그아웃 실패', error);
//         });
// };

// function onClickLogout() {
//     try {
//         const storedUsersJSON = sessionStorage.getItem('users');
//         const storedUsers = storedUsersJSON ? JSON.parse(storedUsersJSON) : [];

//         const existingUser = storedUsers.find((user: any) => user.email === globalmail);

//         if (existingUser) {
//             existingUser.islogin = false;
//             sessionStorage.setItem('users', JSON.stringify(storedUsers));
//         }
//         dispatch(updateLogin(false));
//         sessionStorage.removeItem('memberid');
//         sessionStorage.removeItem('membername');
//         sessionStorage.removeItem('memberemail');
//         sessionStorage.removeItem('memberpassword');
//         // navigate('/');
//         console.log('로그아웃 성공');
//     } catch (error) {
//         console.log('로그아웃 실패', error);
//     }
// }

function Header() {
    const dispatch: AppDispatch = useDispatch();
    const navigate = useNavigate();
    const isLogin = useSelector((state: { data: DataState }) => state.data.isLogin);
    const [isMagnifierClicked, setisMagnifierClicked] = useState<boolean>(false);
    const globalName = useSelector((state: { data: DataState }) => state.data.globalname);

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

    function handleMagnifierClick() {
        setisMagnifierClicked(!isMagnifierClicked);
        if (!isMagnifierClicked) {
            console.log('검색바 열림!');
        } else {
            console.log('검색바 닫힘!');
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

    const [query, setQuery] = useState<string>('');
    const [searchData, setSearchData] = useState<Array<{ movieNm: string; movieId: string }>>([
        { movieNm: '1번영화', movieId: '1' },
        { movieNm: '2번영화', movieId: '2' },
        { movieNm: '3번영화', movieId: '3' },
        { movieNm: '4번영화', movieId: '4' },
        { movieNm: '5번영화', movieId: '5' },
        { movieNm: '6번영화', movieId: '6' },
        { movieNm: '7번영화', movieId: '7' },
        { movieNm: '8번영화', movieId: '8' },
    ]);

    interface MovieItem {
        movieNm: string;
        movieId: string;
        [key: string]: any;
    }

    // 검색기능
    // useEffect(() => {
    //     apiCall(
    //         {
    //             method: 'GET',
    //             url: 'http://13.209.157.148:8080/top10',
    //         },
    //         false,
    //     )
    //         .then((response) => {
    //             console.log('검색 리스폰스', response);
    //             const boxofficeList =
    //                 response.data.boxofficeList?.map((item: any) => ({
    //                     movieNm: item.movieNm,
    //                     movieId: item.movieId,
    //                 })) || [];

    //             const genreMovieList =
    //                 response.data.genreMovieList?.map((item: any) => ({
    //                     movieNm: item.movieNm,
    //                     movieId: item.movieId,
    //                 })) || [];

    //             const combinedList = [...boxofficeList, ...genreMovieList];

    //             const uniqueList = combinedList.reduce((acc: any[], cur: any) => {
    //                 const isDuplicate = acc.some((item: any) => item.movieId === cur.movieId);
    //                 if (!isDuplicate) {
    //                     acc.push(cur);
    //                 }
    //                 return acc;
    //             }, []);
    //             setSearchData(uniqueList);
    //         })
    //         .catch((error) => {
    //             console.error('응답실패', error);
    //         });
    // }, []);

    const filteredData = searchData.filter((item) => {
        if (query.trim() === '') {
            return false;
        }
        return item.movieNm.toLowerCase().includes(query.toLowerCase());
    });

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
                        <img src="/Magnifier_white.png" alt="" style={{ width: '100%', height: '100%' }} />
                    </MagnifierStyle>
                    <SearchbarStyle $isOpen={isMagnifierClicked}>
                        <Relative>
                            <SearchinputStyle
                                aria-label=""
                                placeholder="검색..."
                                value={query}
                                onChange={(e) => setQuery(e.target.value)}
                            ></SearchinputStyle>
                            <SearchfilterStyle>
                                {filteredData.map((item: { movieNm: string; movieId: string }, index: number) => (
                                    <SearchfilterliStyle key={index}>
                                        <Link to={`/submain/${item.movieId}`}>{item.movieNm}</Link>
                                    </SearchfilterliStyle>
                                ))}
                            </SearchfilterStyle>
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
                                    Hello,
                                    <Link to="/mypage">{globalName}!</Link>
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
