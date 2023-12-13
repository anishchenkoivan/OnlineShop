package org.example.repository;

import org.example.entity.Products;
import org.example.entity.User;
import org.example.entity.entityId.UserId;

import java.util.concurrent.atomic.AtomicLong;

public interface UserRepository {
    UserId generateId();
    User getUserById(UserId userId);
    User createUser();
    void deleteUser();
}
