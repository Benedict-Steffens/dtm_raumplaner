package project.raumplaner.RaumplanerApp.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.raumplaner.RaumplanerApp.user.model.entity.EmailVerification;

import java.util.List;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    List<EmailVerification> findByNewEmail(String newEmail);
}
