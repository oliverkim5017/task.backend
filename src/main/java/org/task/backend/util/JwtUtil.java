package org.task.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author OliverKim
 * @description
 * @since 2024-03-10
 */
public class JwtUtil {


	private static final String SECRET_KEY = "Nyuh0z61SYHzkzXAdgDzbzwBmoCAW0l8K9Ij_3GZ4Pk"; // 选择一个强秘钥
	private static final long EXPIRATION_TIME = 5 * 60 * 10000; // token过期时间，例如这里设置为5分钟

	public static String generateToken(String username, String name, String departmentId, String roleId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);
		claims.put("name", name);
		claims.put("department", departmentId);
		claims.put("role", roleId);

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Date exp = new Date(nowMillis + EXPIRATION_TIME);

		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

}
