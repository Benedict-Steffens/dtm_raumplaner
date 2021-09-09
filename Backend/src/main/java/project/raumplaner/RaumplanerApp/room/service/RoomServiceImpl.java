package project.raumplaner.RaumplanerApp.room.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;
import project.raumplaner.RaumplanerApp.room.model.repository.RoomRepository;

import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl extends RoomValidationServiceImpl implements RoomService {

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           BookingRepository bookingRepository) {
        super(roomRepository, bookingRepository);
    }

    @Override
    public Room create(Room room) {
        Preconditions.checkNotNull(room, "Room shall not be null.");
        Preconditions.checkArgument(room.getCapacity() > 0, "Capacity shall be bigger than 0");
        validateEntity(room); // throws e
        existsByName(room);   // throws e

        room = this.roomRepository.save(room);
        log.info("Room with ID {}, was successfully created.", room.getId());
        return room;
    }

    @Override
    public Room findById(Long id) {
        Preconditions.checkArgument(this.roomRepository.existsById(id),
                "The room does not exist.");

        Room room = this.roomRepository.findById(id).get();
        return room;
    }

    @Override
    public List<Room> findByName(String name) {
        validateString(name, 100);

        return this.roomRepository.findByName(name);
    }

    @Override
    public List<Room> findAll(String order) {
        if (order.equals("asc")) {
            return this.roomRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        }

        if (order.equals("desc")) {
            return this.roomRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        }

        return this.roomRepository.findAll();
    }

    @Override
    public Room update(Room room, Long id) {
        Preconditions.checkNotNull(room, "Room shall not be null.");
        Preconditions.checkArgument(id == room.getId(), "Id and the id of the room object shall be the same.");
        Preconditions.checkArgument(room.getCapacity() > 0, "Capacity shall be bigger than 0");
        Preconditions.checkArgument(this.roomRepository.existsById(room.getId()), "The room does not exist.");
        validateEntity(room); // throws e

        room = this.roomRepository.save(room);
        log.info("Room with ID {}, was successfully updated.", room.getId());
        return room;
    }

    @Override
    public Boolean checkRoomForDependencies(Long id) {
        return !(this.bookingRepository.findByRoom_Id(id).isEmpty());
    }

    @Override
    public Long delete(Long id) {
        Preconditions.checkArgument(this.roomRepository.existsById(id),
                "The room does not exist.");
        Preconditions.checkArgument(this.bookingRepository.findByRoom_Id(id).isEmpty(),
                "Room can not be deleted as there are still booking(s) related to the room.");

        this.roomRepository.deleteById(id);
        log.info("Room with ID {}, was successfully deleted.", id);
        return id;
    }
}
