package fr.univ.orleans.info.m1.ws.tp4.security.config;

import fr.univ.orleans.info.m1.ws.tp4.modele.FacadeUtilisateurs;
import fr.univ.orleans.info.m1.ws.tp4.modele.Role;
import fr.univ.orleans.info.m1.ws.tp4.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    FacadeUtilisateurs facadeUtilisateurs;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    /**
     * Configuration des permissions d'accès aux différentes URIs du WebService.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/utilisateurs").permitAll()
                .antMatchers("/api/questions/**").hasRole(Role.ENSEIGNANT.name())
                .antMatchers("/api/utilisateurs/**").hasRole(Role.ETUDIANT.name())
                .anyRequest().denyAll()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Service de génération des détails d'authentification d'un utilisateur.
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}

