package project.raumplaner.RaumplanerApp.user.service;

import project.raumplaner.RaumplanerApp.user.model.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * @return Admin with type Role
     */
    Role getAdminRole();

    /**
     * @return User with type Role
     */
    Role getUserRole();

    /**
     * @return A list with all roles with type List<Role>
     */
    List<Role> findAll();
}
