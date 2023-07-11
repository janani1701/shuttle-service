package com.shuttle.controllers;

import com.shuttle.models.ERole;
import com.shuttle.models.Role;
import com.shuttle.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @Autowired
  RoleRepository roleRepository;

  @GetMapping("/all")
  public String allAccess() {
    Role role = new Role();

    role.setId(1);
    role.setName(ERole.ROLE_ADMIN);
    roleRepository.save(role);
    role.setId(2);
    role.setName(ERole.ROLE_STUDENT);
    roleRepository.save(role);
    role.setId(3);
    role.setName(ERole.ROLE_DRIVER);
    roleRepository.save(role);
    return "Validated Successfully";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('STUDENT') or hasRole('DRIVER') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/driver")
  @PreAuthorize("hasRole('DRIVER')")
  public String driverAccess() {
    return "Driver Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
