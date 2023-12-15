package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.request.AddToCartRequest;
import org.example.controller.request.RemoveFromCartRequest;
import org.example.controller.response.*;
import org.example.entity.entityId.UserId;
import org.example.service.UserService;
import org.example.service.exceptions.CartUpdateException;
import org.example.service.exceptions.UserDeleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

public class UserController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
  private final Service service;
  private final UserService userService;
  private final ObjectMapper objectMapper;

  public UserController(Service service, UserService userService, ObjectMapper objectMapper) {
    this.service = service;
    this.userService = userService;
    this.objectMapper = objectMapper;
  }


  @Override
  public void initializeEndpoints() {
    createUser();
    deleteUser();
    addToCart();
    removeFromCart();
  }

  private void createUser() {
    service.post(
        "/api/user",
        (Request request, Response response) -> {
          response.type("application/json");
          UserId id = userService.createUser();
          LOG.debug("User with id={} created", id);
          response.status(201);
          return objectMapper.writeValueAsString(new UserCreateResponse(id));
        }
    );
  }

  private void deleteUser() {
    service.delete(
        "api/user/:userId",
        (Request request, Response response) -> {
          response.type("application/json");
          UserId id = new UserId(Long.parseLong(request.params("userId")));
          try {
            userService.deleteUser(id);
            LOG.debug("User with id={} deleted", id);
            response.status(204);
            return objectMapper.writeValueAsString(new UserDeleteResponse());
          } catch (UserDeleteException e) {
            LOG.warn("couldn't delete user with id={}", id);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void addToCart() {
    service.patch(
        "api/user/:userId/addtocart",
        (Request request, Response response) -> {
          response.type("application/json");
          UserId id = new UserId(Long.parseLong(request.params("userId")));
          String body = request.body();
          AddToCartRequest addToCartRequest = objectMapper.readValue(body, AddToCartRequest.class);
          try {
            userService.addToCart(id, addToCartRequest.product(), addToCartRequest.amount());
            LOG.debug("Added {} items of type {} to the user with id={}", addToCartRequest.amount(), addToCartRequest.product(), id);
            response.status(201);
            return objectMapper.writeValueAsString(new AddToCartResponse());
          } catch (CartUpdateException e) {
            LOG.warn("Failed to updated the cart of user with id={}", id);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void removeFromCart() {
    service.patch(
        "api/user/:userId/removefromcart",
        (Request request, Response response) -> {
          response.type("application/json");
          UserId id = new UserId(Long.parseLong(request.params("userId")));
          String body = request.body();
          RemoveFromCartRequest removeFromCartRequest = objectMapper.readValue(body, RemoveFromCartRequest.class);
          try {
            userService.addToCart(id, removeFromCartRequest.product(), removeFromCartRequest.amount());
            LOG.debug("deleted {} items of type {} from the user with id={}", removeFromCartRequest.amount(), removeFromCartRequest.product(), id);
            response.status(201);
            return objectMapper.writeValueAsString(new RemoveFromCartResponse());
          } catch (CartUpdateException e) {
            LOG.warn("Failed to updated the cart of user with id={}", id);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }
}
