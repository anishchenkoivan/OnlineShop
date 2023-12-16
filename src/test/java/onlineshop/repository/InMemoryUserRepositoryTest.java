package onlineshop.repository;

import onlineshop.entity.User;
import onlineshop.entity.entityId.UserId;
import onlineshop.repository.exceptions.UserNotFoundException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserRepositoryTest {
  InMemoryUserRepository repository = new InMemoryUserRepository();

  @Test
  public void createUser() {
    UserId userId = repository.createUser();
    assertEquals(1, userId.id());
  }
  @Test
  public void findAndDeleteUser() {
    UserId userId = repository.createUser();

    User user = repository.getUserById(userId);
    assertEquals(1, user.getUserId().id());

    repository.deleteUser(userId);

    assertThrows(UserNotFoundException.class, () -> repository.getUserById(userId));
  }
}
