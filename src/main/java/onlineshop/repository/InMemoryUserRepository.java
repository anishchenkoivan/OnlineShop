package onlineshop.repository;

import onlineshop.repository.exceptions.UserNotFoundException;
import onlineshop.entity.User;
import onlineshop.entity.entityId.UserId;

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
      throw new UserNotFoundException("There is no user with id " + userId);
    } else {
      return usersMap.get(userId);
    }
  }

  @Override
  public UserId createUser() {
    UserId userId = generateId();
    User user = new User(userId, new HashMap<>());
    usersMap.put(userId, user);
    return userId;
  }

  @Override
  public void deleteUser(UserId userId) {
    if (!usersMap.containsKey(userId)) {
      throw new UserNotFoundException("There is no user with id " + userId);
    } else {
      usersMap.remove(userId);
    }
  }
}
