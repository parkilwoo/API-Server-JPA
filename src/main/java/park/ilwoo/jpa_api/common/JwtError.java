package park.ilwoo.jpa_api.common;

import lombok.Getter;

/**
 * JWT Error 처리를 위한 Enum
 */
public enum JwtError {

    UnauthorizedException("410", "로그인이 필요합니다."),
    ExpiredJwtException("411", "토큰이 만료 되었습니다."),
    UnsupportedJwtException("412", "지원하지 않은 토큰 형식입니다."),
    MalformedJwtException("413", "토큰의 구성이 옳바르지 않습니다."),
    SignatureException("414", "토큰의 서명을 확인하지 못했습니다."),
    NotValidUser("414", "유저의 정보가 존재하지 않습니다.");

    @Getter
    private String code;
    @Getter
    private String msg;

    JwtError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
