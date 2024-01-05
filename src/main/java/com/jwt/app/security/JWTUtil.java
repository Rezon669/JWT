package com.jwt.app.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.jsonwebtoken.Claims;

@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	private String secret;

	public String generateToken(String email, String role) throws IllegalArgumentException, JWTCreationException {
		return JWT.create().withSubject("User Details").withIssuer("JWT-Project").withClaim("email", email)
				.withClaim("role", role).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + 3600000)).sign(Algorithm.HMAC256(secret));
	}

	public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {

		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject("User Details").withIssuer("JWT-Project")
				.acceptExpiresAt(System.currentTimeMillis()).build();

		DecodedJWT jwt = verifier.verify(token);

		Date expiration = jwt.getExpiresAt();

		if (expiration.before(new Date())) {

			throw new TokenExpiredException(token, null);

		}

		return jwt.getClaim("email").asString();
	}
}
