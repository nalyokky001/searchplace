package com.kabang.searchplace.dto;

import lombok.Data;

@Data
public class SearchPlaceResponseDto {

    private String placeName;
    private String placeAddress;

    public SearchPlaceResponseDto(NaverApiResponseDto.Item item) {
        placeName = item.getTitle();
        placeAddress = item.getAddress();
    }

    public SearchPlaceResponseDto(KakaoApiResponseDto.Document document) {
        placeName = document.getPlace_name();
        placeAddress = document.getAddress_name();
    }
}
