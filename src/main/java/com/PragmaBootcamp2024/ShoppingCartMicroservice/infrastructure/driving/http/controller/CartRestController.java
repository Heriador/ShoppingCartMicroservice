package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.controller;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.DeleteResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.ItemCartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.PaginationResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler.ICartHandler;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driving.http.util.RestControllerConstants.*;

@RestController
@RequestMapping(CART_ROUTE)
@RequiredArgsConstructor
@Tag(name = DocumentationConstants.CART_CONTROLLER_TAG, description = DocumentationConstants.CART_CONTROLLER_DESCRIPTION)
public class CartRestController {

    private final ICartHandler cartHandler;

    @PreAuthorize(HAS_ROLE_CLIENT)
    @PostMapping(ADD_ITEM_ROUTE)
    @Operation(summary = DocumentationConstants.ADD_ITEM_TO_CART_OPERATION_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_201,
                    description = DocumentationConstants.DESCRIPTION_STATUS_201,
                    content = @Content(schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_400,
                    description = DocumentationConstants.DESCRIPTION_STATUS_400,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_401,
                    description = DocumentationConstants.DESCRIPTION_STATUS_401,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_403,
                    description = DocumentationConstants.DESCRIPTION_STATUS_403,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_404,
                    description = DocumentationConstants.DESCRIPTION_STATUS_404,
                    content = @Content)
    })
    public ResponseEntity<CartResponse> addItem(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartHandler.addProduct(cartRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @PreAuthorize(HAS_ROLE_CLIENT)
    @DeleteMapping(DELETE_ITEM_ROUTE)
    @Operation(summary = DocumentationConstants.DELETE_ITEM_FROM_CART_OPERATION_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_200,
                    description = DocumentationConstants.DESCRIPTION_STATUS_200,
                    content = @Content(schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_401,
                    description = DocumentationConstants.DESCRIPTION_STATUS_401,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_403,
                    description = DocumentationConstants.DESCRIPTION_STATUS_403,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_404,
                    description = DocumentationConstants.DESCRIPTION_STATUS_404,
                    content = @Content)
    })
    public ResponseEntity<DeleteResponse> deleteItem(@PathVariable Long itemId) {
        cartHandler.deleteItem(itemId);

        return ResponseEntity.ok(new DeleteResponse(itemId, DELETE_ITEM_SUCCESS_MESSAGE));
    }

    @PreAuthorize(HAS_ROLE_CLIENT)
    @GetMapping(GET_CART_ROUTE)
    @Operation(summary = DocumentationConstants.GET_ITEMS_FROM_CART_PAGINATED_OPERATION_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_200,
                    description = DocumentationConstants.DESCRIPTION_STATUS_200,
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_400,
                    description = DocumentationConstants.DESCRIPTION_STATUS_400,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_401,
                    description = DocumentationConstants.DESCRIPTION_STATUS_401,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_403,
                    description = DocumentationConstants.DESCRIPTION_STATUS_403,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CODE_STATUS_404,
                    description = DocumentationConstants.DESCRIPTION_STATUS_404,
                    content = @Content)
    })
    public ResponseEntity<PaginationResponse<ItemCartResponse>> getItemsFromCartPaginated(
            @RequestParam(defaultValue = PAGE_PARAM_DEFAULT_VALUE) Integer page,
            @RequestParam(defaultValue = SIZE_PARAM_DEFAULT_VALUE) Integer size,
            @RequestParam(defaultValue = ORDER_PARAM_DEFAULT_VALUE) Boolean order,
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
