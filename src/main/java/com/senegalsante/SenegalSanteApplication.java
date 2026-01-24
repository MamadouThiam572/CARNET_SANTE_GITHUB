package com.senegalsante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SenegalSanteApplication {
    public static void main(String[] args) {
        SpringApplication.run(SenegalSanteApplication.class, args);
        System.out.println("=========================================");
        System.out.println("SÉNÉGAL SANTÉ (PURE JAVA) LANCÉ !");
        System.out.println("Accès : http://localhost:8080");
        System.out.println("=========================================");
    }
}