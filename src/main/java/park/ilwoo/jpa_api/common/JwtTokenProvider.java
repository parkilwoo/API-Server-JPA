package park.ilwoo.jpa_api.common;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletRequest;
import java.util.Date;

/**
 * Jwt Token Provider Class
 */
@Slf4j
public class JwtTokenProvider {
    //  token secretKey
    private static final String JWT_SECRET = "secretKey";
    //  token 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    // jwt 토큰 생성
    public static String generateToken(Authentication authentication) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        Claims claims = Jwts.claims().setSubject((String) authentication.getPrincipal());

        return Jwts.builder()
                .setClaims(claims)       // claim 설정
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();
    }

    // Jwt 토큰에서 아이디 추출
    public static String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateToken(ServletRequest request, String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            request.setAttribute("jwtError",JwtError.MalformedJwtException);
        } catch (ExpiredJwtException ex) {
            request.setAttribute("jwtError",JwtError.ExpiredJwtException);
        } catch (UnsupportedJwtException ex) {
            request.setAttribute("jwtError", JwtError.UnsupportedJwtException);
        } catch (SignatureException ex) {
            request.setAttribute("jwtError", JwtError.SignatureException);
        }
        return false;
    }


}