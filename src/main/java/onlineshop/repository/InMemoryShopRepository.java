package onlineshop.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshop.entity.Product;
import onlineshop.entity.User;
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
      stock.put(key, stock.get(key) - user.getCart().get(key));
    }
  }

  @Override
  public synchronized void initShop() {
    try {
      Reader reader = new FileReader("src/main/resources/config.json", StandardCharsets.UTF_8);
      Map<String, Integer> productsMap = objectMapper.readValue(reader, HashMap.class);

      for (Product p : Product.values()) {
        stock.put(p, productsMap.getOrDefault(p.toString(), 0));
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
