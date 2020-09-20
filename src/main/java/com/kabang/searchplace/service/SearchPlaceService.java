package com.kabang.searchplace.service;

import com.kabang.searchplace.common.KakaoApiHelper;
import com.kabang.searchplace.common.NaverApiHelper;
import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.KakaoApiResponseDto;
import com.kabang.searchplace.dto.NaverApiResponseDto;
import com.kabang.searchplace.dto.SearchFavoriteDto;
import com.kabang.searchplace.dto.SearchPlaceResponseDto;
import com.kabang.searchplace.repository.SearchPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchPlaceService {

    @Resource
    private NaverApiHelper naverApiHelper;

    @Resource
    private KakaoApiHelper kakaoApiHelper;

    private final SearchPlaceRepository searchPlaceRepository;
    private static Logger logger = LoggerFactory.getLogger(SearchPlaceService.class);

    public SearchPlaceService(SearchPlaceRepository searchPlaceRepository) {
        this.searchPlaceRepository = searchPlaceRepository;
    }

    public List<SearchPlaceResponseDto> searchPlace(SearchPlace searchPlace) {

        List<SearchPlaceResponseDto> result = new ArrayList<>();

        NaverApiResponseDto naverApiResponseDto = naverApiHelper.searchPlaceByKeyword(searchPlace.getKeyword());
        KakaoApiResponseDto kakaoApiResponseDto = kakaoApiHelper.searchPlaceByKeyword(searchPlace.getKeyword());

        result.addAll(naverApiResponseDto.getItems().stream()
                .map(o -> new SearchPlaceResponseDto(o))
                .collect(Collectors.toList()));

        result.addAll(kakaoApiResponseDto.getDocuments().stream()
                .map(o -> new SearchPlaceResponseDto(o))
                .collect(Collectors.toList()));

        searchPlaceRepository.saveHistory(searchPlace);

        return result;
    }

    public List<SearchPlace> searchHistory(SearchPlace searchPlace) {
        return searchPlaceRepository.findHistory(searchPlace);
    }

    public List<SearchFavoriteDto> searchFavorite() {
        return searchPlaceRepository.findFavorite();
    }
}
