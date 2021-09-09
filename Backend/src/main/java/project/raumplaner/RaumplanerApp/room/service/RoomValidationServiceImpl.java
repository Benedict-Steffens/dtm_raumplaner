package project.raumplaner.RaumplanerApp.room.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;
import project.raumplaner.RaumplanerApp.room.model.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RoomValidationServiceImpl implements RoomValidationService {

    protected final RoomRepository roomRepository;
    protected final BookingRepository bookingRepository;

    public RoomValidationServiceImpl(RoomRepository roomRepository,
                                     BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void validateEntity(Room room) {
        Preconditions.checkNotNull(room, "Room shall not be null.");

        validateString(room.getName(), 100);
        validateString(room.getDescription(), 3000);
        validateString(room.getLocation(), 500);
        Preconditions.checkArgument(room.getCapacity() > 0,
                "Capacity shall be bigger than 0");
    }

    @Override
    public void existsByName(Room room) {
        List<Long> roomIds = this.roomRepository.findByName(room.getName())
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        if (!(roomIds.isEmpty() || roomIds.size() == 1 && roomIds.contains(room.getId()))) {
            throw new IllegalArgumentException("This room name exists already. Each room name needs to be unique.");
        }
    }
}
