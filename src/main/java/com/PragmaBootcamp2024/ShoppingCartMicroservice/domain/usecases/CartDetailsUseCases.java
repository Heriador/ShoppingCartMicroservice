package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.ItemCartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.LimitItemPerCategoryException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
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
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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


        CartDetails existingDetail = cartDetailsPersistencePort.getCartDetails(cartDetails.getCart().getId(),
                        cartDetails.getItemId()).orElse(DomainConstants.NULL_CART_DETAILS);

        validateItemExistence(cartDetails.getItemId());

        validateQuantity(cartDetails.getQuantity());

        validateStock(cartDetails.getItemId(), cartDetails.getQuantity());


        if(existingDetail != null){

            existingDetail.setQuantity(existingDetail.getQuantity() + cartDetails.getQuantity());
            cartDetailsPersistencePort.addProductToCart(existingDetail);

        }
        else{
            List<Long> itemIds = new ArrayList<>(cartDetailsPersistencePort.getItemIdsByCartId(cartDetails.getCart().getId()));
            itemIds.add(cartDetails.getItemId());

            validateLimitItemPerCategory(itemIds);

            cartDetailsPersistencePort.addProductToCart(cartDetails);

        }

    }

    @Override
    public void deleteItem(Long itemId, Cart cart) {

        CartDetails cartDetail = cart.getItems().stream().filter(item -> item.getItemId().equals(itemId)).findFirst()
                .orElseThrow(() -> new NoItemFoundException(DomainConstants.ITEM_NOT_FOUND_EXCEPTION_MESSAGE));

        cartDetailsPersistencePort.deleteItemFromCart(cart.getId(), cartDetail.getItemId());
    }

    @Override
    public PaginationCustom<Item> getItemsFromCartPaginated(List<CartDetails> cartDetails, PaginationUtil paginationUtil) {

        PaginationValidator.validate(paginationUtil);
        List<Long> itemIds = cartDetails.stream().map(CartDetails::getItemId).toList();

        ItemCartRequest itemCartRequest = new ItemCartRequest();
        itemCartRequest.setItemIds(itemIds);
        PaginationCustom<Item> cartPagination = stockPersistencePort.getCartPagination(itemCartRequest, paginationUtil);

        if(cartPagination.getContent() != null){
            updateItemDetailsWithSupplyDateAndCartInfo(cartDetails, cartPagination.getContent());
        }

        BigDecimal totalPrice = calculateTotalPrice(cartDetails);

        cartPagination.setTotalPrice(totalPrice);

        return cartPagination;
    }

    private void updateItemDetailsWithSupplyDateAndCartInfo(List<CartDetails> cartDetails, List<Item> itemsPaginated) {

        Map<Long, CartDetails> cartDetailsMap = cartDetails.stream()
                        .collect(Collectors.toMap(CartDetails::getItemId, cartDetail -> cartDetail));

        for(Item item: itemsPaginated){
            CartDetails cartDetail = cartDetailsMap.get(item.getId());

            if(Objects.equals(item.getStock(), DomainConstants.ZERO_QUANTITY) || (item.getStock() < cartDetail.getQuantity())){
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


            item.setCartQuantity(cartDetail.getQuantity());
            item.setCartPrice(item.getPrice().multiply(BigDecimal.valueOf(cartDetail.getQuantity())));
        }


    }

    private BigDecimal calculateTotalPrice(List<CartDetails> cartDetails) {
        BigDecimal totalPrice = BigDecimal.ZERO;


        for(CartDetails cartDetail : cartDetails){


          BigDecimal itemPrice = stockPersistencePort.getPriceById(cartDetail.getItemId());
          totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartDetail.getQuantity())));


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
