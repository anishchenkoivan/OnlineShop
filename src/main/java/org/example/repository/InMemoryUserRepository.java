package org.example.repository;

import org.example.entity.User;
import org.example.entity.entityId.UserId;

import org.example.repository.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository implements UserRepository{

  private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);

  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<UserId, User> usersMap = new ConcurrentHashMap<>();

  @Override
  public UserId generateId() {
    return new UserId(nextId.incrementAndGet());
  }

  @Override
  public User getUserById(UserId userId) {
    if (!usersMap.containsKey(userId)) {
      throw new UserNotFoundException("There is not exist user with id " + userId);
    } else {
      LOG.debug("User was found by id: " + userId);
      return usersMap.get(userId);
    }
  }

  @Override
  public UserId createUser() {
    UserId userId = generateId();
    User user = new User(userId, new HashMap<>());
    usersMap.put(userId, user);
    LOG.debug("User was created with id: " + userId);
    return userId;
  }

  @Override
  public void deleteUser(UserId userId) {
    if (!usersMap.containsKey(userId)) {
      throw new UserNotFoundException("There is not exist user with id " + userId);
    } else {
      LOG.debug("User was deleted by id: " + userId);
      usersMap.remove(userId);
    }
  }
}
