package project.raumplaner.RaumplanerApp.booking.service;

import project.raumplaner.RaumplanerApp.base.BaseValidationService;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;

public interface BookingValidationService extends BaseValidationService<Booking, Long> {

    /**
     * @param booking The booking to check, if the booked time lapse and room does not collide with another booking.
     * @throws IllegalArgumentException in case that the room of the booking to be checked is already occupied
     */
    void checkForCollidingBookings(Booking booking);
}
