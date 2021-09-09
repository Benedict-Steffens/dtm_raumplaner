package project.raumplaner.RaumplanerApp.booking.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;
import project.raumplaner.RaumplanerApp.user.model.entity.AppUser;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Purpose shall not be null")
    private String purpose;

    @FutureOrPresent(message = "Date of booking shall not be in the past")
    private LocalDate date;

    @FutureOrPresent(message = "Beginning of booking shall not be in the past")
    private LocalDateTime startBooking;

    @FutureOrPresent(message = "End of booking shall not be in the past")
    private LocalDateTime endBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NotNull(message = "Room shall not be null")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appUser_id")
    @NotNull(message = "App user shall not be null")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AppUser appUser;
}
