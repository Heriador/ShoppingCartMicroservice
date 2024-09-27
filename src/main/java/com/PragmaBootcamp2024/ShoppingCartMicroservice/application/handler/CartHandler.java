package com.PragmaBootcamp2024.ShoppingCartMicroservice.application.handler;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.ItemCartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.PaginationResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.CartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.response.CartResponse;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper.CartRequestMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.mapper.CartResponseMapper;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandler implements ICartHandler {

    private final ICartServicePort cartServicePort;
    private final CartRequestMapper cartRequestMapper;
    private final CartResponseMapper cartResponseMapper;

    public CartResponse addProduct(CartRequest cartRequest) {

        CartDetails cart = cartRequestMapper.toCart(cartRequest);

        cartServicePort.addProduct(cart);

        return cartResponseMapper.toCartResponse(cartRequest);
    }

    @Override
    public void deleteItem(Long itemId) {

        cartServicePort.deleteItem(itemId);

    }


    @Override
    public PaginationResponse<ItemCartResponse> getItemsFromCartPaginated(PaginationUtil paginationUtil) {

        return cartResponseMapper.toPaginationResponse(cartServicePort.getItemsFromCartPaginated(paginationUtil));
    }
}
