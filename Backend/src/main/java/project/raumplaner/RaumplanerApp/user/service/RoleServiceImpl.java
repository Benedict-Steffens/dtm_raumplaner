package project.raumplaner.RaumplanerApp.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.raumplaner.RaumplanerApp.user.model.entity.Role;
import project.raumplaner.RaumplanerApp.user.model.repository.RoleRepository;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final String admin = "admin";
    private final String user = "user";

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getAdminRole() {
        return this.roleRepository.findByName(admin);
    }

    @Override
    public Role getUserRole() {
        return this.roleRepository.findByName(user);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

}
