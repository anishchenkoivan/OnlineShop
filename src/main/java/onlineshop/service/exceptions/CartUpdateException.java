package onlineshop.service.exceptions;

public class CartUpdateException extends RuntimeException {
    public CartUpdateException(String message) {
        super(message);
    }

    public CartUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
