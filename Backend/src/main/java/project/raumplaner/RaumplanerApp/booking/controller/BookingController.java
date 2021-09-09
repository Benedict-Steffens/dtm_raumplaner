package project.raumplaner.RaumplanerApp.booking.controller;

import project.raumplaner.RaumplanerApp.base.BaseController;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingController extends BaseController<Booking, Long> {

    /**
     * @param start The start time of a date to find a suitable room for in the database
     * @param end   The end time of a date to find a suitable room for in the database
     * @return A list with available rooms for the chosen date with type List<Room>
     */
    List<Room> findAvailableRooms(LocalDateTime start, LocalDateTime end);

    /**
     * @param date The date all registered bookings shall be retrieved from the database
     * @return A list with bookings taking place at the defined date with type List<Booking>
     */
    List<Booking> findByDate(LocalDate date);


    /**
     * @param id The id of the room to be checked for occupation time
     * @return Remaining minutes the room is occupied with type Long
     */
    Long calcRoomOccupationInMinutes(Long roomId);

    /**
     * @param roomId       The id of the room to be checked for availability in specified time
     * @param startBooking The start date and start time of the availability check
     * @param endBooking   The end date and end time of the availability check
     * @return If the room is available in the specified time period
     */
    Boolean isRoomAvailable(Long roomId, LocalDateTime startBooking, LocalDateTime endBooking);
}
