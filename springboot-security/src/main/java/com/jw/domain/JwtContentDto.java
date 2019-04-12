package com.jw.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JwtContentDto {

    private String userId;
    private String username;
    private String password;
    private String iss;
    private long exp;
}
