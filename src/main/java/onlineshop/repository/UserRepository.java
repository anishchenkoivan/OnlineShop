package onlineshop.repository;

import onlineshop.entity.User;
import onlineshop.entity.entityId.UserId;

public interface UserRepository {
    UserId generateId();
    User getUserById(UserId userId);
    UserId createUser();
    void deleteUser(UserId userId);
}
