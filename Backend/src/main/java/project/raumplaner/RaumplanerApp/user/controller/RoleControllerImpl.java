package project.raumplaner.RaumplanerApp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.raumplaner.RaumplanerApp.user.model.entity.Role;
import project.raumplaner.RaumplanerApp.user.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleControllerImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @GetMapping
    public List<Role> findAll() {
        return this.roleService.findAll();
    }
}
