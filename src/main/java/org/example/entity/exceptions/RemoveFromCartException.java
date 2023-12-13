package org.example.entity.exceptions;

public class RemoveFromCartException extends RuntimeException {
  public RemoveFromCartException(String message) {
    super(message);
  }
}
