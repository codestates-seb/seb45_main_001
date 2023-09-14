const TMDB_KEY: string = process.env.REACT_APP_MOVIE_DB_API_KEY || "";
const BASE_URL: string = "https://api.themoviedb.org/3";
const BASE_LANG: string = "ko";
const BASE_REGION: string = "KR";

export const getPopularMovies = (): string => {
  return `${BASE_URL}/movie/popular?api_key=${TMDB_KEY}&language=${BASE_LANG}&region=${BASE_REGION}`;
};

// Images SAMPLE
// https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
export const getImageUrl = (path: string, size: number = 400): string => {
  return `https://image.tmdb.org/t/p/w${size}${path}`;
};