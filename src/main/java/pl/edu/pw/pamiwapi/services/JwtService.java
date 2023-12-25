package pl.edu.pw.pamiwapi.services;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static pl.edu.pw.pamiwapi.security.JwtProperties.JWT_EXPIRE_TIME;

@Service
public class JwtService {
    private static final SecretKey secret = Jwts.SIG.HS512.key().build();

    public String generateJwt(Authentication authentication) {
        var username = authentication.getName();
        var currentDate = new Date();
        var expireDate = new Date(currentDate.getTime() + JWT_EXPIRE_TIME);

        return Jwts.builder()
                .issuedAt(currentDate)
                .expiration(expireDate)
                .subject(username)
                .signWith(secret)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
