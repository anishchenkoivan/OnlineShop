package org.example.repository;

import org.example.entity.Products;
import org.example.entity.User;
import org.example.entity.entityId.UserId;

public class InMemoryUserRepository implements UserRepository{


  @Override
  public UserId generateId() {
    return null;
  }

  @Override
  public User getUserById(UserId userId) {
    return null;
  }

  @Override
  public User createUser() {
    return null;
  }

  @Override
  public void deleteUser() {

  }
}
