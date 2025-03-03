package dh.backend.music_store.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //TOKEN GENERATOR
    public String tokenGenerator(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // TOKEN VALIDATOR
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey()) // validar firma
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (Exception e){
            log.error("TOKEN INVALIDO ERROR: "+ e.getMessage());
            return false;
        }
    }

    //OBTENER USERNAME DEL TOKEN
    public String getUserNameFromToken(String token){
        return getClaim(token,Claims::getSubject);
    }

    // OBTENER 1 CLAIMS
    public <T> T getClaim(String token, Function<Claims,T> claimsTFunction){
        Claims claims = extracAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //OBTENER CLAIMS DEL TOKEN

    public Claims extracAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey()) // validar firma
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    //OBTENER FIRMA DEL TOKEN

    public Key getSignatureKey(){
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }


}
