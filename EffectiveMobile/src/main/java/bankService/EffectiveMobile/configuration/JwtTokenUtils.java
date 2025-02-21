package bankService.EffectiveMobile.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret.token}")
    private String secretToken;

    @Value("${jwt.life-time}")
    private Duration jwtLifeTime;

    public String generateToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", user.getAuthorities());

        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedDate).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES256, secretToken)
                .compact();
    }

    public String getUsernameFromToke(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getUserEmailsFromToken(String token){
        return getClaimsFromToken(token).get("authorities", List.class);
    }

    private Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretToken).parseClaimsJwt(token).getBody();
    }
}
