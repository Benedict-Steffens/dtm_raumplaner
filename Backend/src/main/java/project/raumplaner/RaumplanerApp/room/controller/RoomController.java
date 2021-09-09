package project.raumplaner.RaumplanerApp.room.controller;

import project.raumplaner.RaumplanerApp.base.ExtendedBaseController;
import project.raumplaner.RaumplanerApp.room.model.entity.Room;

import java.util.List;

public interface RoomController extends ExtendedBaseController<Room, Long> {

    /**
     * @param order The order in which all rooms shall be retrieved from the database by their name
     * @return A list with all rooms, ordered by desired order with type List<Room>
     */
    List<Room> findAll(String order);

    /**
     * @param id The id of the room to be checked for dependencies
     * @return If the room with the specific id has dependencies as type Boolean
     */
    Boolean checkRoomForDependencies(Long id);
}
