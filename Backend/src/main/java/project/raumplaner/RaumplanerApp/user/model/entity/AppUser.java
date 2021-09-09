package project.raumplaner.RaumplanerApp.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name shall not be empty")
    private String firstName;

    @NotBlank(message = "Second Name shall not be empty")
    private String secondName;

    @Email(message = "Email shall be valid")
    private String email;

    @Size(min = 8, message = "Password shall have at least eight characters")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @NotNull(message = "Role shall not be null")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emailVerificationId_id")
    @NotNull(message = "EmailVerification shall not be null")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private EmailVerification emailVerification;
}
