package project.raumplaner.RaumplanerApp.booking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByDate(LocalDate date);

    @Query("select room from Room room where room.id not in " +
            "(select distinct room.id from Booking booking inner join Room room on booking.room.id = room.id " +
            "where (:startBooking <= booking.endBooking and :endBooking >= booking.startBooking))")
    List<Room> findAvailableRooms(@Param("startBooking") LocalDateTime startBooking, @Param("endBooking") LocalDateTime endBooking);

    @Query("select booking.id from Booking booking inner join Room room on booking.room.id = room.id " +
            "where (:startBooking <= booking.endBooking and :endBooking >= booking.startBooking and :roomId = room.id)")
    List<Long> getCollidingBookings(@Param("startBooking") LocalDateTime startBooking, @Param("endBooking") LocalDateTime endBooking, @Param("roomId") Long roomId);

    @Query("select booking from Booking booking inner join Room room on booking.room.id = room.id " +
            "where (:currentTime <= booking.endBooking and :currentTime >= booking.startBooking and :roomId = room.id)")
    List<Booking> getCurrentBookingByRoomId(@Param("currentTime") LocalDateTime currentTime, @Param("roomId") Long roomId);

    List<Booking> findByAppUser_Id(Long appUserId);

    List<Booking> findByRoom_Id(Long roomId);

    List<Booking> findByRoom_IdAndStartBookingAfter(Long roomId, @Param("currentTime") LocalDateTime currentTime);

}

