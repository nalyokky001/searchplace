package com.kabang.searchplace.service;

import com.kabang.searchplace.common.EncryptHelper;
import com.kabang.searchplace.domain.Member;
import com.kabang.searchplace.dto.MemberRequestDto;
import com.kabang.searchplace.exception.MyDataNotFoundException;
import com.kabang.searchplace.exception.MyPasswdNotCorrectException;
import com.kabang.searchplace.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private static Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(MemberRequestDto requestMember)  {

        String createdUserId;

        try{
            String salt = EncryptHelper.generateSalt();
            String newPassword = EncryptHelper.encrypt(requestMember.getPassword(), salt);

            Member member = new Member();
            member.setUserId(requestMember.getUserId());
            member.setPassword(newPassword);
            member.setSalt(salt);
            // member.setApiKey(CommonUtil.generateApiKey());
            member.setApiKey("testApiKey"); // test 목적 때문에 고정키로 임시 변경

            createdUserId = memberRepository.save(member);
        } catch(Exception e) {
            logger.error(e.getMessage());
            return null;
        }

        return createdUserId;
    }

    public Member login(MemberRequestDto requestMember) throws MyDataNotFoundException, MyPasswdNotCorrectException {

        Member result = memberRepository.find(requestMember.getUserId());

        if ( result == null ) {
            logger.error("userId is not exist");
            throw new MyDataNotFoundException();
        }

        if ( !result.getPassword().equals(EncryptHelper.encrypt(requestMember.getPassword(), result.getSalt()))) {
            logger.error("password is not correct");
            throw new MyPasswdNotCorrectException();
        }

        return result;
    }
}
