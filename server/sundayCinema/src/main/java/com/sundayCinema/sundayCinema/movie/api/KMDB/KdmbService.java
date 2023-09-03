package com.sundayCinema.sundayCinema.movie.api.KMDB;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KdmbService {

    String serviceKey = "8Z3A9MG71B4A155KB5M0"; // 서비스키를 직접 입력
    String kdmbUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp";

    public KdmbResponse generateKdmb(String title) throws IOException {
        String apiUrl = buildApiUrl(title);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("ServiceKey", serviceKey); // 인증키 추가

        System.out.println("Response code: " + conn.getResponseCode());


        String apiResponse = readApiResponse(conn);
        conn.disconnect();
        log.info("movieData : "+ parsingKdmb(apiResponse).getData().get(0).getResult().get(0).getPlots());
        return parsingKdmb(apiResponse);
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

    private String buildApiUrl(String title) throws UnsupportedEncodingException {
        UrlParameterBuilder urlParameterBuilder = new UrlParameterBuilder();
        // URL 파라미터 추가
        urlParameterBuilder.addParameter("ServiceKey", serviceKey);
        urlParameterBuilder.addParameter("collection", "kmdb_new2");
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