package onlineshop.service.exceptions;

public class PurchaseException extends RuntimeException {
    public PurchaseException(String message) {
        super(message);
    }

    public PurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
