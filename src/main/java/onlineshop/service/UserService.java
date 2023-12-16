package onlineshop.service;

import onlineshop.entity.Product;
import onlineshop.entity.User;
import onlineshop.entity.entityId.UserId;
import onlineshop.entity.exceptions.RemoveFromCartException;
import onlineshop.repository.ShopRepository;
import onlineshop.repository.UserRepository;
import onlineshop.repository.exceptions.UserNotFoundException;
import onlineshop.service.exceptions.CartUpdateException;
import onlineshop.service.exceptions.UserDeleteException;

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
