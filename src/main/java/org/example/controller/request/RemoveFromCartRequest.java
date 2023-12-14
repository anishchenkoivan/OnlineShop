package org.example.controller.request;

import org.example.entity.Product;

public record RemoveFromCartRequest(Product product, int amount) {
}
