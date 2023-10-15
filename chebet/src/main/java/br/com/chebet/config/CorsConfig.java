package br.com.chebet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir para todas as URLs
            .allowedOrigins("*") // Permitir todas as origens
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(3600); // Tempo máximo de cache da configuração CORS
    }
}