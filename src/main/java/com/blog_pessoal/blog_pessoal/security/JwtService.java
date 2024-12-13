package com.blog_pessoal.blog_pessoal.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//io.jsonwebtoken não funcionou antes de dar update no Maven, e conferir o POM.
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component //Class
public class JwtService {

	
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; //o atributo SECRET. Este atributo armazenará a Chave de assinatura do Token JWT
//Foi definido também o modificador static, porque o atributo deve estar associado apenas e exclusivamente a esta Classe
	
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET); //responsáfel por codificar o o SECRET em B64 e gerar a assinatura do JWT 
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {//retorna todas as Claims inseridas no payload do Token JWT
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	} // Através do operador de referência de métodos (::), que retorna o valor da claim sub.
	// Recupera os dados da Claim sub, onde se encontra o usuario (e-mail).

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	} 

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
}
