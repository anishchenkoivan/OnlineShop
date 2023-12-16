package onlineshop.repository;

import onlineshop.entity.Product;
import onlineshop.entity.User;

import java.io.FileNotFoundException;

public interface ShopRepository {
    void purchase(User user);
    void initShop() throws FileNotFoundException;
    int checkProduct(Product product);
}
