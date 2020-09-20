package com.kabang.searchplace.dto;

import lombok.Data;

@Data
public class NaverApiResponseDto {

    private int display;
    private int total;
    private int start;
    private Item[] items;

    static class Item {
        public String title;
        public String link;
        public String category;
        public String description;
        public String telephone;
        public String address;
        public String roadAddress;
        public String mapx;
        public String mapy;
    }
}
