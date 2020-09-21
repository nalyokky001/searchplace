package com.kabang.searchplace.service;

import com.kabang.searchplace.common.CommonUtil;
import com.kabang.searchplace.common.KakaoApiHelper;
import com.kabang.searchplace.common.NaverApiHelper;
import com.kabang.searchplace.domain.Member;
import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.*;
import com.kabang.searchplace.repository.MemberRepository;
import com.kabang.searchplace.repository.SearchPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.transform.Result;
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

    @Resource
    private CommonUtil commonUtil;

    private final SearchPlaceRepository searchPlaceRepository;
    private static Logger logger = LoggerFactory.getLogger(SearchPlaceService.class);

    public SearchPlaceService(SearchPlaceRepository searchPlaceRepository) {
        this.searchPlaceRepository = searchPlaceRepository;
    }

    public SearchPlaceResponseResultDto searchPlace(SearchPlaceRequestDto requestDto) {

        SearchResultDto checkResult = commonUtil.checkMemberAndApiKey(requestDto.getUserId(), requestDto.getApiKey());
        SearchPlaceResponseResultDto result;

        if ( !checkResult.getResult().equals("success") ) {
            result = new SearchPlaceResponseResultDto(checkResult, null);
            logger.error(result.getResult());
            return result;
        }

        SearchPlace searchPlace = new SearchPlace();
        searchPlace.setUserId(requestDto.getUserId());
        searchPlace.setKeyword(requestDto.getKeyword());

        List<SearchPlaceResponseDto> placeList = new ArrayList<>();

        NaverApiResponseDto naverApiResponseDto = naverApiHelper.searchPlaceByKeyword(searchPlace.getKeyword());
        KakaoApiResponseDto kakaoApiResponseDto = kakaoApiHelper.searchPlaceByKeyword(searchPlace.getKeyword());

        List<SearchPlaceResponseDto> resultByNaver = naverApiResponseDto.getItems().stream()
                .map(SearchPlaceResponseDto::new)
                .collect(Collectors.toList());

        List<SearchPlaceResponseDto> resultByKakao = kakaoApiResponseDto.getDocuments().stream()
                .map(SearchPlaceResponseDto::new)
                .collect(Collectors.toList());

        placeList.addAll(resultByKakao.stream().filter(k -> resultByNaver.stream().anyMatch(n -> k.getPlaceName().equals(n.getPlaceName()))).collect(Collectors.toList()));
        placeList.addAll(resultByKakao.stream().filter(k -> resultByNaver.stream().noneMatch(n -> k.getPlaceName().equals(n.getPlaceName()))).collect(Collectors.toList()));
        placeList.addAll(resultByNaver.stream().filter(n -> resultByKakao.stream().noneMatch(k -> n.getPlaceName().equals(k.getPlaceName()))).collect(Collectors.toList()));

        searchPlaceRepository.saveHistory(searchPlace);

        checkResult.setCount(placeList.size());
        result = new SearchPlaceResponseResultDto(checkResult, placeList);

        return result;
    }

    public SearchHistoryResponseResultDto searchHistory(SearchHistoryRequestDto requestDto) {

        SearchResultDto checkResult = commonUtil.checkMemberAndApiKey(requestDto.getUserId(), requestDto.getApiKey());
        SearchHistoryResponseResultDto result;

        if ( !checkResult.getResult().equals("success") ) {
            result = new SearchHistoryResponseResultDto(checkResult, null);
            logger.error(result.getResult());
            return result;
        }

        List<SearchHistoryResponseDto> historyList = searchPlaceRepository.findHistory(requestDto.getUserId()).stream().map(SearchHistoryResponseDto::new).collect(Collectors.toList());

        checkResult.setCount(historyList.size());
        result = new SearchHistoryResponseResultDto(checkResult, historyList);

        return result;
    }

    public SearchFavoriteResponseResultDto searchFavorite(SearchFavoriteRequestDto requestDto) {

        SearchResultDto checkResult = commonUtil.checkMemberAndApiKey(requestDto.getUserId(), requestDto.getApiKey());
        SearchFavoriteResponseResultDto result;

        if ( !checkResult.getResult().equals("success") ) {
            result = new SearchFavoriteResponseResultDto(checkResult, null);
            logger.error(result.getResult());
            return result;
        }

        List<SearchFavoriteResponseDto> favoriteList = searchPlaceRepository.findFavorite();

        checkResult.setCount(favoriteList.size());
        result = new SearchFavoriteResponseResultDto(checkResult, favoriteList);

        return result;
    }
}
