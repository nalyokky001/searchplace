package com.kabang.searchplace.dto;

import lombok.Data;

@Data
public class SearchPlaceResponseDto {

    private String placeName;
    private String placeAddress;

    public SearchPlaceResponseDto(NaverApiResponseDto.Item item) {
        placeName = item.getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("\\p{Z}", "");
        placeAddress = item.getAddress();
    }

    public SearchPlaceResponseDto(KakaoApiResponseDto.Document document) {
        placeName = document.getPlace_name().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("\\p{Z}", "");
        placeAddress = document.getAddress_name();
    }
}
