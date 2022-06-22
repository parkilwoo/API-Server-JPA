package park.ilwoo.jpa_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import park.ilwoo.jpa_api.config.YamlPropertySourceFactory;

@SpringBootApplication
@PropertySource(value = {"classpath:/config/common.yml"}, ignoreResourceNotFound = false, factory = YamlPropertySourceFactory.class)
public class JpaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApiApplication.class, args);
    }

}
