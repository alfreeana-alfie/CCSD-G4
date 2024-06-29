/*
    Filename: AppConfig.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */

package com.ccsd.project.config;

import com.ccsd.project.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.upload-url}")
    private String uploadUrl;

    /*
     * Description:
     *   To retrieve Base URL from application.properties
     * */
    public String getBaseUrl() {
        return baseUrl;
    }

    /*
     * Description:
     *   To retrieve Upload URL from application.properties
     * */
    public String getUploadUrl() {
        return uploadUrl;
    }

    /*
     * Description:
     *   Create the bean for PasswordEncoder
     * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Description:
     *   Create bean for CustomUserDetailsService
     * */
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /*
     * Description:
     *   Setting up the user authentication.
     * */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    /*
     * Description:
     *   Configuring the login/logout route. If Login is successful, direct to /indexL, else redirect to /index.
     *   If logout, redirect to /index page.
     * */
    @Bean
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/index").authenticated()
                        .anyRequest().permitAll())
                .formLogin(login -> login.usernameParameter("username").defaultSuccessUrl("/indexL").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/index").permitAll());
        return httpSecurity.build();

    }

    /* START --- API POSTMAN AUTHENTICATION  */
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService1() {
//        UserDetails user = User.withUsername("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                .permitAll()).csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
//    }
    /* END --- API POSTMAN AUTHENTICATION  */
}
