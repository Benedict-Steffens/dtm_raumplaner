package project.raumplaner.RaumplanerApp.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;
import project.raumplaner.RaumplanerApp.user.model.entity.EmailVerification;
import project.raumplaner.RaumplanerApp.user.model.repository.EmailVerificationRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.RoleRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@Slf4j
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailVerificationRepository emailVerificationRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleServiceImpl roleServiceImpl;

    @MockBean
    private BookingRepository bookingRepository;

    @Before
    public void setUp() {
        this.userService = new UserServiceImpl(userRepository,
                emailVerificationRepository,
                roleRepository,
                roleServiceImpl,
                bookingRepository,
                passwordEncoder);
    }

    @Test
    public void create_withValidUser_returnsUserObject() {
        AppUser user = Given.validUserToCreate();

        when(userRepository.save(any(AppUser.class))).thenReturn(user);
        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(emailVerificationRepository.findByNewEmail(any(String.class))).thenReturn(Collections.emptyList());
        when(roleServiceImpl.getUserRole()).thenReturn(Given.validUserRole());

        assertEquals(user, userService.create(user));
        verify(roleServiceImpl, times(1)).getUserRole();
        verify(passwordEncoder, times(1)).encode(any(String.class));
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test(expected = NullPointerException.class)
    public void create_withNullUser_throwsException() {
        AppUser user = null;

        userService.create(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_withUserWithInvalidEmailAddress_throwsException() {
        AppUser user = Given.userWithInvalidEMailAddress();

        userService.create(user);
    }

    @Test
    public void createEmailVerificationObject_withValidatedEmailAddress_returnsEmailVerificationObject() {
        String email = Given.validUserToCreate().getEmail();
        EmailVerification emailVerification = Given.validEmailVerification();

        when(emailVerificationRepository.save(any(EmailVerification.class))).thenReturn(emailVerification);

        assertEquals(emailVerification, userService.createEmailVerificationObject(email));
        verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));

    }

    @Test
    public void validateEmailAddress_withIdOfExistingUserWithValidatableEmailAddress_returnsUserWithValidatedEmail() {
        AppUser userWithNonValidatedEmailAddress = Given.userWithNonValidatedEmailAddress();
        AppUser userWithValidatedEmailAddress = Given.userWithValidatedEmailAddress();

        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userWithNonValidatedEmailAddress));
        when(userRepository.save(any(AppUser.class))).thenReturn(userWithValidatedEmailAddress);

        assertEquals(userWithValidatedEmailAddress, userService.validateEmailAddress(userWithValidatedEmailAddress.getId()));
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateEmailAddress_withIdOfNonExistingUser_throwsException() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        userService.validateEmailAddress(userId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateEmailAddress_withIdOfExistingUserWithValidatedEmailAddress_throwsException() {
        Long userId = 1L;
        AppUser userWithValidatedEmailAddress = Given.userWithValidatedEmailAddress();

        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userWithValidatedEmailAddress));

        userService.validateEmailAddress(userId);
    }

    @Test
    public void findById_withExistingId_returnsValidUser() {
        AppUser user = Given.userWithValidatedEmailAddress();

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.existsById(any(Long.class))).thenReturn(true);

        assertEquals(user, userService.findById(user.getId()));
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById_withNonExistingId_throwsException() {
        Long nonExistingUserId = 1L;

        when(userRepository.findById(nonExistingUserId)).thenThrow(EntityNotFoundException.class);

        userService.findById(nonExistingUserId);
    }

    @Test
    public void findByName_withExistingName_returnsValidUser() {
        String userSecondName = Given.validUserToCreate().getSecondName();
        List<AppUser> users = Given.userListWithIdenticalSecondNames();

        when(userRepository.findBySecondName(userSecondName)).thenReturn(users);

        assertEquals(users, userService.findByName(userSecondName));
        verify(userRepository, times(1)).findBySecondName(any(String.class));

    }

    @Test
    public void findByName_withNonExistingName_throwsException() {
        String nonExistingSecondName = "Doey";

        when(userRepository.findBySecondName(nonExistingSecondName)).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), userService.findByName(nonExistingSecondName));
    }

    @Test
    public void findAll_withAscOrder_returnsAllUsersAsSortedList() {
        String order = "asc";
        List<AppUser> users = Given.userListWithDifferentSecondNamesAsc();

        when(userRepository.findAll(Sort.by(Sort.Direction.ASC, "secondName"))).thenReturn(users);

        assertEquals(users, userService.findAll(order));
        verify(userRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "secondName"));
    }

    @Test
    public void findAll_withDescOrder_returnsAllUsersAsSortedList() {
        String order = "desc";
        List<AppUser> users = Given.userListWithDifferentSecondNamesDesc();

        when(userRepository.findAll(Sort.by(Sort.Direction.DESC, "secondName"))).thenReturn(users);

        assertEquals(users, userService.findAll(order));
        verify(userRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "secondName"));

    }

    @Test
    public void findAll_withInvalidOrder_returnsAllUsersSortedById() {
        String order = "123";
        List<AppUser> usersSortedById = Given.userListWithDifferentSecondNamesDesc();

        when(userRepository.findAll()).thenReturn(usersSortedById);

        assertEquals(usersSortedById, userService.findAll(order));
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void update_withValidatedUser_returnsUpdatedUser() {
        AppUser user = Given.userWithValidatedEmailAddress();

        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(emailVerificationRepository.findByNewEmail(any(String.class))).thenReturn(Collections.emptyList());
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.update(user, user.getId()));
        verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test(expected = NullPointerException.class)
    public void update_withNullUser_throwsException() {
        AppUser user = null;
        Long id = 1L;

        userService.update(user, id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_withValidUserAndNotMatchingId_throwsException() {
        AppUser user = Given.userWithValidatedEmailAddress();
        Long notMatchingId = 3L;

        userService.update(user, notMatchingId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_withNonExistingUserId_throwsException() {
        AppUser user = Given.userWithValidatedEmailAddress();

        when(userRepository.existsById(user.getId())).thenReturn(false);

        userService.update(user, user.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_withNonValidatedUser_throwsException() {
        AppUser user = Given.userWithNonValidatedEmailAddress();

        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.update(user, user.getId());
    }

    @Test
    public void delete_withExistingUSerId_returnUserId() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(bookingRepository.findByAppUser_Id(userId)).thenReturn(Collections.emptyList());

        assertEquals(userId, userService.delete(userId));
        verify(userRepository, times(1)).deleteById(any(Long.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_withNonExistingUserId_throwsException() {
        Long userId = 42L;

        when(userRepository.existsById(userId)).thenReturn(false);

        userService.delete(userId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_IdOfUserWithDependency_throwsException() {
        Long idWithDependency = 2L;
        List<Booking> bookings = Given.bookingList();

        when(userRepository.existsById(idWithDependency)).thenReturn(true);
        when(this.bookingRepository.findByAppUser_Id(idWithDependency)).thenReturn(bookings);

        userService.delete(idWithDependency);
    }
}
