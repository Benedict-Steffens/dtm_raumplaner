package project.raumplaner.RaumplanerApp.user.controller;

import project.raumplaner.RaumplanerApp.user.model.entity.Role;

import java.util.List;

public interface RoleController {

    /**
     * @return A list with all roles from type List<Role>
     */
    List<Role> findAll();
}
