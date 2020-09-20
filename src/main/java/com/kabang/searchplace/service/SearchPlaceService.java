package com.kabang.searchplace.service;

import com.kabang.searchplace.common.KakaoApiHelper;
import com.kabang.searchplace.common.NaverApiHelper;
import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.*;
import com.kabang.searchplace.repository.SearchPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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

        List<SearchPlaceResponseDto> resultByNaver = naverApiResponseDto.getItems().stream()
                .map(SearchPlaceResponseDto::new)
                .collect(Collectors.toList());

        List<SearchPlaceResponseDto> resultByKakao = kakaoApiResponseDto.getDocuments().stream()
                .map(SearchPlaceResponseDto::new)
                .collect(Collectors.toList());

        result.addAll(resultByKakao.stream().filter(k -> resultByNaver.stream().anyMatch(n -> k.getPlaceName().equals(n.getPlaceName()))).collect(Collectors.toList()));
        result.addAll(resultByKakao.stream().filter(k -> resultByNaver.stream().noneMatch(n -> k.getPlaceName().equals(n.getPlaceName()))).collect(Collectors.toList()));
        result.addAll(resultByNaver.stream().filter(n -> resultByKakao.stream().noneMatch(k -> n.getPlaceName().equals(k.getPlaceName()))).collect(Collectors.toList()));

        searchPlaceRepository.saveHistory(searchPlace);

        return result;
    }

    public List<SearchHistoryResponseDto> searchHistory(String userId) {
        return searchPlaceRepository.findHistory(userId).stream()
                .map(SearchHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<SearchFavoriteResponseDto> searchFavorite() {
        return searchPlaceRepository.findFavorite();
    }
}
