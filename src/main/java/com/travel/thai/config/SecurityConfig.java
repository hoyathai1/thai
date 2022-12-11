package com.travel.thai.config;

import com.travel.thai.user.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthenticationFailureHandler customFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler customAuthSuccessHandler;

    @Autowired
    private LogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/board/**").permitAll()
                .antMatchers("/pc/**").permitAll()
                .antMatchers("/**").permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/board/list")
                .successHandler(customAuthSuccessHandler)
                .failureHandler(customFailureHandler)
                .permitAll();

        http.rememberMe()
                .key("hoya.no.1!")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30)       // 한달
                .userDetailsService(userDetailService);

        http.csrf().disable();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler);

        http.exceptionHandling()
                .accessDeniedPage("/warning");

    }

    /**
     * 로그인성공 처리
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
}
