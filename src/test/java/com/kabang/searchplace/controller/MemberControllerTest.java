package com.kabang.searchplace.controller;

import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.dto.MemberResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value=MemberController.class)
public class MemberControllerTest {

    @Test
    void join() {
    }

    @Test
    void login() {
    }
}