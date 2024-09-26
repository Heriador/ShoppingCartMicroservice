package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.ItemCartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.LimitItemPerCategoryException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.QuantityNotPositiveException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.DomainConstants;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public void addProduct(CartDetails cartDetails) {


        CartDetails existingDetail = cartDetailsPersistencePort.getCartDetails(cartDetails.getCartId(), cartDetails.getItemId())
                                                                        .orElse(DomainConstants.NULL_CART_DETAILS);

        validateItemExistence(cartDetails.getItemId());

        validateQuantity(cartDetails.getQuantity());

        validateStock(cartDetails.getItemId(), cartDetails.getQuantity());


        if(existingDetail != null){

            existingDetail.setQuantity(existingDetail.getQuantity() + cartDetails.getQuantity());
            cartDetailsPersistencePort.addProductToCart(existingDetail);

        }
        else{
            List<Long> itemIds = new ArrayList<>(cartDetailsPersistencePort.getItemIdsByCartId(cartDetails.getCartId()));
            itemIds.add(cartDetails.getItemId());

            validateLimitItemPerCategory(itemIds);

            cartDetailsPersistencePort.addProductToCart(cartDetails);

        }

    }

    @Override
    public void deleteItem(Long itemId, Long id) {
        CartDetails cartDetails = cartDetailsPersistencePort.getCartDetails(id, itemId)
                .orElseThrow(() -> new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE));

        cartDetailsPersistencePort.deleteItemFromCart(cartDetails);
    }

    @Override
    public PaginationCustom<Item> getCart(Long cartId, PaginationUtil paginationUtil) {

        List<CartDetails> cartDetails = cartDetailsPersistencePort.findByCartId(cartId)
                .orElseThrow(() -> new NoItemFoundException(DomainConstants.CART_NOT_FOUND_EXCEPTION_MESSAGE));

        List<Long> itemIds = cartDetails.stream().map(CartDetails::getItemId).toList();

        ItemCartRequest itemCartRequest = new ItemCartRequest();
        itemCartRequest.setItemIds(itemIds);
        PaginationCustom<Item> cartPagination = stockPersistencePort.getCartPagination(itemCartRequest, paginationUtil);

        cartPagination.getContent().forEach(item -> {
            if(Objects.equals(item.getStock(), DomainConstants.ZERO_QUANTITY)){
                LocalDate nextSupplyDate = transactionPersistencePort.getNextSupplyDateByItemId(item.getId());

                if(nextSupplyDate == null || nextSupplyDate.isBefore(LocalDate.now())){
                    item.setNextSupplyDate(DomainConstants.NO_SUPPLY_DATE);
                }
                else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DomainConstants.SUPPLY_DATE_FORMAT,
                            Locale.ENGLISH);
                    item.setNextSupplyDate(nextSupplyDate.format(formatter));
                }
            }

        });

        BigDecimal totalPrice = calculateTotalPrice(cartId);

        cartPagination.setTotalPrice(totalPrice);

        return cartPagination;
    }

    private BigDecimal calculateTotalPrice(Long cartId) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        List<Long> itemIds = cartDetailsPersistencePort.getItemIdsByCartId(cartId);

        for(Long itemId : itemIds){
          CartDetails cartDetails = cartDetailsPersistencePort.getCartDetails(cartId, itemId).orElse(null);

          if(cartDetails == null){
              continue;
          }

          BigDecimal itemPrice = stockPersistencePort.getPriceById(itemId);
          totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartDetails.getQuantity())));


        }



        return totalPrice;
    }







    private void validateItemExistence(Long itemId){
        if(Boolean.FALSE.equals(stockPersistencePort.existsById(itemId))){
            throw new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE);
        }
    }

    private void validateQuantity(Integer quantity){
        if(quantity <= DomainConstants.ZERO_QUANTITY){
            throw new QuantityNotPositiveException(DomainConstants.QUANTITY_NOT_POSITIVE_MESSAGE);
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

    private void validateLimitItemPerCategory(List<Long> itemIds){
        Map<String, Integer> categoriesCount = new HashMap<>();

        for(Long itemId : itemIds){
            List<String> categories = stockPersistencePort.getCategoriesNameByItemId(itemId);
            for(String category : categories){
                categoriesCount.put(category, categoriesCount
                        .getOrDefault(category, DomainConstants.DEFAULT_COUNT_VALUE) + DomainConstants.ADDITIONAL_COUNT_VALUE);
                if (categoriesCount.get(category) > DomainConstants.LIMIT_ITEM_PER_CATEGORY){
                    throw new LimitItemPerCategoryException(DomainConstants.LIMIT_ITEM_PER_CATEGORY_EXCEPTION_MESSAGE);
                }
            }
        }
    }
}
