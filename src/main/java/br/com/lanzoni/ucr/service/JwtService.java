package br.com.lanzoni.ucr.service;

import br.com.lanzoni.ucr.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;

    private static String SECRET = "35b42134baec35ec6d73a8d5cc1793ef";

    //The JWT signature algorithm we will be using to sign the token
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //We will sign our JWT with our ApiKey secret
    private static byte[] apiKeySecretBytes = SECRET.getBytes();
    private static Key key = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    public static String getAuthentication(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("id", String.class);
    }

    public static String generateAuthentication(User user) {
        String jwt = Jwts.builder()
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
        return jwt;
    }

}
