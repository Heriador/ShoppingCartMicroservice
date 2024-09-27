package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.application.Dto.request.ItemCartRequest;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.*;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.util.PaginationUtil;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartDetailsFactory;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartFactory;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.ItemFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartDetailsUseCasesTest {

    @Mock
    private IStockPersistencePort stockPersistencePort;

    @Mock
    private ICartDetailsPersistencePort cartDetailsPersistencePort;

    @Mock
    private ITransactionPersistencePort transactionPersistencePort;

    @InjectMocks
    private CartDetailsUseCases cartDetailsUseCases;


    @Test
    @DisplayName("Update existing item to cart should pass")
    void addItem_ValidItem_AddsItem() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        Cart cart = CartFactory.getCart();
        cartDetails.setCart(cart);

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.of(cartDetails));
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(true);
        doNothing().when(cartDetailsPersistencePort).addProductToCart(cartDetails);

        // Act
        cartDetailsUseCases.addProduct(cartDetails);

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(cartDetailsPersistencePort, times(1)).addProductToCart(cartDetails);
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Add item should throw exception when item does not exist")
    void addItem_ItemDoesNotExist_ThrowsException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(CartFactory.getCart());

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(false);

        // Act
        assertThrows(NoItemFoundException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Add item should throw exception when quantity is not positive")
    void addItem_QuantityNotPositive_ThrowsException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setQuantity(0);
        cartDetails.setCart(CartFactory.getCart());

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);

        // Act
        assertThrows(QuantityNotPositiveException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Add item should throw exception when there is no stock")
    void addItem_NoStock_ThrowsException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(CartFactory.getCart());

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(false);
        when(transactionPersistencePort.getNextSupplyDateByItemId(anyLong())).thenReturn(LocalDate.now());

        // Act
        assertThrows(NotEnoughStockException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Add new item to cart should pass")
    void addItem_NewItem_AddsItem() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(CartFactory.getCart());

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(true);
        when(cartDetailsPersistencePort.getItemIdsByCartId(anyLong())).thenReturn(List.of(2L));
        when(stockPersistencePort.getCategoriesNameByItemId(anyLong())).thenReturn(List.of("category"));
        when(stockPersistencePort.getCategoriesNameByItemId(cartDetails.getItemId())).thenReturn(List.of("prueba"));
        doNothing().when(cartDetailsPersistencePort).addProductToCart(cartDetails);

        cartDetailsUseCases.addProduct(cartDetails);

        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
        verify(cartDetailsPersistencePort, times(1)).getItemIdsByCartId(anyLong());
        verify(stockPersistencePort, times(2)).getCategoriesNameByItemId(anyLong());
        verify(cartDetailsPersistencePort, times(1)).addProductToCart(cartDetails);

    }

    @Test
    @DisplayName("Add new item to cart should throw exception when limit of items per category is reached")
    void addItem_LimitItemsPerCategoryReached_ThrowsException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(CartFactory.getCart());

        when(cartDetailsPersistencePort.getCartDetails(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(true);
        when(cartDetailsPersistencePort.getItemIdsByCartId(anyLong())).thenReturn(List.of(2L, 3L, 4L));
        when(stockPersistencePort.getCategoriesNameByItemId(anyLong())).thenReturn(List.of("category"));

        // Act
        assertThrows(LimitItemPerCategoryException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(anyLong(), anyLong());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
        verify(cartDetailsPersistencePort, times(1)).getItemIdsByCartId(anyLong());
        verify(stockPersistencePort, times(4)).getCategoriesNameByItemId(anyLong());
    }

    @Test
    @DisplayName("Delete item from cart should pass")
    void deleteItemFromCartShouldPass() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        Cart cart = CartFactory.getCart();

        doNothing().when(cartDetailsPersistencePort).deleteItemFromCart(anyLong(),anyLong());

        // Act
        cartDetailsUseCases.deleteItem(cartDetails.getItemId(), cart);

        // Assert
        verify(cartDetailsPersistencePort, times(1)).deleteItemFromCart(cart.getId(),cartDetails.getItemId());
    }

    @Test
    @DisplayName("Delete item from cart should throw NoItemFoundException")
    void deleteItemFromCartShouldThrowNoItemFoundException() {
        // Arrange
        Cart cart = CartFactory.getCart();


        assertThrows(NoItemFoundException.class, () -> cartDetailsUseCases.deleteItem(2L, cart));

    }

    @Test
    @DisplayName("Get cart should pass")
    void getItemsFromCartPaginatedShouldPass() {

        Cart cart = CartFactory.getCart();

        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(cart);

        CartDetails cartDetails1 = new CartDetails(2L, cart, 2L,5);

        PaginationUtil paginationUtil = new PaginationUtil();
        paginationUtil.setPage(0);
        paginationUtil.setSize(5);
        paginationUtil.setOrder(true);
        PaginationCustom<Item> paginationCustom = ItemFactory.getPaginationCustom();

        when(stockPersistencePort.getCartPagination(any(ItemCartRequest.class), any(PaginationUtil.class)))
                .thenReturn(paginationCustom);
        when(transactionPersistencePort.getNextSupplyDateByItemId(anyLong())).thenReturn(LocalDate.now());
        when(stockPersistencePort.getPriceById(anyLong())).thenReturn(BigDecimal.ONE);

        PaginationCustom<Item> result = cartDetailsUseCases.getItemsFromCartPaginated(List.of(cartDetails,cartDetails1), paginationUtil);

        assertEquals(paginationCustom.getContent(), result.getContent());
        assertEquals(paginationCustom.getTotalElements(), result.getTotalElements());
        assertEquals(paginationCustom.getTotalPages(), result.getTotalPages());
        assertEquals(paginationCustom.isLast(), result.isLast());
        assertEquals(paginationCustom.getPageNumber(), result.getPageNumber());
        assertEquals(paginationCustom.getPageSize(), result.getPageSize());
        assertEquals(paginationCustom.getTotalPrice(), result.getTotalPrice());

        verify(stockPersistencePort, times(1)).getCartPagination(any(ItemCartRequest.class), any(PaginationUtil.class));
        verify(transactionPersistencePort, atLeast(1)).getNextSupplyDateByItemId(anyLong());
        verify(stockPersistencePort, atLeast(1)).getPriceById(anyLong());



    }

    @Test
    @DisplayName("Get cart should throw ValidationException for paginotionUtil invalid values")
    void getItemsFromCartPaginatedShouldThrowValidationException() {
        Cart cart = CartFactory.getCart();

        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setCart(cart);

        PaginationUtil paginationUtil = new PaginationUtil();
        paginationUtil.setPage(-1);
        paginationUtil.setSize(-1);
        paginationUtil.setOrder(true);

        assertThrows(ValidationException.class, () -> cartDetailsUseCases.getItemsFromCartPaginated(List.of(cartDetails), paginationUtil));
    }
}