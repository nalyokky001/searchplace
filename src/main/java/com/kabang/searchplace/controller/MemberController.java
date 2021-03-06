package com.kabang.searchplace.controller;

import com.kabang.searchplace.domain.Member;
import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.dto.MemberResponseDto;
import com.kabang.searchplace.exception.MyDataNotFoundException;
import com.kabang.searchplace.exception.MyPasswdNotCorrectException;
import com.kabang.searchplace.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberController {

    private final MemberService memberService;
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/join")
    @ResponseBody
    public MemberResponseDto join(@RequestBody @Valid MemberRequestDto requestMember) {

        MemberResponseDto responseMember = new MemberResponseDto();

        try {
            String createdUserId = memberService.join(requestMember);
            responseMember.setUserId(createdUserId);
            responseMember.setMessage("success");
            logger.debug(responseMember.getMessage());
        } catch (Exception e) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("fail : already exist ID");
            logger.error(responseMember.getMessage());
        }

        return responseMember;
    }

    @PostMapping("/member/login")
    @ResponseBody
    public MemberResponseDto login(@RequestBody @Valid MemberRequestDto requestMember) {

        Member member = new Member();
        member.setUserId(requestMember.getUserId());
        member.setPassword(requestMember.getPassword());

        Member result;
        MemberResponseDto responseMember = new MemberResponseDto();

        try {
            result = memberService.login(requestMember);
            responseMember.setUserId(result.getUserId());
            responseMember.setApiKey(result.getApiKey());
            responseMember.setMessage("success");
            logger.debug(responseMember.getMessage());
        } catch (MyDataNotFoundException mdnf) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("fail : userId is not exist.");
            logger.error(responseMember.getMessage());
        } catch (MyPasswdNotCorrectException mpnc) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("fail : user password is not correct.");
            logger.error(responseMember.getMessage());
        }

        return responseMember;
    }
}
