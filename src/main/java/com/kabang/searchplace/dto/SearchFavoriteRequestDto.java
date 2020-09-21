package com.kabang.searchplace.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SearchFavoriteRequestDto {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String apiKey;
}
