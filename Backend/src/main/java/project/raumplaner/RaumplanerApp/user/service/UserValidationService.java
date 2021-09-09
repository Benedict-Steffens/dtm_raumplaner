package project.raumplaner.RaumplanerApp.user.service;

import project.raumplaner.RaumplanerApp.base.BaseValidationService;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;

public interface UserValidationService extends BaseValidationService<AppUser, Long> {

    /**
     * @param user The user of whom the email address shall be checked, if it exists in the database already
     * @throws IllegalArgumentException in case of another user in the database having the same email address as the user to be checked
     */
    void existsByEmail(AppUser user);

}
