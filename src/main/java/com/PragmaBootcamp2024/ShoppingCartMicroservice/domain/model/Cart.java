package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartDetails> items;

    public Cart() {
    }

    public Cart(Long id, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt, List<CartDetails> items) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartDetails> getItems() {
        return items;
    }

    public void setItems(List<CartDetails> items) {
        this.items = items;
    }
}
