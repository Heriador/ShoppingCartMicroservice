package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Item {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Brand brand;
    private List<Category> categories;
    private String nextSupplyDate;

    public Item() {
    }

    public Item(Long id, String name, String description, BigDecimal price, Integer stock, Brand brand, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getNextSupplyDate() {
        return nextSupplyDate;
    }

    public void setNextSupplyDate(String nextSupplyDate) {
        this.nextSupplyDate = nextSupplyDate;
    }
}
