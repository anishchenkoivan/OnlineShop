package org.example.repository;

import org.example.entity.User;

public interface ShopRepository {
    void purchase(User user);

    void initShop();
}
