package org.example.controller.request;

import org.example.entity.Product;

public record AddToCartRequest (Product product, int amount) {
}
