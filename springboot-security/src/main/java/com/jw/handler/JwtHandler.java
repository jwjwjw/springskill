package com.jw.handler;


import com.jw.domain.Result;
import com.jw.domain.UserAccount;

public interface JwtHandler {


    Result registerAccessToken(UserAccount userAccount);

    Result verifyAccessToken(String token);
}
