package park.ilwoo.jpa_api.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import park.ilwoo.jpa_api.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt인증 Filter
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

        if (token != null && JwtTokenProvider.validateToken(request, token)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //  인증된 정보가 없을때만 새로 token에서 가져온다
            if(authentication == null) {
                String userId = JwtTokenProvider.getUserIdFromJWT(token); // Token에서 userId 가져오기

                try {
                    UserDetails userDetails = userService.loadUserByUsername(userId);   //  인증된 user 만들기
                    authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                catch (Exception e) {
                    log.error(e.getMessage());
                    request.setAttribute("jwtError", JwtError.NotValidUser);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}