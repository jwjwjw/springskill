package com.jw.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JwtHeaderDto {

    public String typ ;
    public String alg ;
}
