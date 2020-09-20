package com.kabang.searchplace.service;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.KakaoApiResponseDto;
import com.kabang.searchplace.dto.NaverApiResponseDto;
import com.kabang.searchplace.dto.SearchFavoriteDto;
import com.kabang.searchplace.repository.SearchPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class SearchPlaceService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String NAVER_CLIENT_ID = "hvk1hpWP_P2JxkxyqCad";
    private final String NAVER_CLIEND_SECRET = "jgZOl01DhZ";

    private final String naverApiUrl_searchPlace = "https://openapi.naver.com/v1/search/local.json?query={keyword}&display=5";

    private final String KAKAO_REST_API_ID = "2f70b85f0c53ed6b43fef2e8448e36f4";

    private final String kakaoApiUrl_searchPlace = "https://dapi.kakao.com/v2/local/search/keyword.json?query={keyword}";

    private final SearchPlaceRepository searchPlaceRepository;
    private static Logger logger = LoggerFactory.getLogger(MemberService.class);

    public SearchPlaceService(SearchPlaceRepository searchPlaceRepository) {
        this.searchPlaceRepository = searchPlaceRepository;
    }

    public NaverApiResponseDto searchPlaceByNaver(SearchPlace searchPlace) {

        NaverApiResponseDto naverApiResponseDto;
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIEND_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            naverApiResponseDto = restTemplate.exchange(naverApiUrl_searchPlace, HttpMethod.GET, entity, NaverApiResponseDto.class, searchPlace.getKeyword()).getBody();

            searchPlaceRepository.saveHistory(searchPlace);
        } catch (Exception e) {
            return null;
        }

        return naverApiResponseDto;
    }

    public KakaoApiResponseDto searchPlaceByKakao(SearchPlace searchPlace) {

        KakaoApiResponseDto kakaoApiResponseDto;
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_ID);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            kakaoApiResponseDto = restTemplate.exchange(kakaoApiUrl_searchPlace, HttpMethod.GET, entity, KakaoApiResponseDto.class, searchPlace.getKeyword()).getBody();

            searchPlaceRepository.saveHistory(searchPlace);
        } catch (Exception e) {
            return null;
        }

        logger.info("kakao : " + kakaoApiResponseDto.toString());

        return kakaoApiResponseDto;
    }

    public List<SearchPlace> searchHistory(SearchPlace searchPlace) {

        return searchPlaceRepository.findHistory(searchPlace);
    }

    public List<SearchFavoriteDto> searchFavorite() {
        return searchPlaceRepository.findFavorite();
    }
}
