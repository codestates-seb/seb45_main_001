package com.sundayCinema.sundayCinema.movie.dto.detailPage;

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

    public String audiAcc; // "누적 관객수"

    public List<String> poster;

    public String genre;
    public String nation;
    public String watchGradeNm;

    //평균 평점
}
