package com.sundayCinema.sundayCinema.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundayCinema.sundayCinema.security.security.CustomAuthenticationFilter;
import com.sundayCinema.sundayCinema.security.security.CustomCookieLoggingFilter;
import com.sundayCinema.sundayCinema.security.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 뒤로 밀어 둬보쟈 모두 얼라이브로
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomCookieLoggingFilter customCookieLoggingFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customCookieLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .permitAll()
                .and()
                .sessionManagement()
                .sessionFixation().changeSessionId()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //STATELESS
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Access Denied!");
                })
                .and()
                .logout()
                .permitAll();


        http
                .authorizeRequests()
                .antMatchers().permitAll()
                .anyRequest().permitAll();
//                .antMatchers("/check-session").permitAll()
//                .anyRequest().permitAll()

//                .antMatchers("/h2/**").permitAll() // H2 콘솔에 대한 접근 허용
//                .antMatchers("/h2-console/**").permitAll() // H2 콘솔에 대한 접근 허용
//                .antMatchers("/checksession").permitAll() // 엔드포인트에 대한 권한 설정
//                .antMatchers().permitAll()
//                .antMatchers().permitAll()
//                .anyRequest().permitAll();

        // CSRF 토큰 비활성화 (개발 환경에서만 사용하고, 실제 운영에서는 활성화해야 함)
        http.csrf().disable();
        http.cors();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
//        source.registerCorsConfiguration("/host/check-session", configuration);
//        source.registerCorsConfiguration("/check-session", configuration);

        return source;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter("/signin", authenticationManager(), objectMapper);
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/signin?error"));
        return filter;
    }
}
