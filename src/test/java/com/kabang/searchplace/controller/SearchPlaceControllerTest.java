package com.kabang.searchplace.controller;

import com.google.gson.Gson;
import com.kabang.searchplace.dto.SearchFavoriteRequestDto;
import com.kabang.searchplace.dto.SearchHistoryRequestDto;
import com.kabang.searchplace.dto.SearchPlaceRequestDto;
import com.kabang.searchplace.service.SearchPlaceService;
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
class SearchPlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private SearchPlaceService searchPlaceService;

    @Test
    void searchPlace() throws Exception {

        SearchPlaceRequestDto testRequestDto = new SearchPlaceRequestDto();
        testRequestDto.setApiKey("testApiKey");
        testRequestDto.setUserId("testUser_5");
        testRequestDto.setKeyword("시프트업");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/search/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andDo(print());
    }

    @Test
    void searchHistory() throws Exception {

        SearchHistoryRequestDto testRequestDto = new SearchHistoryRequestDto();
        testRequestDto.setApiKey("testApiKey");
        testRequestDto.setUserId("testUser_0");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/search/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$..data[0].keyword").value("공원"))
                .andExpect(jsonPath("$..data[1].keyword").value("카카오뱅크"))
                .andDo(print());
    }

    @Test
    void searchFavorite() throws Exception {

        SearchFavoriteRequestDto testRequestDto = new SearchFavoriteRequestDto();
        testRequestDto.setApiKey("testApiKey");
        testRequestDto.setUserId("testUser_0");
        Gson gson = new Gson();
        String json = gson.toJson(testRequestDto);

        this.mvc.perform(post("/search/favorite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$..data[0].keyword").value("카카오뱅크"))
                .andExpect(jsonPath("$..data[0].cnt").value(5))
                .andExpect(jsonPath("$..data[1].keyword").value("공원"))
                .andExpect(jsonPath("$..data[1].cnt").value(4))
                .andDo(print());
    }
}