package project.raumplaner.RaumplanerApp.base;

import java.util.List;

public interface ExtendedBaseController<E, K> extends BaseController<E, K> {

    /**
     * @param name The name of the entity to be retrieved from the database
     * @return The desired entity with type E
     */
    List<E> findByName(String name);
}
