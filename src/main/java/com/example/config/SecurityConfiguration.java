package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);
        return userDetailsManager;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // the default authentication is an in memory user called "user" with a generated password at start up
        // i.e. Using default security password: 63ee012d-4ea5-4476-b925-edef1b8c52fe
        auth.jdbcAuthentication().dataSource(this.dataSource)
            .withDefaultSchema()
            .withUser("admin").password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Authorize application specific URLs
        http.authorizeRequests().antMatchers("/", "/student/**", "/course/**", "/students", "/courses")
            .authenticated().and()

        // Configure the login rules
        .formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error").permitAll().and()

        // Configure what happens on logout
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll().and()

        // Disable Cross Site Request Forgery, nice feature but annoying for this demo
        .csrf().disable();

        // Disable X-Frame-Options header so the h2-console can work
        http.headers().frameOptions().disable();
    }

}