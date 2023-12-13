package org.example.repository;

import org.example.entity.Products;
import org.example.entity.User;
import org.example.entity.entityId.UserId;

public interface UserRepository {
    UserId createUser();

    void deleteUser();

    void addToCart(User user, Products product, int amount);

    void RemoveFromCart(User user, Products product, int amount);
}
