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


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests().antMatchers(
                "/api/secured", //dummy request for testing security

                "/api/profile/me",
                "/api/profile/edit",
                "/api/profile/{id}/add",
                "/api/profile/{id}/add",
                "/api/profile/{id}/delete",
                "/api/profile/{id}/block",
                "/api/profile/{id}/unblock",
                "/api/profile/{id}/friends",
                "/api/profile/{id}/subscribers",
                "/api/profile/{id}/subscriptions",
                "/api/profile/blacklist",

                "/api/messages/{id}",
                "/api/messages",

                "/api/search").hasRole("USER");
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
