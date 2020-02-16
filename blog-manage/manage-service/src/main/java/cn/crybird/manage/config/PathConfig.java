package cn.crybird.manage.config;

import lombok.Data;
import org.aspectj.weaver.patterns.Declare;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("path.properties")
@ConfigurationProperties(prefix = "path")
@Data
public class PathConfig {

    private String md;
    private String image;

}
