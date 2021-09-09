package project.raumplaner.RaumplanerApp.base;

public interface BaseValidationService<E, K> {

    /**
     * @param e the entity to be validated
     * @throws IllegalArgumentException in case of invalid input or NullpointerException in case of null values
     */
    void validateEntity(E e);

    /**
     * @param string    is the String to be validated
     * @param maxLength is the maximum number of characters the string shall have
     * @throws IllegalArgumentException in case of invalid input or NullpointerException in case of null values
     */
    default void validateString(String string, int maxLength) {
        if (string == null) {
            throw new IllegalArgumentException("The string shall not be null.");
        } else if (string.isBlank()) {
            throw new IllegalArgumentException("The string shall not be empty");
        } else if (string.length() > maxLength) {
            throw new IllegalArgumentException("The string shall not have more than" + maxLength + "characters");
        }
    }
}
