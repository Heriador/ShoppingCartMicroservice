package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.controller;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.DeleteResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.ItemCartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.PaginationResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler.ICartHandler;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util.RestControllerConstants.*;

@RestController
@RequestMapping(CART_ROUTE)
@RequiredArgsConstructor
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize(HAS_ROLE_CLIENT)
    @PostMapping(ADD_ITEM_ROUTE)
    public ResponseEntity<CartResponse> addProduct(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PreAuthorize(HAS_ROLE_CLIENT)
    @DeleteMapping(DELETE_ITEM_ROUTE)
    public ResponseEntity<DeleteResponse> deleteItem(@PathVariable Long itemId) {
        cartHandler.deleteItem(itemId);

        return ResponseEntity.ok(new DeleteResponse(itemId, DELETE_ITEM_SUCCESS_MESSAGE));
    }

    @PreAuthorize(HAS_ROLE_CLIENT)
    @GetMapping(GET_CART_ROUTE)
    public ResponseEntity<PaginationResponse<ItemCartResponse>> getItemsFromCartPaginated(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "order", defaultValue = "true") Boolean order,
            @RequestParam(required = false) String filterByBrandName,
            @RequestParam(required = false) String filterByCategoryName
    ) {
        PaginationUtil paginationUtil = new PaginationUtil();
        paginationUtil.setPage(page);
        paginationUtil.setSize(size);
        paginationUtil.setOrder(order);
        paginationUtil.setFilterByBrandName(filterByBrandName);
        paginationUtil.setFilterByCategoryName(filterByCategoryName);
        PaginationResponse<ItemCartResponse> cartResponse = cartHandler.getItemsFromCartPaginated(paginationUtil);

        return ResponseEntity.ok(cartResponse);
    }

}
