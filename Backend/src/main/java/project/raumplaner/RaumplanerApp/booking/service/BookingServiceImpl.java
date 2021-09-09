package project.raumplaner.RaumplanerApp.booking.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;
import project.raumplaner.RaumplanerApp.room.model.repository.RoomRepository;
import project.raumplaner.RaumplanerApp.user.model.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl extends BookingValidationServiceImpl implements BookingService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository,
                              UserRepository userRepository) {
        super(bookingRepository);
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Booking create(Booking booking) {
        Preconditions.checkNotNull(booking, "Booking shall not be null.");
        validateEntity(booking);  // throws e

        booking = this.bookingRepository.save(booking);
        log.info("Booking with ID {}, was successfully created.", booking.getId());
        return booking;
    }

    @Override
    public Booking findById(Long id) {
        Preconditions.checkNotNull(id, "ID shall not be null.");
        Preconditions.checkArgument(this.bookingRepository.existsById(id), "The booking does not exist.");

        return this.bookingRepository.findById(id).get();
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        Preconditions.checkNotNull(date, "Date shall not be null.");

        return this.bookingRepository.findByDate(date);
    }

    @Override
    public Booking update(Booking booking, Long id) {
        Preconditions.checkNotNull(booking, "Booking shall not be null.");
        Preconditions.checkArgument(id == booking.getId(), "Id and the id of the booking object shall be the same.");
        Preconditions.checkArgument(this.bookingRepository.existsById(booking.getId()), "The booking does not exist.");
        validateEntity(booking);  // throws e

        booking = this.bookingRepository.save(booking);
        log.info("Booking with ID {}, was successfully updated.", booking.getId());
        return booking;
    }

    @Override
    public Long delete(Long id) {
        Preconditions.checkNotNull(id, "ID shall not be null.");
        Preconditions.checkArgument(this.bookingRepository.existsById(id),
                "The booking does not exist.");

        this.bookingRepository.delete(this.bookingRepository.findById(id).get());
        log.info("Booking with ID {}, was successfully deleted.", id);
        return id;
    }

    @Override
    public List<Room> findAvailableRooms(LocalDateTime startBooking, LocalDateTime endBooking) {
        Preconditions.checkArgument(startBooking.isBefore(endBooking), "The end of the booking shall be after the beginning of the booking.");

        return this.bookingRepository.findAvailableRooms(startBooking, endBooking);
    }

    @Override
    public Long calcRoomOccupationInMinutes(Long roomId) {
        Preconditions.checkNotNull(roomId, "ID shall not be null.");
        Preconditions.checkArgument(this.roomRepository.existsById(roomId), "The room with ID {} does not exist.", roomId);

        LocalDateTime currentTime = LocalDateTime.now();

        List<Booking> collidingBookings = this.bookingRepository.getCurrentBookingByRoomId(currentTime, roomId);

        if (collidingBookings.isEmpty()) {
            return calcTimeToNextBookingByRoom(roomId, currentTime);
        }

        return Duration.between(LocalDateTime.now(), collidingBookings.get(0).getEndBooking()).toMinutes();
    }

    @Override
    public Long calcTimeToNextBookingByRoom(Long roomId, LocalDateTime baseTime) {
        Optional<Booking> nextBooking = this.bookingRepository.findByRoom_IdAndStartBookingAfter(roomId, baseTime)
                .stream()
                .reduce((booking, booking2) ->
                        booking.getStartBooking().isBefore(booking2.getStartBooking()) ? booking : booking2);

        if (nextBooking.isEmpty()) {
            return Long.MIN_VALUE;
        }
        return -1L * Duration.between(baseTime, nextBooking.get().getStartBooking()).toMinutes();
    }

    @Override
    public Boolean getRoomAvailability(Long roomId, LocalDateTime startBooking, LocalDateTime endBooking) {
        List<Booking> collidingBookings = this.bookingRepository.getCurrentBookingByRoomId(startBooking, roomId);
        if (collidingBookings.size() > 0) {
            return false;
        }

        Long minutesToNextBooking = calcTimeToNextBookingByRoom(roomId, startBooking);
        if (minutesToNextBooking == Long.MIN_VALUE) {
            minutesToNextBooking += 1;
        }
        minutesToNextBooking = minutesToNextBooking * -1;

        if (minutesToNextBooking < 0) {
            return false;
        }

        Long differenceStartEndBooking = Duration.between(startBooking, endBooking).toMinutes();
        return differenceStartEndBooking <= minutesToNextBooking;
    }
}
