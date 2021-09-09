package project.raumplaner.RaumplanerApp.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.booking.model.repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class BookingValidationServiceImpl implements BookingValidationService {

    protected final BookingRepository bookingRepository;

    public BookingValidationServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void validateEntity(Booking booking) {
        validateString(booking.getPurpose(), 100);

        if (booking.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The date of the booking shall not be in the past.");
        } else if (!booking.getDate().isEqual(booking.getStartBooking().toLocalDate())) {
            throw new IllegalArgumentException("The date of the booking shall be the same as the date of startBooking.");
        } else if (!booking.getDate().isEqual(booking.getEndBooking().toLocalDate())) {
            throw new IllegalArgumentException("The date of the booking shall be the same as the date of endBooking.");
        } else if (booking.getEndBooking().isBefore(booking.getStartBooking()) || booking.getEndBooking().isEqual(booking.getStartBooking())) {
            throw new IllegalArgumentException("The end of the booking shall be after the beginning of the booking.");
        }

        checkForCollidingBookings(booking);
    }

    @Override
    public void checkForCollidingBookings(Booking booking) {
        List<Long> bookingIds = this.bookingRepository.getCollidingBookings(booking.getStartBooking(), booking.getEndBooking(), booking.getRoom().getId());

        if (bookingIds.size() > 1 || bookingIds.size() == 1 && !bookingIds.contains(booking.getId())) {
            throw new IllegalArgumentException("The room with id " + booking.getRoom().getId() + " is already occupied for the desired period.");
        }
    }
}
