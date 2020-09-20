package com.kabang.searchplace.dto;

import com.kabang.searchplace.domain.SearchPlace;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchHistoryResponseDto {
    private String userId;
    private String keyword;
    private LocalDateTime searchTime;

    public SearchHistoryResponseDto(SearchPlace searchPlace) {
        userId = searchPlace.getUserId();
        keyword = searchPlace.getKeyword();
        searchTime = searchPlace.getSearchTime();
    }
}