package com.jw.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserAccount {

    private String userId;
    private String username;
    private String password;
}
