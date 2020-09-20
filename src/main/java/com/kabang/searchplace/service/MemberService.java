package com.kabang.searchplace.service;

import com.kabang.searchplace.domain.Member;
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

    public String join(Member member)  {

        String createdUserId = "";

        try{
            createdUserId = memberRepository.save(member);
        } catch(Exception e) {
            logger.error(e.getMessage());
            return null;
        }

        return createdUserId;
    }

    public Member login(Member member) throws MyDataNotFoundException, MyPasswdNotCorrectException {

        Member result = memberRepository.find(member.getUserId());

        if ( result == null ) {
            logger.error("userId is not exist");
            throw new MyDataNotFoundException();
        }

        if ( !result.getPassword().equals(member.getPassword()) ) {
            logger.error("password is not correct");
            throw new MyPasswdNotCorrectException();
        }

        return result;
    }
}
