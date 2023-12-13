package org.example.entity;

import org.example.entity.entityId.UserId;
import org.example.entity.exceptions.RemoveFromCartException;

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

  public void addToCart(Products product, int value) {
    if (cart.containsKey(product)) {
      cart.put(product, cart.get(product) + value);
    } else {
      cart.put(product, value);
    }
  }

  public void removeFromCart(Products product, int value) {
    if (!cart.containsKey(product) || cart.get(product) - value < 0) {
      throw new RemoveFromCartException("There is not enough product.");
    }
    if (cart.get(product) - value == 0) {
      cart.remove(product);
    } else {
      cart.put(product, cart.get(product) - value);
    }
  }
}
