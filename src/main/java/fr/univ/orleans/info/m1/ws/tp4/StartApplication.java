package fr.univ.orleans.info.m1.ws.tp4;

import fr.univ.orleans.info.m1.ws.tp4.modele.FacadeUtilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Autowired
    FacadeUtilisateurs facadeUtilisateurs;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            facadeUtilisateurs.inscrireUtilisateur(
                    "anna.conda@univ-orleans.fr",
                    passwordEncoder.encode("42andCounting"));

            facadeUtilisateurs.inscrireUtilisateur(
                    "mouss.arazeh@etu.univ-orleans.fr",
                    passwordEncoder.encode("1984"));
        };
    }

}
