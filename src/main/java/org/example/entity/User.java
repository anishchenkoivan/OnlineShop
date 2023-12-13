package org.example.entity;

import org.example.entity.entityId.UserId;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
  private final UserId userId;
  private final Map<Products, Integer> cart;
  public User(UserId userId, HashMap<Products, Integer> cart) {
    this.userId = userId;
    this.cart = cart;
  }

  public UserId getUserId() {
    return userId;
  }

  public Map<Products, Integer> getCart() {
    return cart;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId) && Objects.equals(cart, user.cart);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, cart);
  }
}