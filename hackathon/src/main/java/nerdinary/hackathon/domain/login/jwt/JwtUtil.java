package nerdinary.hackathon.domain.login.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private final long accessTokenValid = 1000 * 60 * 30;
	private final long refreshTokenValid = 1000 * 60 * 60 * 24 * 14;

	public String createAccessToken(String email) {
		return createToken(email, accessTokenValid);
	}

	public String createRefreshToken(String email) {
		return createToken(email, refreshTokenValid);
	}

	private String createToken(String email, long validity) {
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + validity))
			.signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getPasswordFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
