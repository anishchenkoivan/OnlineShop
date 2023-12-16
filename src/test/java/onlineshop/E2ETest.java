package onlineshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshop.controller.Controller;
import onlineshop.repository.InMemoryShopRepository;
import onlineshop.repository.InMemoryUserRepository;
import onlineshop.service.ShopService;
import onlineshop.service.UserService;
import onlineshop.controller.ShopController;
import onlineshop.controller.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

public class E2ETest {

  private Service service;

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  public void createEditAndPurchase() throws IOException, InterruptedException {

    ObjectMapper objectMapper = new ObjectMapper();

    InMemoryShopRepository shopRepository = new InMemoryShopRepository(objectMapper);
    InMemoryUserRepository userRepository = new InMemoryUserRepository();

    ShopService shopService = new ShopService(shopRepository, userRepository);
    UserService userService = new UserService(shopRepository, userRepository);

    List<Controller> controllers = new ArrayList<>();
    controllers.add(new ShopController(service, shopService, objectMapper));
    controllers.add(new UserController(service, userService, objectMapper));

    Application application = new Application(controllers);
    application.start();
    service.awaitInitialization();


    // Create user
    HttpRequest createUser = HttpRequest.newBuilder()
        .POST(
            HttpRequest.BodyPublishers.ofString(
                """
                    """
            )
        )
        .uri(URI.create("http://localhost:%d/api/user".formatted(service.port())))
        .build();

    HttpResponse<String> responseCreate = HttpClient.newHttpClient()
        .send(
            createUser,
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, responseCreate.statusCode());

    // Update Cart
    HttpRequest updateCart = HttpRequest.newBuilder()
        .method("PATCH",
            HttpRequest.BodyPublishers.ofString(
                """
                    {"product":"BREAD", "amount": 10}
                    """
            )
        )
        .uri(URI.create("http://localhost:%d/api/user/1/addtocart".formatted(service.port())))
        .build();

    HttpResponse<String> responseUpdate = HttpClient.newHttpClient()
        .send(
            updateCart,
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, responseUpdate.statusCode());

    // Purchase
    HttpRequest purchase = HttpRequest.newBuilder()
        .method("PATCH",
            HttpRequest.BodyPublishers.ofString(
                """
                    {"userId": 1}
                    """
            )
        )
        .uri(URI.create("http://localhost:%d/api/shop/purchase".formatted(service.port())))
        .build();

    HttpResponse<String> responsePurchase = HttpClient.newHttpClient()
        .send(
            purchase,
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, responsePurchase.statusCode());
  }
}