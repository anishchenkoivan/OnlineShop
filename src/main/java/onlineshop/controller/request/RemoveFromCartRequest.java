package onlineshop.controller.request;

import onlineshop.entity.Product;

public record RemoveFromCartRequest(Product product, int amount) {
}
