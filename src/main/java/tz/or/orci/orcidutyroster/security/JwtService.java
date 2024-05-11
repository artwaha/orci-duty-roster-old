package tz.or.orci.orcidutyroster.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tz.or.orci.orcidutyroster.model.entities.User;
import tz.or.orci.orcidutyroster.payload.response.UserDto;
import tz.or.orci.orcidutyroster.repository.TokenRepository;
import tz.or.orci.orcidutyroster.service.Utils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final Utils utils;
    private final TokenRepository tokenRepository;
    @Value("${jwt.token.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expiration-time}")
    private Long expirationTime;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        UserDto authenticatedUser = utils.userEntityToUserDto(userDetails);

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .claim("user", authenticatedUser)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        boolean isTokenRevoked = tokenRepository.existsByTokenAndRevokedTrue(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && !isTokenRevoked;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
