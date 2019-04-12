package com.jw.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JwtTokenDto {

    private String hashToken ;
    private JwtHeaderDto header ;
    private JwtContentDto content ;

}
