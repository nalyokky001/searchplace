package com.kabang.searchplace.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
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
