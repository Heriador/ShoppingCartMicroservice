package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model;

public class CartDetails {
    private Long id;
    private Long cartId;
    private Long itemId;
    private Integer quantity;

    public CartDetails() {
    }

    public CartDetails(Long id, Long cartId, Long itemId, Integer quantity) {
        this.id = id;
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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
