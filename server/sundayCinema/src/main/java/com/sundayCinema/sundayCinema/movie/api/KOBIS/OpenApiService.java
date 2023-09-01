package com.sundayCinema.sundayCinema.movie.api.KOBIS;

import com.sundayCinema.sundayCinema.movie.entity.BoxOfficeMovie;
import com.sundayCinema.sundayCinema.movie.repository.BoxOfficeMovieRepository;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Slf4j
@Service
public class OpenApiService {
    private final String API_KEY = "dd93d4428c56b7a47d27bf57dc81e481"; // 발급받은 API 키 값을 입력해주세요

    private final BoxOfficeMovieRepository boxOfficeMovieRepository;

    public OpenApiService(BoxOfficeMovieRepository boxOfficeMovieRepository) {
        this.boxOfficeMovieRepository = boxOfficeMovieRepository;
    }

    public void dailyBoxOffice(){

        String dailyResponse = "";

        //전날 박스오피스 조회 ( 오늘 날짜꺼는 안나옴.. )
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        String targetDt =  time.format(DateTimeFormatter.ofPattern("yyyMMdd"));

        //ROW 개수
        String itemPerPage = "10";

        //다양성영화(Y)/상업영화(N)/전체(default)
        String multiMovieYn = "";

        //한국영화(K)/외국영화(F)/전체(default)
        String repNationCd = "";

        //상영지역별 코드/전체(default)
        String wideAreaCd = "";
        String[] movieTypeCd = new String[0];

        try {
            // KOBIS 오픈 API Rest Client를 통해 호출
            KobisOpenAPIRestService service = new KobisOpenAPIRestService(API_KEY);

            // 일일 박스오피스 서비스 호출 (boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
            dailyResponse = service.getMovieList(true,  "1", "1", "오펜하이머",  "","",  "","", "", "",movieTypeCd);

            //JSON Parser 객체 생성
            JSONParser jsonParser = new JSONParser();

            //Parser로 문자열 데이터를 객체로 변환
            Object obj = jsonParser.parse(dailyResponse);

            //파싱한 obj를 JSONObject 객체로 변환
            JSONObject jsonObject = (JSONObject) obj;

            //차근차근 parsing하기
            JSONObject parse_boxOfficeResult = (JSONObject) jsonObject.get("movieListResult");
            JSONArray dailyBoxOfficeList = (JSONArray) parse_boxOfficeResult.get("movieList");
            log.info(" movieList" + String.valueOf(dailyBoxOfficeList));

            // 1위 10위까지 배열을 반복해서, 개별 영화별로 세부 사항을 db에 저장
            for (int i = 0; i < dailyBoxOfficeList.size(); i++) {
                JSONObject movie = (JSONObject) dailyBoxOfficeList.get(i);
                BoxOfficeMovie boxOfficeMovie = new BoxOfficeMovie();
                boxOfficeMovie.setMovieCd((String) movie.get("movieCd"));
                boxOfficeMovie.setMovieNm((String) movie.get("movieNm"));

                log.info("BoxOfficeMovie " +   String.valueOf(boxOfficeMovie));
            }

//            ObjectMapper objectMapper = new ObjectMapper();
//            JSONArray parse_dailyBoxOfficeList = (JSONArray) parse_boxOfficeResult.get("dailyBoxOfficeList");
//            for(int i=0; i<parse_dailyBoxOfficeList.size(); i++){
//                JSONObject BoxOffice = (JSONObject) parse_dailyBoxOfficeList.get(i);
//                //JSON object -> Java Object(Entity) 변환
//                BoxOfficeMovie boxOfficeMovie = objectMapper.readValue(BoxOffice.toString(), BoxOfficeMovie.class);
//                //DB저장
//                boxOfficeMovie.setTargetDt(targetDt);
//                boxOfficeMovie.setBoxofficeType(boxofficeType);
//                boxOfficeMovie.setShowRange(showRange);
//                boxOfficeMovieRepository.save(boxOfficeMovie);
            } catch (OpenAPIFault ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


}}
