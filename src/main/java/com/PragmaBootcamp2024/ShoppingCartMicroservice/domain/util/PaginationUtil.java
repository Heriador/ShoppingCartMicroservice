package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util;

public class PaginationUtil {

    private Integer page;
    private Integer size;
    private Boolean order;
    private String filterByBrandName;
    private String filterByCategoryName;

    public PaginationUtil() {
    }

    public PaginationUtil(Integer page, Integer size, Boolean order, String filterByBrandName, String filterByCategoryName) {
        this.page = page;
        this.size = size;
        this.order = order;
        this.filterByBrandName = filterByBrandName;
        this.filterByCategoryName = filterByCategoryName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public String getFilterByBrandName() {
        return filterByBrandName;
    }

    public void setFilterByBrandName(String filterByBrandName) {
        this.filterByBrandName = filterByBrandName;
    }

    public String getFilterByCategoryName() {
        return filterByCategoryName;
    }

    public void setFilterByCategoryName(String filterByCategoryName) {
        this.filterByCategoryName = filterByCategoryName;
    }
}
