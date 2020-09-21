package com.kabang.searchplace.repository;

import com.kabang.searchplace.domain.SearchPlace;
import com.kabang.searchplace.dto.SearchFavoriteResponseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Object[]> tempResult = em.createQuery("select m.keyword as keyword, count(m.userId) as cnt from SearchPlace as m group by m.keyword order by cnt desc").getResultList();
        List<SearchFavoriteResponseDto> result = new ArrayList<>();

        for (Object[] tempRow:tempResult) {
            SearchFavoriteResponseDto tempDto = new SearchFavoriteResponseDto();
            tempDto.setKeyword((String)tempRow[0]);
            tempDto.setCnt(((Long)tempRow[1]).intValue());
            result.add(tempDto);
        }
        
        return result;
    }
}
