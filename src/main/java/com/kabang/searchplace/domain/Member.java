package com.kabang.searchplace.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    private String userId;

    @Column
    private String password;

    @Column
    private String salt;

    @Column
    private String apiKey;
}
