package org.example.entity;

import org.example.entity.entityId.UserId;

import java.util.HashMap;
import java.util.Map;

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
}