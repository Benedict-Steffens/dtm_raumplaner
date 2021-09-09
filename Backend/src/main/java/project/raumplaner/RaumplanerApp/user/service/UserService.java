package project.raumplaner.RaumplanerApp.user.service;

import project.raumplaner.RaumplanerApp.base.BaseService;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.model.entity.EmailVerification;

import java.util.List;

public interface UserService extends BaseService<AppUser, Long> {

    /**
     * @param email The email address to create an EmailVerification object with
     * @return The create EmailVerification object with type EmailVerification
     */
    EmailVerification createEmailVerificationObject(String email);

    /**
     * @param id The id of the user to validate the email of
     * @return The user with the validated email address with type AppUser
     */
    AppUser validateEmailAddress(Long id);

    /**
     * @param name The last name of the users to be found
     * @return The users with the the desired last name with type List<AppUser>
     */
    List<AppUser> findByName(String name);

    /**
     * @param order The order (asc, desc) the returned users shall be ordered as
     * @return All users, ordered by the desired order
     */
    List<AppUser> findAll(String order);

    /**
     * @param email The email address of the user to be retrieved
     * @return The desired user
     */
    AppUser findByEmail(String email);

}


