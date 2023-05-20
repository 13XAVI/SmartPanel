package com.SmartPannel.userData.JWT;

import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.Users;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class jwtTokenApi {
    private static final long EXPIRE_DURATION = 24*60*60*1000;
    private static final Logger LOGGER =  LoggerFactory.getLogger(jwtTokenApi.class);
    @Value("${AppJwtSecretKey}")
    private  String secreteKey;
    public String generateAccToken(Users users){
        List<String> roleNames = users.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(users.getId()+","+users.getEmail())
                .claim("roles", roleNames)
                .setIssuer("SmartPanel")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256,secreteKey)
                .compact();
    }



    public boolean ValidateAccToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secreteKey)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException ex){
            LOGGER.error("JWT Expired");
        } catch (IllegalArgumentException ex){
            LOGGER.error("JWT is null ");
        } catch (MalformedJwtException ex){
            LOGGER.error("JWT is not Supported");
        } catch (SignatureException ex){
            LOGGER.error("Signature Validation Failed ");
        }
        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }
    public Claims parseClaims(String token){
        return Jwts.parser()
                .setSigningKey(secreteKey)
                .parseClaimsJws(token)
                .getBody();

    }
}
