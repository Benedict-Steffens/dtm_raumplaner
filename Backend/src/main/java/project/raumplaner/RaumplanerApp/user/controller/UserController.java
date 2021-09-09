package project.raumplaner.RaumplanerApp.user.controller;

import project.raumplaner.RaumplanerApp.base.ExtendedBaseController;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;

import java.util.List;

public interface UserController extends ExtendedBaseController<AppUser, Long> {

    /**
     * @param order The order in which all users shall be retrieved from the database by their last name
     * @return A list with all users, ordered by desired order with type List<User>
     */
    List<AppUser> findAll(String order);

    /**
     * @param id The ID of the user who's email address shall be validated
     * @return The user with the validated email address
     */
    AppUser validateEmail(Long id);
}
