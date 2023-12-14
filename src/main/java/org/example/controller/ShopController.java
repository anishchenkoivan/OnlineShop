package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

public class ShopController implements Controller {
    private static final Logger LOG = LoggerFactory.getLogger(ShopController.class);
    private final Service service;
    private final ShopService shopService;
    private final ObjectMapper objectMapper;

    public ShopController(Service service, ShopService shopService, ObjectMapper objectMapper) {
        this.service = service;
        this.shopService = shopService;
        this.objectMapper = objectMapper;
    }


    @Override
    public void initializeEndpoints() {
    }
}
