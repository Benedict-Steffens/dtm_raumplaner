package project.raumplaner.RaumplanerApp.base;

public interface BaseController<E, K> {

    /**
     * @param e The entity to be added to the database
     * @return The added entity with type E
     */
    E create(E e);

    /**
     * @param k The id of the entity to be retrieved from the database
     * @return The desired entity with type E
     */
    E findById(K k);

    /**
     * @param e The entity to be updated
     * @param k The id of the entity to be updated
     * @return The updated entity with type E
     */
    E update(E e, K k);

    /**
     * @param k The id of the entity to be deleted
     * @return The id of the deleted entity as K
     */
    K delete(K k);
}
