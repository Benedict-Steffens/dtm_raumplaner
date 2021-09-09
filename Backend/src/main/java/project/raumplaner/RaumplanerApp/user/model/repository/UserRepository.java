package project.raumplaner.RaumplanerApp.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findBySecondName(String name);

    AppUser findByEmail(String email);

    List<AppUser> findAllByEmail(String email);
}
