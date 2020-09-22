package com.kabang.searchplace;

import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.dto.SearchPlaceRequestDto;
import com.kabang.searchplace.service.MemberService;
import com.kabang.searchplace.service.SearchPlaceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;

@SpringBootApplication
public class SearchplaceApplication {

	@Resource
	private MemberService memberService;

	@Resource
	private SearchPlaceService searchPlaceService;

	public static void main(String[] args) {
		SpringApplication.run(SearchplaceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initDBAfterStartUp() {

		String tempUserID;
		String tempPassword;

		for (int i = 0; i < 6; i++) {
			tempUserID = String.format("testUser_%d", i);
			tempPassword = String.format("testPassword_%d", i);

			MemberRequestDto requestDto = new MemberRequestDto(tempUserID, tempPassword);

			memberService.join(requestDto);
		}

		for (int i = 0; i < 5; i++) {
			SearchPlaceRequestDto requestDto = new SearchPlaceRequestDto();
			requestDto.setKeyword("카카오뱅크");
			requestDto.setUserId(String.format("testUser_%d", i));
			requestDto.setApiKey("testApiKey");

			searchPlaceService.searchPlace(requestDto);
		}

		for (int i = 0; i < 4; i++) {
			SearchPlaceRequestDto requestDto = new SearchPlaceRequestDto();
			requestDto.setKeyword("공원");
			requestDto.setUserId(String.format("testUser_%d", i));
			requestDto.setApiKey("testApiKey");

			searchPlaceService.searchPlace(requestDto);
		}
	}
}
