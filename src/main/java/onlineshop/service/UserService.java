package org.example.service;

import org.example.entity.Product;
import org.example.entity.User;
import org.example.entity.entityId.UserId;
import org.example.entity.exceptions.RemoveFromCartException;
import org.example.repository.ShopRepository;
import org.example.repository.UserRepository;
import org.example.repository.exceptions.UserNotFoundException;
import org.example.service.exceptions.CartUpdateException;
import org.example.service.exceptions.UserDeleteException;

public class UserService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public UserService(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    public UserId createUser() {
        return userRepository.createUser();
    }

    public void deleteUser(UserId userId) {
        try {
            userRepository.deleteUser(userId);
        } catch (UserNotFoundException e) {
            throw new UserDeleteException("Couldn't find user with id=" + userId, e);
        }
    }

    public void addToCart(UserId id, Product product, int amount) {
        User user;
        try {
            user = userRepository.getUserById(id);
        } catch (RuntimeException e) {
            throw new CartUpdateException("Couldn't find user with id=" + id, e);
        }

        if (shopRepository.checkProduct(product) < amount) {
            throw new CartUpdateException("Not enough products of type " + product);
        }

        user.addToCart(product, amount);
    }

    public void removeFromCart(UserId id, Product product, int amount) {
        User user;
        try {
            user = userRepository.getUserById(id);
        } catch (RuntimeException e) {
            throw new CartUpdateException("Couldn't find user with id=" + id, e);
        }

        if (user.getCart().getOrDefault(product, 0) < amount) {
            throw new CartUpdateException("Not enough products in the cart of type " + product);
        }

        try {
            user.removeFromCart(product, amount);
        } catch (RemoveFromCartException e) {
            throw new CartUpdateException("Not enough products in the cart of type " + product, e);
        }
    }
}
