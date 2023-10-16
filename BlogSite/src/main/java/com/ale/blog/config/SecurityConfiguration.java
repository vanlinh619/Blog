package com.ale.blog.config;

import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.security.RedirectAuthenticationEntryPoint;
import com.ale.blog.service.CustomOidcUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            @Qualifier("jwtAuthenticationFilter") OncePerRequestFilter oncePerRequestFilter,
            OAuth2UserService oAuth2UserService,
            RedirectAuthenticationEntryPoint redirectAuthenticationEntryPoint
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:3000"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        })
                )
//                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(redirectAuthenticationEntryPoint)
                )
                .requiresChannel(channelRequestMatcherRegistry ->
                        channelRequestMatcherRegistry.anyRequest().requiresSecure()
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/",
                                "/post/**",
                                "/login/**",
                                "/category/**",
                                "/image/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/api/authorize/**").authenticated()
                        .requestMatchers("/test/**").authenticated()
                        .requestMatchers("/api/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(oAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/post/18-gioi-thieu-design-patterns");
                        })
                )
                .addFilterBefore(oncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Remove the ROLE_ prefix
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
