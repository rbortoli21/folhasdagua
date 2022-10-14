package com.folhasdagua.folhasdagua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public WebSecurityConfiguration() {
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)httpSecurity.authorizeRequests().antMatchers(new String[]{"/"})).permitAll();
    }
}
