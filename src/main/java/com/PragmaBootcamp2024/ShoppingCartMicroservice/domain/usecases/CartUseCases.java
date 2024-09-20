package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;

import java.util.Date;

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

        cartDetailsServicePort.addProduct(cartDetails.getItemId(), cartDetails);

        cart.setUpdatedAt(new Date());
        cartPersistencePort.saveCart(cart);
    }



    private Cart createCart(Long userId) {
        Cart cart = new Cart();

        cart.setUserId(userId);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());
        cartPersistencePort.saveCart(cart);

        return cart;
    }
}
