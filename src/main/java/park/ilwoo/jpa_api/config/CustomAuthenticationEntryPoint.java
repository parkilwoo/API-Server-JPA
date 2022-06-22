package park.ilwoo.jpa_api.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import park.ilwoo.jpa_api.common.JwtError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 실패했을때 EntryPoint 설정
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows(JSONException.class)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());

        JwtError jwtError = (JwtError) request.getAttribute("jwtError");
        //  jwtError가 아니면 로그인 issue
        if(jwtError == null) setResp(response, JwtError.UnauthorizedException);
        else setResp(response, jwtError);
    }

    private void setResp(HttpServletResponse resp, JwtError jwtError) throws JSONException, IOException {
        // status를 401 에러로 지정
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글깨짐 수정.
        resp.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("code", jwtError.getCode());
        json.put("message", jwtError.getMsg());

        resp.getWriter().print(json);
    }
}
