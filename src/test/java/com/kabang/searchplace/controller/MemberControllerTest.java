package com.kabang.searchplace.controller;


import com.google.gson.Gson;
import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.service.MemberService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private MemberService memberService;

    @Test
    public void join() throws Exception {

        MemberRequestDto testRequestDto = new MemberRequestDto("azit1022", "tjwngus1");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("azit1022"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.apiKey").value(IsNull.nullValue()))
                .andDo(print());

    }

    @Test
    public void joinFail() throws Exception {

        MemberRequestDto testRequestDto = new MemberRequestDto("testUser_0", "testPassword_0");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("testUser_0"))
                .andExpect(jsonPath("$.message").value("fail : already exist ID"))
                .andExpect(jsonPath("$.apiKey").value(IsNull.nullValue()))
                .andDo(print());
    }

    @Test
    void login() throws Exception {

        MemberRequestDto testRequestDto = new MemberRequestDto("testUser_0", "testPassword_0");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("testUser_0"))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.apiKey").value("testApiKey"))
                .andDo(print());
    }

    @Test
    void loginFailNotCorrectPassword() throws Exception {

        MemberRequestDto testRequestDto = new MemberRequestDto("testUser_0", "notCorrectPassword");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("testUser_0"))
                .andExpect(jsonPath("$.message").value("fail : user password is not correct."))
                .andExpect(jsonPath("$.apiKey").value(IsNull.nullValue()))
                .andDo(print());
    }

    @Test
    void loginFailNotExistUser() throws Exception {

        MemberRequestDto testRequestDto = new MemberRequestDto("testUser_10", "testPassword_10");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("testUser_10"))
                .andExpect(jsonPath("$.message").value("fail : userId is not exist."))
                .andExpect(jsonPath("$.apiKey").value(IsNull.nullValue()))
                .andDo(print());
    }
}