package com.kabang.searchplace.controller;

import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.dto.MemberResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value=MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MemberController mc;

    @Autowired
    private MockRestServiceServer mrss;

    @Test
    void join() {
        MemberRequestDto joinMemReqDto = new MemberRequestDto();
        MemberResponseDto joinMemResDto = new MemberResponseDto();

        joinMemReqDto.setUserId("abc");
        joinMemReqDto.setPasswd("111");



        MemberController memberController = new MemberController();



    }

    @Test
    void login() {
    }
}