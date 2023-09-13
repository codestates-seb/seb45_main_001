package com.sundayCinema.sundayCinema.movie.dto.detailPageDto;

import com.sundayCinema.sundayCinema.movie.dto.mainPageDto.StillCutDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsBasicInfo{

    public String openDt;
    /*해당일자의 박스오피스 순위*/
    public String rank;

    public String movieNm;// "영화명(국문)"
    public String movieNmEn;
    // 영문 제목
    public String audiAcc; // "누적 관객수"
    public String backDrop;
    public String poster;
    public List<StillCutDto> stillCut;
    public String plot;
    public List<String> genre; // 리스트 형태로 수정
    public List<String> nation; // 리스트 형태로 수정
    public String watchGradeNm; // 등급

    //평균 평점
}
