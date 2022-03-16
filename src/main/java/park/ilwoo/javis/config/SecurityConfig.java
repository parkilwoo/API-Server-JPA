package park.ilwoo.javis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import park.ilwoo.javis.common.JwtAuthenticationFilter;
import park.ilwoo.javis.user.UserService;

/**
 * Security Config Class
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserService userService;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, UserService userService) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userService = userService;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * Swagger UI 사용을 위한 예외처리
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/favicon.ico"
                        ,"/error"
                        ,"/swagger-ui/**"
                        ,"/swagger-resources/**"
                        ,"/v3/api-docs"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                // 이 요청은 인증을 하지 않는다.
                .antMatchers(
                        "/szs/signup",
                        "/szs/login"
                )
                .permitAll()
                // 다른 모든 요청은 인증을 한다.
                .anyRequest().authenticated().and()
                // 상태없는 세션을 이용하며, 세션에 사용자의 상태를 저장하지 않는다.
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 모든 요청에 토큰을 검증하는 필터를 추가한다.
                .addFilterBefore(new JwtAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class).authorizeRequests();
    }
}