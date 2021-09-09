package project.raumplaner.RaumplanerApp.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.model.repository.EmailVerificationRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserValidationServiceImpl implements UserValidationService {

    protected final UserRepository userRepository;
    protected final EmailVerificationRepository emailVerificationRepository;
    protected final BookingRepository bookingRepository;

    public UserValidationServiceImpl(UserRepository userRepository,
                                     EmailVerificationRepository emailVerificationRepository,
                                     BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.bookingRepository = bookingRepository;
    }

    // TODO: password -- to be done when complete authorization
    @Override
    public void validateEntity(AppUser user) {
        validateString(user.getFirstName(), 100);
        validateString(user.getSecondName(), 100);

        existsByEmail(user);
    }

    @Override
    public void existsByEmail(AppUser user) {
        List<Long> userIds = this.userRepository.findAllByEmail(user.getEmail())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        List<Long> emailVerificationIds = this.emailVerificationRepository.findByNewEmail(user.getEmail())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        if (userIds.size() > 1 || userIds.size() == 1 && !userIds.contains(user.getId())) {
            throw new IllegalArgumentException("This email address has been registered already.");
        } else if (emailVerificationIds.size() > 1 || emailVerificationIds.size() == 1 &&
                !emailVerificationIds.contains(user.getEmailVerification().getId())) {
            throw new IllegalArgumentException("This email address has been registered already, but has not been validated yet.");
        }
    }
}
