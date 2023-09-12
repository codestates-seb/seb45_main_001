package com.sundayCinema.sundayCinema.movie.api;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageDownloadService {

    public void downloadImage(String imageUrl, String savePath) throws IOException {
        // RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // 이미지 다운로드 설정
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        factory.setBufferRequestBody(false);

        // 이미지 다운로드 요청
        ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
        byte[] imageBytes = response.getBody();

        // 이미지 저장
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(imageBytes);
        }
    }
}



