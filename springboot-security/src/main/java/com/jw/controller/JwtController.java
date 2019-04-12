package com.jw.controller;


import com.jw.domain.Result;
import com.jw.domain.UserAccount;
import com.jw.handler.JwtHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.jw.constants.ApiConstants.REGISTER_ACCESS_TOKEN;
import static com.jw.constants.ApiConstants.VERIFY_ACCESS_TOKEN;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    private static final Logger logger = LogManager.getLogger(JwtController.class);

    @Autowired
    private JwtHandler jwtHandler;

    @PostMapping(value =REGISTER_ACCESS_TOKEN)
    public Result registerAccessToken(@RequestBody UserAccount userAccount){
        return jwtHandler.registerAccessToken(userAccount);
    }

    @PostMapping(value =VERIFY_ACCESS_TOKEN)
    public Result verifyAccessToken(@RequestHeader String token){
        return jwtHandler.verifyAccessToken(token);
    }
}
