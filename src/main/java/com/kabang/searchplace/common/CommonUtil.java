package com.kabang.searchplace.common;

import com.kabang.searchplace.domain.Member;
import com.kabang.searchplace.dto.SearchPlaceResponseResultDto;
import com.kabang.searchplace.dto.SearchResultDto;
import com.kabang.searchplace.repository.MemberRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

@Component
public class CommonUtil {

    @Resource
    private MemberRepository memberRepository;

    public static String generateApiKey() {
        Random rand = new Random();

        byte[] apiKeyByte = new byte[10];
        rand.nextBytes(apiKeyByte);

        StringBuilder sb = new StringBuilder();

        for (byte b : apiKeyByte) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public SearchResultDto checkMemberAndApiKey(String userId, String apiKey) {

        Member resultMember = memberRepository.find(userId);
        SearchResultDto result = new SearchResultDto();

        if ( resultMember == null ) {
            result.setCount(0);
            result.setResult("fail : userId is not exist");
        } else if( !resultMember.getApiKey().equals(apiKey) ) {
            result.setCount(0);
            result.setResult("fail : api key is not match");
            return result;
        } else {
            result.setResult("success");
        }

        return result;
    }
}
