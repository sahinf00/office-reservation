package com.ofis.rezervasyon.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ofis.rezervasyon.enums.RoleName;
import com.ofis.rezervasyon.model.Role;
import com.ofis.rezervasyon.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // check if EMPLOYEE role exists in the database, if not, add it
        if (roleRepository.findByName(RoleName.EMPLOYEE).isEmpty()) {
            Role employeeRole = new Role();
            employeeRole.setName(RoleName.EMPLOYEE);
            roleRepository.save(employeeRole);
            System.out.println("--> EMPLOYEE role automatically added to the database.");
        }

        // check if ADMIN role exists in the database, if not, add it
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(RoleName.ADMIN);
            roleRepository.save(adminRole);
            System.out.println("--> ADMIN role automatically added to the database.");
        }
    }
}
