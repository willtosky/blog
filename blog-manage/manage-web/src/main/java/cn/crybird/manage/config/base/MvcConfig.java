package cn.crybird.manage.config.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("admin/login");
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/admin/").setViewName("admin/index");
        registry.addViewController("/admin/index").setViewName("admin/index");
    }
}
