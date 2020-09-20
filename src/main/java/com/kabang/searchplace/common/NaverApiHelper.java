package com.kabang.searchplace.common;

import com.kabang.searchplace.dto.NaverApiResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NaverApiHelper {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String NAVER_CLIENT_ID = "hvk1hpWP_P2JxkxyqCad";
    private final String NAVER_CLIEND_SECRET = "jgZOl01DhZ";
    private final String naverApiUrl_searchPlace = "https://openapi.naver.com/v1/search/local.json?query={keyword}&display=5";

    public NaverApiResponseDto searchPlaceByKeyword(String keyword) {
        NaverApiResponseDto naverApiResponseDto;
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIEND_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            naverApiResponseDto = restTemplate.exchange(naverApiUrl_searchPlace, HttpMethod.GET, entity, NaverApiResponseDto.class, keyword).getBody();
        } catch (Exception e) {
            return null;
        }

        return naverApiResponseDto;
    }
}