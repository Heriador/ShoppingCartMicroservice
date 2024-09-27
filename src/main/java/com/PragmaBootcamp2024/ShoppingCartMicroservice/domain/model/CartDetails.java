package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model;

public class CartDetails {
    private Long id;
    private Cart cart;
    private Long itemId;
    private Integer quantity;

    public CartDetails() {
    }

    public CartDetails(Long id, Cart cart, Long itemId, Integer quantity) {
        this.id = id;
        this.cart = cart;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
