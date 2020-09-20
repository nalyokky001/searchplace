package com.kabang.searchplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class KakaoApiResponseDto {

    private List<Document> documents;

    @Data
    public static class Document {
        private String address_name;
        private String category_group_code;
        private String category_group_name;
        private String category_name;
        private String distance;
        private String id;
        private String phone;
        private String place_name;
        private String road_address_name;
        private String x;
        private String y;
    }
}
