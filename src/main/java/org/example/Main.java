package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.ShopController;
import org.example.controller.UserController;
import org.example.repository.InMemoryShopRepository;
import org.example.repository.InMemoryUserRepository;
import org.example.repository.ShopRepository;
import org.example.repository.UserRepository;
import org.example.service.ShopService;
import org.example.service.UserService;
import spark.Service;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Service service = Service.ignite();
        ObjectMapper objectMapper = new ObjectMapper();
        ShopRepository shopRepository = new InMemoryShopRepository(objectMapper);
        UserRepository userRepository = new InMemoryUserRepository();
        UserService articleService = new UserService(shopRepository, userRepository);
        ShopService shopService = new ShopService(shopRepository, userRepository);
        Application application = new Application(List.of(new UserController(service, articleService, objectMapper), new ShopController(service, shopService, objectMapper)));
        application.start();
    }
}