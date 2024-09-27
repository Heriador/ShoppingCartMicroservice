package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.DomainConstants;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

import java.time.LocalDateTime;

public class CartUseCases implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;
    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final ICartDetailsServicePort cartDetailsServicePort;

    public CartUseCases(ICartPersistencePort cartPersistencePort,
                        IAuthenticationPersistencePort authenticationPersistencePort,
                        ICartDetailsServicePort cartDetailsServicePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.authenticationPersistencePort = authenticationPersistencePort;
        this.cartDetailsServicePort = cartDetailsServicePort;
    }

    @Override
    public void addProduct(CartDetails cartDetails) {

        Long userId = authenticationPersistencePort.getAuthenticatedUserId();

        Cart cart = cartPersistencePort.existsCart(userId).orElse(null);

        if(cart == null){

            cart = createCart(userId);
        }
        cartDetails.setCart(cart);

        cartDetailsServicePort.addProduct(cartDetails);

        cart.setUpdatedAt(LocalDateTime.now());
        cartPersistencePort.saveCart(cart);
    }

    @Override
    public void deleteItem(Long itemId) {

            Long userId = authenticationPersistencePort.getAuthenticatedUserId();

            Cart cart = cartPersistencePort.existsCart(userId).orElseThrow(()-> new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE));

            cartDetailsServicePort.deleteItem(itemId, cart);

            cart.setUpdatedAt(LocalDateTime.now());
            cartPersistencePort.saveCart(cart);
    }


    @Override
    public PaginationCustom<Item> getCart(PaginationUtil paginationUtil) {

        Long userId = authenticationPersistencePort.getAuthenticatedUserId();

        Cart cart = cartPersistencePort.existsCart(userId).orElseThrow(()-> new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE));

        return cartDetailsServicePort.getCart(cart.getItems(), paginationUtil);
    }

    private Cart createCart(Long userId) {
        Cart cart = new Cart();

        cart.setUserId(userId);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cartPersistencePort.saveCart(cart);

        return cart;
    }
}
