import axios from "axios";

const instance = axios.create({
  
  baseURL: "https://api.themoviedb.org/3",
  // `${REACT_APP_API_URL}/top10`
  params: {
    api_key : process.env.REACT_APP_MOVIE_DB_API_KEY,
    language: "ko-KR",
  },
});

export default instance;