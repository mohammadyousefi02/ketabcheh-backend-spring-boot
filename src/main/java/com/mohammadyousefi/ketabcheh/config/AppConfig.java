package com.mohammadyousefi.ketabcheh.config;

import com.mohammadyousefi.ketabcheh.util.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport {
    private final Interceptor interceptor;

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins("*") // List of allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // List of allowed HTTP methods
                .allowedHeaders("*") // List of allowed headers
                .allowCredentials(true);
    }

    public AppConfig(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
