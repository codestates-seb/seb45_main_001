package com.sundayCinema.sundayCinema.movie.dto.mainPage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainPageDto<T> {
    private T boxofficeList;
}
