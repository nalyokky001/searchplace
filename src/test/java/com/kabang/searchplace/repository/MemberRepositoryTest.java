package com.kabang.searchplace.repository;

import com.kabang.searchplace.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {

        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        // given
        Member member = new Member();
        member.setUserId("azit1022_2");
        member.setPasswd(scpwd.encode(member.getUserId()));

        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
        System.out.println(member.getPasswd());
    }


}