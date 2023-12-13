package org.example.repository;

import org.example.entity.User;
import org.example.entity.entityId.UserId;

public interface UserRepository {
    UserId generateId();
    User getUserById(UserId userId);
    UserId createUser();
    void deleteUser(UserId userId);
}
