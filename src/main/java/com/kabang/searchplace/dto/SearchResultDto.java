package com.kabang.searchplace.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchResultDto {

    public int count;
    public String result;
}