package com.kabang.searchplace.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SearchPlace {

    @Id
    @GeneratedValue
    private Long historyId;

    @Column
    private String userId;

    @Column
    private String keyword;

    @Column
    private LocalDateTime searchTime;
}
