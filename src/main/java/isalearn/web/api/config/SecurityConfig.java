package isalearn.web.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private SecurityDatabaseService securityService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers("/login/managers").hasAnyRole("MANAGERS")
                .requestMatchers("/login/users").hasAnyRole("USERS","MANAGERS")
                .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("{noop}user123")
                .roles("USERS")
                .build());
        manager.createUser(User.withUsername("admin")
                .password("{noop}master123")
                .roles("MANAGERS")
                .build());
        return manager;
    }*/

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> {
            authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
        
        return http.build();
    }*/
}
