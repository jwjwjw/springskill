package com.jw.handler;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jw.domain.*;
import com.jw.util.AESUtil;
import com.jw.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class JwtHandlerImpl implements JwtHandler {
    private static final Logger logger = LogManager.getLogger(JwtHandlerImpl.class);

    private String issuer = "SPRING_BOOT_AUTH_SERVER";
    private String secret = "591e5da9e61533d7aa7a0e11a5e05f3db045c0afa141c5c406afb2078c5c3b7cc40eed579bdbdf56f4b1575f28b984faa55730e6c51b371c39ea21a2bbcc7e55";

    private String aesKey = "000myaeskey000";


    @Override
    public Result registerAccessToken(UserAccount userAccount) {
        Result result = new Result("200","Success",null);
        String id = userAccount.getUserId();
        if(!this.validUserId(id)){
            result.setResultCode("500");
            result.setResultMsg("invalid user Id");
        }

        String token = this.genAccessToken(userAccount);
        result.setPayload(token);
        return result;
    }

    @Override
    public Result verifyAccessToken(String token) {
        Result result = new Result("200","Success",null);
        JwtTokenDto jwtTokenDto;
        try {
            jwtTokenDto = this.decode(token);
        } catch (IOException e) {
            e.printStackTrace();
            result.setResultCode("500");
            result.setResultMsg("Decode token error");
            return result;
        }
//        String userId = jwtTokenDto.getContent().getUserId();
        // do some checking for jwtTokenDto

        result.setPayload(jwtTokenDto);

        return result;
    }

    private String genAccessToken(UserAccount userAccount) {
        String id= userAccount.getUserId();
        String username = userAccount.getUsername();
        String password = userAccount.getPassword();

        Calendar expiryTime = Calendar.getInstance();
        logger.info("expiryCal.getTime() = " + expiryTime.getTime());
        expiryTime.add(Calendar.DATE,30);
        Date expiryDate = expiryTime.getTime();
        logger.info("expiryDate = " + expiryDate.getTime());

        Algorithm algorithm = Algorithm.HMAC512(secret);
        Map<String,Object> headerMap = new HashMap<>() ;
        String token = JWT.create().withHeader(headerMap)
                .withClaim("userId", id)
                .withClaim("username", username)
                .withClaim("password", password.hashCode())
                .withExpiresAt(expiryDate)
                .withIssuer(issuer)
                .sign(algorithm);
        logger.info("Access token = " + token);

        //todo Save AccessToken to DB

        return AESUtil.encrypt(token,aesKey);
    }

    private JwtTokenDto decode(String token) throws IOException {

        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        ObjectMapper mapper = new ObjectMapper();

        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(AESUtil.decrypt(token,aesKey));
        byte[] headerByte = Base64.getDecoder().decode(jwt.getHeader()) ;
        JwtHeaderDto header = mapper.readValue(new String(headerByte), JwtHeaderDto.class) ;

        byte[] contentByte = Base64.getDecoder().decode(jwt.getPayload()) ;
        JwtContentDto content = mapper.readValue(new String(contentByte), JwtContentDto.class) ;

        jwtTokenDto.setHashToken(token);
        jwtTokenDto.setHeader(header);
        jwtTokenDto.setContent(content);
        return jwtTokenDto;
    }

    private boolean validUserId(String id) {

        return StringUtil.containDigitOnly(id);
    }
}
