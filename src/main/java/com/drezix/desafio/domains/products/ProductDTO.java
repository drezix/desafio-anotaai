package com.drezix.desafio.domains.products;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
