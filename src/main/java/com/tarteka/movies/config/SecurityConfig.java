package com.tarteka.movies.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())  // Desactiva la protección CSRF (Cross-Site Request Forgery)
//                .httpBasic(Customizer.withDefaults()) // Habilita la autenticación HTTP básica
//                // Configura la gestión de sesiones como 'stateless', es decir, no se mantiene estado de sesión en el servidor
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // Configura la autorización de las peticiones HTTP
//                .authorizeHttpRequests(http -> {
//                    // endpoints públicos
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();
//                    // Define un endpoint privado, que solo los usuarios con la autoridad 'READ' pueden acceder
//                    http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAuthority("READ");
//                    // Cualquier otra petición no especificada será denegada
//                    http.anyRequest().denyAll(); // Alternativamente, podría ser: http.anyRequest().authenticated();
//                })
//                .build();
//    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (Cross-Site Request Forgery)
                .httpBasic(Customizer.withDefaults()) // Habilita la autenticación HTTP básica
                // Configura la gestión de sesiones como 'stateless', es decir, no se mantiene estado de sesión en el servidor
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
                // La autorización de las peticiones HTTP se realizan en el controllador con la directiva  @PreAuthorize....
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    // en vez de usar una base de datos, vamos usar unos usuarios...
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("tarteka")
                .password("1234").roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());

        userDetailsList.add(User.withUsername("sergio")
                .password("1234").roles("USER")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
