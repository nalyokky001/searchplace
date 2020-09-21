package com.kabang.searchplace.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchFavoriteResponseResultDto extends SearchResultDto {

    private List<SearchFavoriteResponseDto> data;

    public SearchFavoriteResponseResultDto(SearchResultDto resultDto, List<SearchFavoriteResponseDto> listData) {
        this.count = resultDto.getCount();
        this.result = resultDto.getResult();
        this.data = listData;
    }
}
