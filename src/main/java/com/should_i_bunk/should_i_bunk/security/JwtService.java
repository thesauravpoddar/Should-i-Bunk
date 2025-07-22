package com.should_i_bunk.should_i_bunk.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    // This class can be used to handle JWT operations such as token generation, validation, etc.
    // Currently, it is empty, but you can add methods here as needed.

    // Example method to generate a JWT token
    // public String generateToken(UserDetails userDetails) {
    //     return Jwts.builder()
    //             .setSubject(userDetails.getUsername())
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
    //             .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
    //             .compact();
    // }

    // Add more JWT-related methods as needed for your application
    // token type -> access token or refresh token
    // here we will write the methods to validate or to sign and validate the token
    // extract the claims so and, so forth
    private static final String TOKEN_TYPE = "token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    @Value("${app.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;
    @Value("${app.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
        this.publicKey = KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
    }

    public String generateToken(final String username) {
        final Map<String , Object> claims = Map.of(TOKEN_TYPE, "ACCESS_TOKEN");
        return buildToken(username , claims, accessTokenExpiration);
    }

    public String generateRefreshToken(final String username) {
        final Map<String , Object> claims = Map.of(TOKEN_TYPE, "REFRESH_TOKEN");
        return buildToken(username , claims, refreshTokenExpiration);
    }

    private String buildToken(
            final String username,
            final Map<String, Object> claims,
            final long expiration
    ) {
        // here we are gonna build the token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.privateKey)
                .compact();

    }

    public boolean isTokenValid(final String token , final String expectedUsername) {

        final String username = extractUsername(token); //Hey we are gonna, extract the username that we will compare with our expected username.
        return username.equals(expectedUsername) && !isTokenExpired(token); // it says that Whether the username that we entered is equal to the are expected username and checking that is token expired or not
        
    }

    private boolean isTokenExpired(final String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String refreshAccessToken(final String refreshToken) {
        final Claims claims = extractClaims(refreshToken);
        if (!claims.get(TOKEN_TYPE).equals("REFRESH_TOKEN")) {
            throw new RuntimeException("Invalid token type");
        }
        if(isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token expired");
        }
        final String username = claims.getSubject();
        return generateRefreshToken(username);
    }

}
