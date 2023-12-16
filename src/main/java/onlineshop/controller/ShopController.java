package onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import onlineshop.controller.request.PurchaseRequest;
import onlineshop.controller.response.ErrorResponse;
import onlineshop.controller.response.PurchaseResponse;
import onlineshop.entity.entityId.UserId;
import onlineshop.service.ShopService;
import onlineshop.service.exceptions.PurchaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
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
      purchase();
    }

    private void purchase() {
        service.patch(
                "api/shop/purchase",
                (Request request, Response response) -> {
                    response.type("application/json");
                    String body = request.body();
                    PurchaseRequest purchaseRequest = objectMapper.readValue(body, PurchaseRequest.class);
                    try {
                        shopService.purchase(new UserId(purchaseRequest.userId()));
                        LOG.debug("User with id={} made a purchase", purchaseRequest.userId());
                        response.status(201);
                        return objectMapper.writeValueAsString(new PurchaseResponse());
                    } catch(PurchaseException e) {
                        LOG.warn("Purchase failed for user with id={}", purchaseRequest.userId());
                        response.status(400);
                        return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
                    }
                });
    }
}
