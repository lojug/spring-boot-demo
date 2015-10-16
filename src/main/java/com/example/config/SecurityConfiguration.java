package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // the default authentication is an in memory user called "user" with a generated password at start up
        // i.e. Using default security password: 63ee012d-4ea5-4476-b925-edef1b8c52fe
        auth.jdbcAuthentication().dataSource(this.dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Allow webjars
        http.authorizeRequests().antMatchers("/webjars/**").permitAll().and()

        // Every other request must be authorized, so authentication needs to happen
        .authorizeRequests().anyRequest().authenticated().and()

        // Except for the login page which we permit direct access to
        .formLogin().loginPage("/login").failureUrl("/login?error").permitAll().and()

        // And logout is permitted as well
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll().and()

        // Disable Cross Site Request Forgery, nice feature but annoying for this demo
        .csrf().disable();
    }

}