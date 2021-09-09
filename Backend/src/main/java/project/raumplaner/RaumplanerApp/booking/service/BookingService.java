package project.raumplaner.RaumplanerApp.booking.service;

import project.raumplaner.RaumplanerApp.base.BaseService;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService extends BaseService<Booking, Long> {

    /**
     * @param date The date of the entity to be found in the database
     * @return All bookings for the certain date with type List<Bookings>
     */
    List<Booking> findByDate(LocalDate date);

    /**
     * @param startBooking The start time of the desired time lapse
     * @param endBooking   The end time of the desired time lapse
     * @return All available rooms for the desired time lapse with type List<Room>
     */
    List<Room> findAvailableRooms(LocalDateTime startBooking, LocalDateTime endBooking);

    /**
     * @param id The id of the room to be checked for occupation time
     * @return Remaining minutes the room is occupied with type Long
     */
    Long calcRoomOccupationInMinutes(Long id);

    /**
     * @param roomId   The id of the room to be checked for occupation time
     * @param baseTime The date and time after which the next booking shall be found to calculate the time difference
     * @return Remaining minutes the room is occupied with type Long
     */
    Long calcTimeToNextBookingByRoom(Long roomId, LocalDateTime baseTime);


    /**
     * @param roomId       The id of the room to be checked for availability in specified time
     * @param startBooking The start date and start time of the availability check
     * @param endBooking   The end date and end time of the availability check
     * @return If the room is available in the specified time period
     */
    Boolean getRoomAvailability(Long roomId, LocalDateTime startBooking, LocalDateTime endBooking);
}
