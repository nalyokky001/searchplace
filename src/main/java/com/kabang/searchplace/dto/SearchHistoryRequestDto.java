package com.kabang.searchplace.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SearchHistoryRequestDto {

    @NotEmpty
    private String userId;
}
