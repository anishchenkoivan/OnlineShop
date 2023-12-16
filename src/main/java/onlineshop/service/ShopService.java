package onlineshop.service;

import onlineshop.entity.Product;
import onlineshop.entity.entityId.UserId;
import onlineshop.repository.exceptions.UserNotFoundException;
import onlineshop.entity.User;
import onlineshop.repository.ShopRepository;
import onlineshop.repository.UserRepository;
import onlineshop.service.exceptions.PurchaseException;

public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopService(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    public synchronized void purchase(UserId userId) {
        User user;
        try {
            user = userRepository.getUserById(userId);
        } catch (UserNotFoundException e) {
            throw new PurchaseException("Couldn't find user with id=" + userId, e);
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
