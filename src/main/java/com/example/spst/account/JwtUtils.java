package com.example.spst.account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {

    private static SecretKey key;

    static {
        key = generalKey();
    }

    private JwtUtils() {}
    public static Jws<Claims> parseClaim(String token) {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }


    public static SecretKey generalKey(){
        return Jwts.SIG.HS256.key().build();
    }

    public static String generateToken(UserDetails userDetails) {
        final String id = UUID.randomUUID().toString();

        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(key)
                .id(id)
                .issuer("me")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (10 * 1000 * 60)))
                .claim("username", userDetails.getUsername());
        return jwtBuilder.compact();
    }

    public static Authentication getAuthentication(String token) {
        Jws<Claims> jwt = JwtUtils.parseClaim(token);
        Claims payload = jwt.getPayload();
        Object username = payload.get("username");
        Authentication jwtAutencation = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        return jwtAutencation;
    }
}
