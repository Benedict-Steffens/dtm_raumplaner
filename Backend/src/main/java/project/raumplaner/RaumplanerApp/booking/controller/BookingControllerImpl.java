package project.raumplaner.RaumplanerApp.booking.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import project.raumplaner.RaumplanerApp.booking.model.entity.Booking;
import project.raumplaner.RaumplanerApp.booking.service.BookingService;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("bookings")
public class BookingControllerImpl implements BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingControllerImpl(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return this.bookingService.create(booking);
    }

    @Override
    @GetMapping("{id}")
    public Booking findById(@PathVariable Long id) {
        return this.bookingService.findById(id);
    }

    @Override
    @GetMapping("get-room-occupation")
    public Long calcRoomOccupationInMinutes(@RequestParam() Long roomId) {
        return this.bookingService.calcRoomOccupationInMinutes(roomId);
    }

    @Override
    @GetMapping("by-date")
    public List<Booking> findByDate(@RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            LocalDate date) {
        return this.bookingService.findByDate(date);
    }

    @Override
    @PutMapping("{id}")
    public Booking update(@RequestBody Booking booking, @PathVariable Long id) {
        return this.bookingService.update(booking, id);
    }

    @Override
    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        return this.bookingService.delete(id);
    }

    @Override
    @GetMapping("available-rooms")
    public List<Room> findAvailableRooms(@RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                         @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return this.bookingService.findAvailableRooms(start, end);
    }

    @Override
    @GetMapping("room-availability")
    public Boolean isRoomAvailable(@RequestParam() Long roomId,
                                   @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startBooking,
                                   @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endBooking) {
        return this.bookingService.getRoomAvailability(roomId, startBooking, endBooking);
    }
}
