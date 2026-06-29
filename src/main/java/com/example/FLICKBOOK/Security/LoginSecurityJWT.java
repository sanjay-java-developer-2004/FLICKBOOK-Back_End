package com.example.FLICKBOOK.Security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class LoginSecurityJWT {

    String Token = "Sanjay@2004";

    //GENERATE TOKEN

    public String GenerateToken(String user) {
        String TokenGeneration = Jwts.builder()
                                .setSubject(user)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                                .signWith(SignatureAlgorithm.HS256,Token)
                                .compact();

        return TokenGeneration;


    }


    //DECODE TOKEN

    public String DecodeToken(String token) {
        String DecodeToken = ((JwtParser) Jwts.parser()
                            .setSigningKey(Token))
                            .parseClaimsJws(token)
                            .getBody()
                            .getSubject();

        return DecodeToken;
    }
}
