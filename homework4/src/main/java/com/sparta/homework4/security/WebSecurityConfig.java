package com.sparta.homework4.security;

import com.sparta.homework4.jwt.JwtAuthenticationFilter;
import com.sparta.homework4.jwt.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(new RequestMatcher[]{PathRequest.toStaticResources().atCommonLocations()});
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        ((HttpSecurity) ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                                        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                                                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                                                        http.authorizeRequests().antMatchers(new String[]{"/user/**"})).permitAll()
                                                        .antMatchers(new String[]{"**"})).permitAll()
                                                .antMatchers(new String[]{"/"})).permitAll()
                                        .antMatchers(HttpMethod.GET, new String[]{"/api/contents"}))
                                .permitAll().antMatchers(HttpMethod.GET, new String[]{"/api/reply/**"}))
                        .permitAll().anyRequest()).authenticated()
                .and())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and())
                .addFilterBefore(new JwtAuthenticationFilter(this.jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
