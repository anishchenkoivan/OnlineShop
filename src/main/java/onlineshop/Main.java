package onlineshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshop.repository.InMemoryShopRepository;
import onlineshop.repository.InMemoryUserRepository;
import onlineshop.controller.ShopController;
import onlineshop.controller.UserController;
import onlineshop.repository.ShopRepository;
import onlineshop.repository.UserRepository;
import onlineshop.service.ShopService;
import onlineshop.service.UserService;
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