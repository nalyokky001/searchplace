package com.kabang.searchplace.common;

import com.kabang.searchplace.dto.KakaoApiResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoApiHelper {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String KAKAO_REST_API_ID = "2f70b85f0c53ed6b43fef2e8448e36f4";
    private final String kakaoApiUrl_searchPlace = "https://dapi.kakao.com/v2/local/search/keyword.json?query={keyword}";

    public KakaoApiResponseDto searchPlaceByKeyword(String keyword) {

        KakaoApiResponseDto kakaoApiResponseDto;
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_ID);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            kakaoApiResponseDto = restTemplate.exchange(kakaoApiUrl_searchPlace, HttpMethod.GET, entity, KakaoApiResponseDto.class, keyword).getBody();
        } catch (Exception e) {
            return null;
        }

        return kakaoApiResponseDto;
    }
}
