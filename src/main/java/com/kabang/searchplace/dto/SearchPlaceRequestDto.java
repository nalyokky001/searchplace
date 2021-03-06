package com.kabang.searchplace.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SearchPlaceRequestDto {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String keyword;

    @NotEmpty
    private String apiKey;
}
