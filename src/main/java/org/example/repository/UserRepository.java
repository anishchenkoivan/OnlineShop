package org.example.repository;

import org.example.entity.User;

public interface UserRepository {
    UserId createUser();

    void deleteUser();

    void addToCart(User user, Products product, int amount);

    void RemoveFromCart(User user, Products product, int amount);
}
