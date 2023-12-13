package org.example.service;

import org.example.entity.Product;
import org.example.entity.User;
import org.example.entity.entityId.UserId;
import org.example.repository.ShopRepository;
import org.example.repository.UserRepository;
import org.example.service.exceptions.PurchaseException;
import org.example.service.exceptions.UserNotFoundException;

public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopService(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    synchronized void purchase(UserId userId) {
        User user;
        try {
            user = userRepository.getUserById(userId);
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Couldn't find user with id=" + userId, e);
        }

        for (var item : user.getCart().entrySet()) {
            Product product = item.getKey();
            int amount = item.getValue();
            if (shopRepository.checkProduct(product) - amount < 0) {
                throw new PurchaseException("Not enough products of type " + product);
            }
        }

        shopRepository.purchase(user);
        user.clearCart();
    }
}
