package com.kabang.searchplace.dto;

import lombok.Data;

@Data
public class KakaoApiResponseDto {

    private Document[] documents;

    static class Document {
        public String address_name;
        public String category_group_code;
        public String category_group_name;
        public String category_name;
        public String distance;
        public String id;
        public String phone;
        public String place_name;
        public String road_address_name;
        public String x;
        public String y;
    }
}
