package park.ilwoo.javis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * Swagger 설정을 위한 Config
 */
@Configuration
public class SwaggerConfig {

    /**
     * Swagger 설정의 핵심이 되는 Docket 설정
     *
     * useDefaultResponseMessages - Swagger에서 제공해주는 기본응답코드 사용여부
     * apis - Swagger API 문서로 만들 위치 설정(RequestHandlerSelectors.any() -> 모든요청)
     * paths - URL에 해당하는 요청만 Swagger API 문서로 만듬
     * ignoredParameterTypes - 특정 파라미터 타입 안보이기
     * apiInfo - API문서에 대한 설명
     *
     * @return Docket 객체
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Errors.class, Authentication.class)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    /**
     * Swagger API문서 설명을 셋팅하는 Method
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("자비스앤빌런즈 API")
                .description("자비스앤빌런즈 과제(API)_박일우")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("token", authorizationScopes));
    }

}
