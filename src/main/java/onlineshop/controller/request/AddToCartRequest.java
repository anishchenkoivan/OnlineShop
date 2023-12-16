package onlineshop.controller.request;

import onlineshop.entity.Product;

public record AddToCartRequest (Product product, int amount) {
}
