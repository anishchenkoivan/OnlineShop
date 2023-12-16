package onlineshop.entity.exceptions;

public class RemoveFromCartException extends RuntimeException {
  public RemoveFromCartException(String message) {
    super(message);
  }
}
