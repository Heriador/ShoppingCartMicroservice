package com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartServicePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
import com.PargmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;

import java.util.Date;

public class CartUseCases implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;
    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final IStockPersistencePort stockPersistencePort;

    public CartUseCases(ICartPersistencePort cartPersistencePort,
                        IAuthenticationPersistencePort authenticationPersistencePort,
                        IStockPersistencePort stockPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.authenticationPersistencePort = authenticationPersistencePort;
        this.stockPersistencePort = stockPersistencePort;
    }

    @Override
    public void addProduct(Cart cart) {

        Long userId = authenticationPersistencePort.getAuthenticatedUserId();

        if(!stockPersistencePort.existsById(cart.getItemId())){
            throw new NoItemFoundException("Item not found");
        }

        cart.setUserId(userId);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());
        cartPersistencePort.addProduct(cart);
    }
}
