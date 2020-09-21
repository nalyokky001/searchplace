package com.kabang.searchplace.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchHistoryResponseResultDto extends SearchResultDto {

    private List<SearchHistoryResponseDto> data;

    public SearchHistoryResponseResultDto(SearchResultDto resultDto, List<SearchHistoryResponseDto> listData) {
        this.count = resultDto.getCount();
        this.result = resultDto.getResult();
        this.data = listData;
    }
}
