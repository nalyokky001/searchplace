package com.kabang.searchplace.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchPlaceResponseResultDto extends SearchResultDto {

    private List<SearchPlaceResponseDto> data;

    public SearchPlaceResponseResultDto(SearchResultDto resultDto, List<SearchPlaceResponseDto> listData) {
        this.count = resultDto.getCount();
        this.result = resultDto.getResult();
        this.data = listData;
    }
}
