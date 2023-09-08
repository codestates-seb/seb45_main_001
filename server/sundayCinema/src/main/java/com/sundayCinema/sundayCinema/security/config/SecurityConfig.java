//package com.sundayCinema.sundayCinema.security.config;
//
//
//import com.sundayCinema.sundayCinema.security.security.CustomAuthenticationFilter;
//import com.sundayCinema.sundayCinema.security.security.CustomUserDetailsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.firewall.DefaultHttpFirewall;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//
//                .cors()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .accessDeniedHandler((request, response, accessDeniedException) -> {
//                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    response.getWriter().write("Access Denied!");
//                })
//                .and()
//                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)// CustomAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 추가
//                .formLogin()
//                .loginPage("/signin") // 로그인 페이지 URL
//                .successHandler(new SimpleUrlAuthenticationSuccessHandler())
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//
//
//        http.authorizeRequests()
//                .antMatchers("/host/check-session").permitAll()
//                .anyRequest().authenticated();
////                .antMatchers("/h2/**").permitAll() // H2 콘솔에 대한 접근 허용
////                .antMatchers("/h2-console/**").permitAll() // H2 콘솔에 대한 접근 허용
////                .antMatchers("/checksession").permitAll() // 엔드포인트에 대한 권한 설정
//////                .antMatchers().permitAll()
////                .antMatchers("/host/signup", "/host/signin").permitAll()
////                .anyRequest().authenticated();
//
//        // CSRF 토큰 비활성화 (개발 환경에서만 사용하고, 실제 운영에서는 활성화해야 함)
//        http.csrf().disable();
//        http.cors();
//    }
//
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//    @Bean
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true); // // 문자열 허용 설정
//        return firewall;
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");// 허용할 도메인 추가
//        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
//        configuration.addAllowedHeader("*"); // 모든 헤더 허용
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/host/check-session", configuration);
//        source.registerCorsConfiguration("/check-session", configuration);
//
//        return source;
//    }
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//    @Bean
//    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter("/signin", authenticationManager());
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/signin?error"));
//        return filter;
//    }
//
//}
