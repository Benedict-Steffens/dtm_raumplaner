package project.raumplaner.RaumplanerApp.user.service;

import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.model.entity.EmailVerification;
import project.raumplaner.RaumplanerApp.user.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class Given {

    public static AppUser validUserToCreate() {
        AppUser user = new AppUser();
        user.setFirstName("John");
        user.setSecondName("Doe");
        user.setEmail("john@doe.org");
        user.setPassword("123");
        return user;
    }

    public static AppUser userWithInvalidEMailAddress() {
        AppUser user = validUserToCreate();
        user.setEmail("invalid@email");
        return user;
    }

    public static AppUser userWithNonValidatedEmailAddress() {
        AppUser user = validUserToCreate();
        user.setId(1L);
        user.setEmail(null);
        user.setEmailVerification(validEmailVerification());
        return user;
    }

    public static AppUser userWithValidatedEmailAddress() {
        AppUser user = userWithNonValidatedEmailAddress();
        user.setId(2L);
        user.setEmail("user@mail.com");
        EmailVerification emailVerification = validEmailVerification();
        emailVerification.setNewEmail(null);
        user.setEmailVerification(emailVerification);
        return user;
    }

    public static List<AppUser> userListWithIdenticalSecondNames() {
        List<AppUser> users = new ArrayList<>();
        users.add(userWithNonValidatedEmailAddress());
        users.add(userWithValidatedEmailAddress());
        return users;
    }

    public static List<AppUser> userListWithDifferentSecondNamesDesc() {
        List<AppUser> users = userListWithIdenticalSecondNames();
        AppUser user = userWithNonValidatedEmailAddress();
        user.setSecondName("Davies");
        user.setId(3L);
        users.add(user);
        return users;
    }

    public static List<AppUser> userListWithDifferentSecondNamesAsc() {
        List<AppUser> users = userListWithIdenticalSecondNames();
        AppUser user = userWithNonValidatedEmailAddress();
        user.setSecondName("Smith");
        users.add(user);
        return users;
    }

    public static Role validUserRole() {
        Role user = new Role();
        user.setId(2L);
        user.setName("user");
        return user;
    }

    public static EmailVerification validEmailVerification() {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setId(1L);
        emailVerification.setNewEmail("1@mail.de");
        return emailVerification;
    }

    public static List<Booking> bookingList() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        return bookings;
    }
}
