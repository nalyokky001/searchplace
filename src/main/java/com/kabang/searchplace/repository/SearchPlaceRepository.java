package com.kabang.searchplace.repository;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.SearchFavoriteResponseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SearchPlaceRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveHistory(SearchPlace searchPlace) {
        searchPlace.setSearchTime(LocalDateTime.now());
        em.persist(searchPlace);
    }

    public List<SearchPlace> findHistory(String userId) {
        return em.createQuery("select m from SearchPlace m where m.userId = :userId order by m.searchTime desc", SearchPlace.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<SearchFavoriteResponseDto> findFavorite() {
        return em.createQuery("select m.keyword, count(m.userId) as cnt from SearchPlace m group by m.keyword order by cnt desc", SearchFavoriteResponseDto.class)
                .getResultList();
    }
}
