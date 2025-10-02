package s25.bookstore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/books/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/**").hasAuthority("ADMIN")
                        .requestMatchers("/h2-console/**").permitAll() // for h2console
                        .anyRequest().authenticated())
                // Käyttää HTTP Basic -autentikointia oletusasetuksilla (Postman)
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions
                        .disable())) // for h2console
                .formLogin(formlogin -> formlogin
                        .defaultSuccessUrl("/main", true)
                        .permitAll())
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); // not for production, just for development

        return http.build();
    }

    /*
     * private UserDetailsService userDetailsService;
     * 
     * // Constructor injection
     * public WebSecurityConfig(UserDetailsService userDetailsService) {
     * this.userDetailsService = userDetailsService;
     * }
     */
    /*
     * @Autowired
     * public void configureGlobal(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.userDetailsService(userDetailsService).passwordEncoder(new
     * BCryptPasswordEncoder());
     * }
     */
}
