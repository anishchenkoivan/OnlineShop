package onlineshop.service.exceptions;

public class UserDeleteException extends RuntimeException {
    public UserDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
