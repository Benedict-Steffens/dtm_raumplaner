package project.raumplaner.RaumplanerApp.user.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.model.entity.EmailVerification;
import project.raumplaner.RaumplanerApp.user.model.repository.EmailVerificationRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.RoleRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends UserValidationServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailVerificationRepository emailVerificationRepository,
                           RoleRepository roleRepository,
                           RoleServiceImpl roleServiceImpl,
                           BookingRepository bookingRepository,
                           PasswordEncoder passwordEncoder) {
        super(userRepository, emailVerificationRepository, bookingRepository);

        this.roleRepository = roleRepository;
        this.roleServiceImpl = roleServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser create(AppUser user) {
        Preconditions.checkNotNull(user, "User shall not be null.");
        Preconditions.checkArgument(user.getEmail().contains("@") &&
                user.getEmail().contains("."), "Email shall contain @ and .");

        user.setRole(this.roleServiceImpl.getUserRole());   //default role is user
        user.setEmailVerification(createEmailVerificationObject(user.getEmail()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        validateEntity(user);    // throws e

        // user.setEmail(null);

        user = this.userRepository.save(user);
        user.setPassword(null);
        log.info("User with ID {}, was successfully created.", user.getId());
        return user;
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public EmailVerification createEmailVerificationObject(String email) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setNewEmail(email);
        return this.emailVerificationRepository.save(emailVerification);
    }

    @Override
    public AppUser validateEmailAddress(Long id) {
        Preconditions.checkArgument(this.userRepository.existsById(id),
                "The user does not exist.");

        AppUser user = findById(id);

        Preconditions.checkArgument(user.getEmailVerification().getNewEmail() != null,
                "The email address has been validated already.");

        user.setEmail(user.getEmailVerification().getNewEmail());
        user.getEmailVerification().setNewEmail(null);
        user = this.userRepository.save(user);
        log.info("Email address of User with ID {}, was successfully validated.", id);
        return user;
    }

    @Override
    public AppUser findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<AppUser> findByName(String name) {
        return this.userRepository.findBySecondName(name);
    }

    @Override
    public List<AppUser> findAll(String order) {
        if (order.equals("asc")) {
            return this.userRepository.findAll(Sort.by(Sort.Direction.ASC, "secondName"));
        } else if (order.equals("desc")) {
            return this.userRepository.findAll(Sort.by(Sort.Direction.DESC, "secondName"));
        }
        log.error("Order shall be either asc or desc.");
        return this.userRepository.findAll();
    }

    // TODO: Update password once there is authentification
    @Override
    public AppUser update(AppUser user, Long id) {
        Preconditions.checkNotNull(user, "User shall not be null.");
        Preconditions.checkArgument(id == user.getId(), "Id and the id of the user object shall be the same.");
        Preconditions.checkArgument(this.userRepository.existsById(user.getId()), "The user does not exist.");
        Preconditions.checkArgument(user.getEmail() != null, "Email Address needs to be validated before user can be updated.");
        validateEntity(user); // throws e

        this.emailVerificationRepository.save(user.getEmailVerification());
        user = this.userRepository.save(user);
        user.setPassword(null);

        log.info("User with ID {}, was successfully updated.", user.getId());
        return user;
    }

    @Override
    public Long delete(Long id) {
        Preconditions.checkArgument(this.userRepository.existsById(id),
                "The user does not exist.");
        Preconditions.checkArgument(this.bookingRepository.findByAppUser_Id(id).isEmpty(),
                "User can not be deleted as there are still booking(s) related to the user.");

        this.userRepository.deleteById(id);
        log.info("User with ID {}, was successfully deleted.", id);
        return id;
    }
}
