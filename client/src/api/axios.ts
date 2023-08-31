import axios from "axios";

const instance = axios.create({
  
  baseURL: "https://api.themoviedb.org/3",
  // process.env.REACT_APP_KMDB_API
  params: {
    api_key : process.env.REACT_APP_MOVIE_DB_API_KEY,
    language: "ko-KR",
  },
});

export default instance;