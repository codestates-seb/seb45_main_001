package com.sundayCinema.sundayCinema.movie.dto.detailPageDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPageDto<T> {
    private T detailsList;
}
