package project.raumplaner.RaumplanerApp.room.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;
import project.raumplaner.RaumplanerApp.room.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("rooms")
@Slf4j
public class RoomControllerImpl implements RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomControllerImpl(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    @PostMapping
    public Room create(@RequestBody Room room) {
        return this.roomService.create(room);
    }

    @Override
    @GetMapping("{id}")
    public Room findById(@PathVariable Long id) {
        return this.roomService.findById(id);
    }

    @Override
    @GetMapping("search-by-name")
    public List<Room> findByName(@RequestParam() String name) {
        return this.roomService.findByName(name);
    }

    @Override
    @GetMapping
    public List<Room> findAll(@RequestParam String order) {
        return this.roomService.findAll(order);
    }

    @Override
    @GetMapping("has-dependencies")
    public Boolean checkRoomForDependencies(@RequestParam Long id) {
        return this.roomService.checkRoomForDependencies(id);
    }

    @Override
    @PutMapping("{id}")
    public Room update(@RequestBody Room room, @PathVariable Long id) {
        return this.roomService.update(room, id);
    }

    @Override
    @DeleteMapping("{id}")
    public Long delete(@PathVariable Long id) {
        return this.roomService.delete(id);
    }
}
