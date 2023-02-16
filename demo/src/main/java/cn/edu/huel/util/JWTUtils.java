package cn.edu.huel.util;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
public final class JWTUtils {

	public static final String SIGN_KEY = "hash";

	public static String createToken(String info) {
		return Jwts.builder().setSubject(info)
				.signWith(SignatureAlgorithm.HS512, SIGN_KEY)
				.compressWith(CompressionCodecs.GZIP)
				.compact();
	}


	public static String getInfo(String token) {
		return Jwts.parser().setSigningKey(SIGN_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}


}
