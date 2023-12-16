package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryShopRepositoryTest {

  InMemoryShopRepository repository;
  @BeforeEach
  void beforeEach(){
    ObjectMapper objectMapper = new ObjectMapper();
    repository = new InMemoryShopRepository(objectMapper);
  }

  @Test
  void initialisation() {
    HashMap<Product, Integer> correctMap = new HashMap<>(){{
      put(Product.WATER, 1997);
      put(Product.FLOUR, 556);
      put(Product.BREAD, 42);
      put(Product.VODKA, 155037934);
      put(Product.PASTA, 6799);
      put(Product.RICE, 107);
      put(Product.COOKIE, 999);
      put(Product.WAFFLES, 0);
    }};

    assertEquals(correctMap, repository.stock);
  }

  @Test
  void checkProduct() {
    assertEquals(0, repository.checkProduct(Product.WAFFLES));
    assertEquals(155037934, repository.checkProduct(Product.VODKA));
  }
}