package com.viajessolparaiso.gestion_ofertas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
// para pasar el estado de las ofertas
@EnableScheduling
public class GestionOfertasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionOfertasApplication.class, args);

	}


}
