package org.example.events.npmg;


import org.example.events.npmg.models.Role.ERole;
import org.example.events.npmg.models.Role.Role;
import org.example.events.npmg.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@Bean
	public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.count() == 0) {
				for (ERole role : ERole.values())
					roleRepository.save(new Role(role));
			}
		};
	}
}
