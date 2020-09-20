package com.kabang.searchplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverApiResponseDto {

    private int display;
    private int total;
    private int start;
    public List<Item> items;

    @Data
    public static class Item {
        private String title;
        private String link;
        private String category;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private String mapx;
        private String mapy;
    }
}
