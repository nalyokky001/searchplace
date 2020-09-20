package com.kabang.searchplace.controller;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.*;
import com.kabang.searchplace.service.SearchPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
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
    public List<SearchPlaceResponseDto> searchPlace(@RequestBody @Valid SearchPlaceRequestDto requestPlace) {

        SearchPlace searchPlace = new SearchPlace();
        searchPlace.setUserId(requestPlace.getUserId());
        searchPlace.setKeyword(requestPlace.getKeyword());

        List<SearchPlaceResponseDto> result = searchPlaceService.searchPlace(searchPlace);

        return result;
    }

    @PostMapping("/search/history")
    @ResponseBody
    public List<SearchPlace> searchHistory(@RequestBody @Valid SearchHistoryRequestDto requestDto) {

        SearchPlace searchPlace = new SearchPlace();
        searchPlace.setUserId(requestDto.getUserId());

        return searchPlaceService.searchHistory(searchPlace);
    }

    @GetMapping("/search/favorite")
    @ResponseBody
    public List<SearchFavoriteDto> searchFavorite() {
        return searchPlaceService.searchFavorite();
    }
}
