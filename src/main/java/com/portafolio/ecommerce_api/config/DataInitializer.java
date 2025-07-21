package com.portafolio.ecommerce_api.config;

import com.portafolio.ecommerce_api.entities.Role;
import com.portafolio.ecommerce_api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Este código se ejecuta una vez que la aplicación ha arrancado
        // y la base de datos está completamente lista.

        // Crear ROLE_USER si no existe
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("Rol ROLE_USER creado.");
        }

        // Crear ROLE_ADMIN si no existe
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println("Rol ROLE_ADMIN creado.");
        }
    }
}