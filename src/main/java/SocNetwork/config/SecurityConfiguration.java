package SocNetwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by aleksei on 11.02.17.
 */


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests().antMatchers("/api/secured").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/me").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/edit").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/add").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/delete").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/block").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/unblock").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/friends").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/subscribers").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/{id}/subscriptions").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/profile/blacklist").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/api/messages/{id}").hasRole("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
