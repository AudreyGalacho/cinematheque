package fr.eni.filmotheque.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // defini un beanspring
@EnableWebSecurity
public class WebSecurityConfig {
    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);


    // Securisation des routes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/accueil", "/films", "/films/detail","/register", "/style.css").permitAll()
                        //permition avec role
                        .requestMatchers("/ajoutfilm").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // défini la page de connection et déconnection comme accessible a tous
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/films")
                        .permitAll());

        logger.info("-------------SecurityFilterChain ok");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new BCryptPasswordEncoder();
    }


}