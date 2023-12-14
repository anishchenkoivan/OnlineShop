package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Product;
import org.example.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class InMemoryShopRepository implements ShopRepository {
  private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepository.class);

  ObjectMapper objectMapper;
  Map<Product, Integer> stock = new HashMap<>();

  public InMemoryShopRepository(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    initShop();
  }

  @Override
  public synchronized void purchase(User user) {
    for (Product key : user.getCart().keySet()) {
      LOG.info("User with id " + user.getUserId() + " bought " + user.getCart().get(key) + " " + key.getUnit());
    }
    user.clearCart();
  }

  @Override
  public synchronized void initShop() {
    try {
      Reader reader = new FileReader("src/main/resources/config.json", StandardCharsets.UTF_8);
      Map<String, Integer> productsMap = objectMapper.readValue(reader, HashMap.class);

      for (Product p : Product.values()) {
        stock.put(p, p.getValue());
      }

      for (String p : productsMap.keySet()) {
        stock.put(Product.valueOf(p), productsMap.get(p));
        LOG.debug("Was added " + productsMap.get(p) + " of " + p);
      }
    } catch (IOException e) {
      LOG.warn("Config file not found");
    }
  }

  @Override
  public synchronized int checkProduct(Product product) {
    return stock.getOrDefault(product, 0);
  }
}
