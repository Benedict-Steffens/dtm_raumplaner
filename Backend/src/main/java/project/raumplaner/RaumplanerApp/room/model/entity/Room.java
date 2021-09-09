package project.raumplaner.RaumplanerApp.room.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Name shall not be empty")
    private String name;

    @NotBlank(message = "Description shall not be empty")
    @Lob
    private String description;

    @NotBlank(message = "Location shall not be empty")
    private String location;

    @Min(value = 1, message = "Capacity shall not be less than one")
    private int capacity;


}
