package com.sundayCinema.sundayCinema.movie.dto.detaiPagelDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPageDto<T> {
    private T detailsList;
}
