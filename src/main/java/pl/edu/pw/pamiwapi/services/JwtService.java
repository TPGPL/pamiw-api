package pl.edu.pw.pamiwapi.services;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final SecretKey secret = Jwts.SIG.HS512.key().build();
    private static final int JWT_EXPIRE_TIME = 30 * 60 * 1000;
    private static final String JWT_ISSUER = "PamiwAPI";

    public String generateJwt(String username) {
        var currentDate = new Date();
        var expireDate = new Date(currentDate.getTime() + JWT_EXPIRE_TIME);

        return Jwts.builder()
                .issuer(JWT_ISSUER)
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
