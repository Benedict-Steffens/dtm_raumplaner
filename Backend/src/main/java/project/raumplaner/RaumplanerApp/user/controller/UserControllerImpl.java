package project.raumplaner.RaumplanerApp.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("sign-up")
    public AppUser create(@RequestBody AppUser user) {
        return this.userService.create(user);
    }

    @Override
    @GetMapping("{id}")
    public AppUser findById(@PathVariable Long id) {
        return this.userService.findById(id);
    }

    @Override
    @GetMapping("search-by-second-name")
    public List<AppUser> findByName(@RequestParam() String name) {
        return this.userService.findByName(name);
    }

    @Override
    @GetMapping
    public List<AppUser> findAll(@RequestParam() String order) {
        return this.userService.findAll(order);
    }

    @Override
    @GetMapping("validate/{id}")
    public AppUser validateEmail(@PathVariable Long id) {
        return this.userService.validateEmailAddress(id);
    }

    @Override
    @PutMapping("{id}")
    public AppUser update(@RequestBody AppUser user, @PathVariable Long id) {
        return this.userService.update(user, id);
    }

    @Override
    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        return this.userService.delete(id);
    }
}
