package com.kabang.searchplace.controller;

import com.kabang.searchplace.domain.Member;
import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.dto.MemberResponseDto;
import com.kabang.searchplace.exception.MyDataNotFoundException;
import com.kabang.searchplace.exception.MyPasswdNotCorrectException;
import com.kabang.searchplace.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MemberController {

    private final MemberService memberService;
    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    @ResponseBody
    public MemberResponseDto join(@RequestBody @Valid MemberRequestDto requestMember) {

        logger.info(requestMember.getUserId());
        logger.info(requestMember.getPassword());

        MemberResponseDto responseMember = new MemberResponseDto();

        Member member = new Member();
        member.setUserId(requestMember.getUserId());
        member.setPassword(requestMember.getPassword());

        try {
            String createdUserId = memberService.join(member);
            responseMember.setUserId(createdUserId);
            responseMember.setMessage("success");
        } catch (Exception e) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("duplicated");
        }

        return responseMember;
    }

    @GetMapping("/member")
    @ResponseBody
    public MemberResponseDto login(@RequestBody @Valid MemberRequestDto requestMember) {

        logger.info(requestMember.getUserId());
        logger.info(requestMember.getPassword());

        Member member = new Member();
        member.setUserId(requestMember.getUserId());
        member.setPassword(requestMember.getPassword());

        Member result;
        MemberResponseDto responseMember = new MemberResponseDto();

        try {
            result = memberService.login(member);
            responseMember.setUserId(result.getUserId());
            responseMember.setMessage("success");
        } catch (MyDataNotFoundException mdnf) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("userId is not exist.");
        } catch (MyPasswdNotCorrectException mpnc) {
            responseMember.setUserId(requestMember.getUserId());
            responseMember.setMessage("user password is not correct.");
        }

        return responseMember;
    }
}
