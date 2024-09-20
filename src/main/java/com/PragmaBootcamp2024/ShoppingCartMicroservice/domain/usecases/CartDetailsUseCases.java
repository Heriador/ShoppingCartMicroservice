package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.DomainConstants;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CartDetailsUseCases implements ICartDetailsServicePort {

    private final IStockPersistencePort stockPersistencePort;
    private final ICartDetailsPersistencePort cartDetailsPersistencePort;
    private final ITransactionPersistencePort transactionPersistencePort;

    public CartDetailsUseCases(
                               ICartDetailsPersistencePort cartDetailsPersistencePort,
                               IStockPersistencePort stockPersistencePort,
                               ITransactionPersistencePort transactionPersistencePort) {
        this.cartDetailsPersistencePort = cartDetailsPersistencePort;
        this.stockPersistencePort = stockPersistencePort;
        this.transactionPersistencePort = transactionPersistencePort;
    }

    @Override
    public void addProduct(Long cartId, CartDetails cartDetails) {


        CartDetails existingDetail = cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())
                                                                        .orElse(DomainConstants.NULL_CART_DETAILS);

        validateItemExistence(cartDetails.getItemId());

        validateQuantity(cartDetails.getQuantity());

        validateStock(cartDetails.getItemId(), cartDetails.getQuantity());


        if(existingDetail != null){

            existingDetail.setQuantity(existingDetail.getQuantity() + cartDetails.getQuantity());
            cartDetailsPersistencePort.addProductToCart(existingDetail);

        }
        else{
            cartDetails.setCartId(cartId);
            cartDetailsPersistencePort.addProductToCart(cartDetails);
        }

    }

    private void validateItemExistence(Long itemId){
        if(Boolean.FALSE.equals(stockPersistencePort.existsById(itemId))){
            throw new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE);
        }
    }

    private void validateQuantity(Integer quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException(DomainConstants.QUANTITY_NOT_POSITIVE_MESSAGE);
        }
    }

    private void validateStock(Long itemId, Integer quantity){
        if(Boolean.FALSE.equals(stockPersistencePort.hasStock(itemId, quantity))){
            LocalDate nextSupplyDate = transactionPersistencePort.getNextSupplyDateByItemId(itemId);

            if(nextSupplyDate == null || nextSupplyDate.isBefore(LocalDate.now())){
                throw new NotEnoughStockException(DomainConstants.INSUFFICIENT_STOCK_MESSAGE);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DomainConstants.SUPPLY_DATE_FORMAT,
                    Locale.ENGLISH);

            throw new NotEnoughStockException(DomainConstants.INSUFFICIENT_STOCK_MESSAGE+
                    DomainConstants.NEXT_SUPPLY_DATE+
                    nextSupplyDate.format(formatter));
        }
    }
}
