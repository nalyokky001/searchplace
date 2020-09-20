package com.kabang.searchplace.controller;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.*;
import com.kabang.searchplace.service.SearchPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SearchPlaceController {

    private final SearchPlaceService searchPlaceService;
    private static Logger logger = LoggerFactory.getLogger(SearchPlaceController.class);

    public SearchPlaceController(SearchPlaceService searchPlaceService) {
        this.searchPlaceService = searchPlaceService;
    }

    @PostMapping("/search/place")
    @ResponseBody
    public List<SearchPlaceResponseDto> searchPlace(@RequestBody @Valid SearchPlaceRequestDto requestDto) {

        SearchPlace searchPlace = new SearchPlace();
        searchPlace.setUserId(requestDto.getUserId());
        searchPlace.setKeyword(requestDto.getKeyword());

        return searchPlaceService.searchPlace(searchPlace);
    }

    @PostMapping("/search/history")
    @ResponseBody
    public List<SearchHistoryResponseDto> searchHistory(@RequestBody @Valid SearchHistoryRequestDto requestDto) {
        return searchPlaceService.searchHistory(requestDto.getUserId());
    }

    @GetMapping("/search/favorite")
    @ResponseBody
    public List<SearchFavoriteResponseDto> searchFavorite() {
        return searchPlaceService.searchFavorite();
    }
}
