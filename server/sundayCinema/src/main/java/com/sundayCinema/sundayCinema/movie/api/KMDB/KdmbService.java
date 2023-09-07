package com.sundayCinema.sundayCinema.movie.api.KMDB;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.movie.api.youtubeAPI.YoutubeService;
import com.sundayCinema.sundayCinema.movie.entity.Movie;
import com.sundayCinema.sundayCinema.movie.entity.Plots;
import com.sundayCinema.sundayCinema.movie.entity.Poster;
import com.sundayCinema.sundayCinema.movie.entity.StillCut;
import com.sundayCinema.sundayCinema.movie.entity.Trailer;
import com.sundayCinema.sundayCinema.movie.repository.MovieRepository;
import com.sundayCinema.sundayCinema.movie.repository.PlotRepository;
import com.sundayCinema.sundayCinema.movie.repository.PosterRepository;
import com.sundayCinema.sundayCinema.movie.repository.StillCutRepository;
import com.sundayCinema.sundayCinema.movie.repository.TrailerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KdmbService {
    @Value("${KDMB_API_KEY}")
    private String KdmbApiKey; // 서비스키를 직접 입력
    private String kdmbUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp";

    private final PosterRepository posterRepository;
    private final StillCutRepository stillCutRepository;
    private final TrailerRepository trailerRepository;
    private final PlotRepository plotRepository;
    private final MovieRepository movieRepository;

    private final YoutubeService youtubeService;

    public KdmbService(PosterRepository posterRepository, StillCutRepository stillCutRepository,
                       TrailerRepository trailerRepository, PlotRepository plotRepository,
                       MovieRepository movieRepository, YoutubeService youtubeService) {
        this.posterRepository = posterRepository;
        this.stillCutRepository = stillCutRepository;
        this.trailerRepository = trailerRepository;
        this.plotRepository = plotRepository;
        this.movieRepository = movieRepository;
        this.youtubeService = youtubeService;
    }

    public void generateKdmb(String movieCd, String movieNm, String openDt) throws IOException, GeneralSecurityException {
        if(verifyExistMovie(movieCd)){
            // true일 때 아무 작업도 수행하지 않음
        }
        else {
            String apiUrl = buildApiUrl(openDt, movieNm);
            URL url = new URL(apiUrl);
            log.info("url : " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("ServiceKey", KdmbApiKey); // 인증키 추가

            System.out.println("kdmb Response code: " + conn.getResponseCode());

            String apiResponse = readApiResponse(conn);
            conn.disconnect();
            KdmbResponse kdmbResponse = parsingKdmb(apiResponse);
            log.info("kdmb : " +kdmbResponse);
            saveKdmb(kdmbResponse, movieCd);
        }
    }
    public void saveKdmb(KdmbResponse kdmbResponse, String movieCd) throws GeneralSecurityException, IOException {
        savePoster(kdmbResponse,movieCd);
        savePlot(kdmbResponse,movieCd);
        saveStill(kdmbResponse,movieCd);
        saveTrailer(kdmbResponse,movieCd);
    }
    public void savePoster(KdmbResponse kdmbResponse, String movieCd) {
        String posterUrls = kdmbResponse.getData().get(0).getResult().get(0).getPosters();

        String[] posterArray = posterUrls.split("\\|");
        for (int i = 0; i < posterArray.length; i++) {
            Poster poster = new Poster();
            log.info("poster : " + poster.getPoster_image_url());
            if (posterRepository.findMaxPosterId() == null) {
                poster.setPosterId(0);
            } else {
                poster.setPosterId(posterRepository.findMaxPosterId() + 1);
            }

            String posterImageUrl = posterArray[i];

            // 포스터 이미지 URL이 비어있을 경우 기본 URL 또는 다른 처리를 할 수 있습니다.
            if (posterImageUrl == null || posterImageUrl.isEmpty()) {
                posterImageUrl = "기본 포스터 이미지 URL"; // 기본 URL 또는 다른 처리 추가
            }

            poster.setPoster_image_url(posterImageUrl);
            poster.setMovie(movieRepository.findByMovieCd(movieCd));
            posterRepository.save(poster);
        }
    }
    public void saveStill(KdmbResponse kdmbResponse, String movieCd) {
        String stillUrls = kdmbResponse.getData().get(0).getResult().get(0).getStlls();

        String[] stillArray = stillUrls.split("\\|");
        for (int i = 0; i < stillArray.length; i++) {
            StillCut stillCut = new StillCut();
            if (stillCutRepository.findMaxStillCutId() == null) {
                stillCut.setStillCutId(0);
            } else {
                stillCut.setStillCutId(stillCutRepository.findMaxStillCutId() + 1);
            }

            String stillCutUrl = stillArray[i];

            // 스틸컷 URL이 비어있을 경우 기본 URL 또는 다른 처리를 할 수 있습니다.
            if (stillCutUrl == null || stillCutUrl.isEmpty()) {
                stillCutUrl = "기본 스틸컷 URL"; // 기본 URL 또는 다른 처리 추가
            }

            stillCut.setStillCut_url(stillCutUrl);
            stillCut.setMovie(movieRepository.findByMovieCd(movieCd));
            stillCutRepository.save(stillCut);
        }
    }
    public void saveTrailer(KdmbResponse kdmbResponse, String movieCd) throws GeneralSecurityException, IOException {
        List<KdmbResponse.Vod.VodInfo> vods = kdmbResponse.getData().get(0).getResult().get(0).getVods().getVod();
        Movie findMovie = movieRepository.findByMovieCd(movieCd);
        for (int i = 0; i < vods.size(); i++) {
            String vodUrl = vods.get(i).getVodUrl();
            String vodClass = vods.get(i).getVodClass();
            Trailer trailer = new Trailer();
            if (trailerRepository.findMaxTrailerId() == null) {
                trailer.setTrailerId(0);
            } else {
                trailer.setTrailerId(trailerRepository.findMaxTrailerId() + 1);
            }

            // 트레일러 URL이 비어있을 경우 기본 URL 또는 다른 처리를 할 수 있습니다.
            if (vodUrl == null || vodUrl.isEmpty()) {
                String movieName= findMovie.getMovieNm();
                String searchTrailer = youtubeService.extractYoutube(youtubeService.searchYoutube(movieName, "메인 예고편"));

                String[] splitResults = searchTrailer.split(",");
                for (String result : splitResults) {
                    String[] splitResult = result.split("\\|");
                    String videoId = splitResult[0];
                    if(trailerRepository.findMaxTrailerId() == null){
                        trailer.setTrailerId(0);
                    }
                    else {
                        trailer.setTrailerId(trailerRepository.findMaxTrailerId() + 1);
                    }
                    trailer.setMovie(findMovie);
                    trailer.setTrailer_url("https://www.youtube.com/watch?v="+videoId);
                    trailerRepository.save(trailer);
                }
            }

            trailer.setVodClass(vodClass);
            trailer.setTrailer_url(vodUrl);
            trailer.setMovie(findMovie);
            trailerRepository.save(trailer);
        }
    }
    public void savePlot(KdmbResponse kdmbResponse, String movieCd){
        String plotText = kdmbResponse.getData().get(0).getResult().get(0).getPlots().getPlot().get(0).getPlotText();
        // 플롯 텍스트가 비어있는지 확인
        if (plotText == null || plotText.isEmpty()) {
            plotText = "플롯 텍스트가 없습니다."; // 플롯 텍스트가 없을 경우 기본 메시지 설정
        }
        Plots plots= new Plots();
        if (plotRepository.findMaxPlotId() == null) {
            plots.setPlotId(0);
        } else {
            plots.setPlotId(plotRepository.findMaxPlotId() + 1);
        }
        plots.setPlotText(plotText);
        plots.setMovie(movieRepository.findByMovieCd(movieCd));
        plotRepository.save(plots);
    }
    public boolean verifyExistMovie(String movieCd){
        Movie findMovie = movieRepository.findByMovieCd(movieCd);
        if(posterRepository.findByMovie(findMovie).isEmpty()) return false;
        else return true;
    }
    public KdmbResponse parsingKdmb(String response) throws JsonProcessingException {
        String jsonString = response;

        ObjectMapper objectMapper = new ObjectMapper();
        KdmbResponse movieData = objectMapper.readValue(jsonString, KdmbResponse.class);

        return movieData;
    }


    private String readApiResponse(HttpURLConnection conn) throws IOException {
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        return sb.toString();
    }

    private String buildApiUrl(String openDt ,String title) throws UnsupportedEncodingException {
        UrlParameterBuilder urlParameterBuilder = new UrlParameterBuilder();
        // URL 파라미터 추가
        urlParameterBuilder.addParameter("ServiceKey", KdmbApiKey);
        urlParameterBuilder.addParameter("collection", "kmdb_new2");
        if (openDt != null && !openDt.isEmpty()) {
            urlParameterBuilder.addParameter("releaseDts", openDt);
        }
        urlParameterBuilder.addParameter("title", title);

        return kdmbUrl + urlParameterBuilder.build();
    }

    public class UrlParameterBuilder {
        private Map<String, String> parameters = new HashMap<>();

        public void addParameter(String key, String value) {
            parameters.put(key, value);
        }

        public String build() throws UnsupportedEncodingException {
            StringBuilder urlBuilder = new StringBuilder();
            boolean isFirstParameter = true;

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (isFirstParameter) {
                    urlBuilder.append("?");
                    isFirstParameter = false;
                } else {
                    urlBuilder.append("&");
                }
                urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                urlBuilder.append("=");
                urlBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return urlBuilder.toString();
        }
    }
}