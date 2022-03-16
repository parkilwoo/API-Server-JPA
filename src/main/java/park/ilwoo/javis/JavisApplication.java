package park.ilwoo.javis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import park.ilwoo.javis.config.YamlPropertySourceFactory;

@SpringBootApplication
@PropertySource(value = {"classpath:/config/common.yml"}, ignoreResourceNotFound = false, factory = YamlPropertySourceFactory.class)
public class JavisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavisApplication.class, args);
    }

}
