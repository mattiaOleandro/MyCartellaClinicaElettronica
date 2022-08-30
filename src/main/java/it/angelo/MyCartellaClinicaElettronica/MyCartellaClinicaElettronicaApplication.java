package it.angelo.MyCartellaClinicaElettronica;

import it.angelo.MyCartellaClinicaElettronica.user.entities.Role;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class MyCartellaClinicaElettronicaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyCartellaClinicaElettronicaApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		//Role VIEWER
		Role viewer = new Role();
		viewer.setName("VIEWER");
		viewer.setDescription("Viewer");
		roleRepository.save(viewer);
	}
}
