package site.benitohuerta.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/uploads/**").addResourceLocations("file:resources/static/uploads/**");
    }
}