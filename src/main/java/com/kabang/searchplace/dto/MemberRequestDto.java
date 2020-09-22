package com.kabang.searchplace.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberRequestDto {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;

    public MemberRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
