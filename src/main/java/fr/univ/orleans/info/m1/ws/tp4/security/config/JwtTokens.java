package fr.univ.orleans.info.m1.ws.tp4.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokens {

    private static final long EXPIRATION_TIME = 1_000_000 ;

    @Autowired
    private Key secretKey;

    public String genereToken(UserDetails userDetails){
        String login = userDetails.getUsername();
        var roles =userDetails.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", roles);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
        return token;
    }
}
