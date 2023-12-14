package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.response.UserCreateResponse;
import org.example.entity.entityId.UserId;
import org.example.service.UserService;
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
}
