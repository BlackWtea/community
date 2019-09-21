package com.mashen.community.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String token;
    private String name;
    private String accountId;
    private Long gmtCreated;
    private Long gmtModified;


}
