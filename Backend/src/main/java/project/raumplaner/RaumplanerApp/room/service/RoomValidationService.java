package project.raumplaner.RaumplanerApp.room.service;

import project.raumplaner.RaumplanerApp.base.BaseValidationService;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

public interface RoomValidationService extends BaseValidationService<Room, Long> {

    /**
     * @param room The room of which the name shall be checked, if it exists in the database already
     * @throws IllegalArgumentException in case of another room in the database having the same name as the room to be checked
     */
    void existsByName(Room room);
}
