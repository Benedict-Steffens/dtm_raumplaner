package project.raumplaner.RaumplanerApp.room.service;

import project.raumplaner.RaumplanerApp.base.BaseService;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.util.List;

public interface RoomService extends BaseService<Room, Long> {

    /**
     * @param name The name of the room to be found in the database
     * @return The found rooms as type List<Room>
     */
    List<Room> findByName(String name);

    /**
     * @return The found rooms as type List<Room>
     */
    List<Room> findAll(String order);

    /**
     * @param id The id of the room to be checked for dependencies
     * @return If the room with the specific id has dependencies as type Boolean
     */
    Boolean checkRoomForDependencies(Long id);
}
