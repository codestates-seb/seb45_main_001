package com.sundayCinema.sundayCinema.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPageDto<T> {
    private T detailsList;
}
