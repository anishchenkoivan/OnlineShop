package org.example.repository;

import org.example.entity.Product;
import org.example.entity.User;

import java.io.FileNotFoundException;

public interface ShopRepository {
    void purchase(User user);
    void initShop() throws FileNotFoundException;
    int checkProduct(Product product);
}
