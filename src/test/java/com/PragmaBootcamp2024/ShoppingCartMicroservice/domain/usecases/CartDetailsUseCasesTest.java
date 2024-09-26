package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.LimitItemPerCategoryException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.QuantityNotPositiveException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartDetailsFactory;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


        // Act & Assert
        assertThrows(NoItemFoundException.class, () -> cartDetailsUseCases.deleteItem(2L, cart));

        // Assert
    }
}