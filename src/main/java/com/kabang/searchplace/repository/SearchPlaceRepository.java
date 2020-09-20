package com.kabang.searchplace.repository;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.SearchFavoriteDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SearchPlaceRepository {

    @PersistenceContext
    private EntityManager em;

    public SearchPlace saveHistory(SearchPlace searchPlace) {
        searchPlace.setSearchTime(LocalDateTime.now());
        em.persist(searchPlace);
        return searchPlace;
    }

    public List<SearchPlace> findHistory(SearchPlace searchPlace) {

        return em.createQuery("select m from SearchPlace m where m.userId = :userId", SearchPlace.class)
                .setParameter("userId", searchPlace.getUserId())
                .getResultList();
    }

    public List<SearchFavoriteDto> findFavorite() {
        List<SearchFavoriteDto> result = em.createQuery("select m.keyword, count(m.userId) as cnt from SearchPlace m group by m.keyword", SearchFavoriteDto.class)
                .getResultList();

        return result;
    }
}
