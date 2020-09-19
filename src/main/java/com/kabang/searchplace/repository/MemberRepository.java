package com.kabang.searchplace.repository;

import com.kabang.searchplace.domain.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public String save(Member member) {
        em.persist(member);
        return member.getUserId();
    }

    public Member find(String userId) {
        return em.find(Member.class, userId);
    }

}
