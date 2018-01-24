package com.vizoni.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	/* Este método vem do CommandLineRunner que permite já executar um código quando iniciar,
	 * está sendo usado para popular as categorias do banco (para que não comece vazio)*/
	@Override
	public void run(String... arg0) throws Exception {
		
		
	}
}
