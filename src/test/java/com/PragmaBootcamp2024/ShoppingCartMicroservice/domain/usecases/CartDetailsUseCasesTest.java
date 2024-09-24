package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.LimitItemPerCategoryException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NotEnoughStockException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartDetailsPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IStockPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ITransactionPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.factory.CartDetailsFactory;
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
        Long cartId = 1L;
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())).thenReturn(Optional.of(cartDetails));
        when(stockPersistencePort.existsById(cartDetails.getItemId())).thenReturn(true);
        when(stockPersistencePort.hasStock(cartDetails.getItemId(), cartDetails.getQuantity())).thenReturn(true);
        doNothing().when(cartDetailsPersistencePort).addProductToCart(cartDetails);

        // Act
        cartDetailsUseCases.addProduct(cartDetails);

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartId, cartDetails.getItemId());
        verify(cartDetailsPersistencePort, times(1)).addProductToCart(cartDetails);
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Add item should throw exception when item does not exist")
    void addItem_ItemDoesNotExist_ThrowsException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(cartDetails.getCartId(), cartDetails.getItemId())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(false);

        // Act
        assertThrows(NoItemFoundException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartDetails.getCartId(), cartDetails.getItemId());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Add item should throw exception when quantity is not positive")
    void addItem_QuantityNotPositive_ThrowsException() {
        // Arrange
        Long cartId = 1L;
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        cartDetails.setQuantity(0);

        when(cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);

        // Act
        assertThrows(IllegalArgumentException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartId, cartDetails.getItemId());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Add item should throw exception when there is no stock")
    void addItem_NoStock_ThrowsException() {
        // Arrange
        Long cartId = 1L;
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(false);
        when(transactionPersistencePort.getNextSupplyDateByItemId(anyLong())).thenReturn(LocalDate.now());

        // Act
        assertThrows(NotEnoughStockException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartId, cartDetails.getItemId());
        verify(stockPersistencePort, times(1)).existsById(anyLong());
        verify(stockPersistencePort, times(1)).hasStock(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Add new item to cart should pass")
    void addItem_NewItem_AddsItem() {
        // Arrange
        Long cartId = 1L;
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(true);
        when(cartDetailsPersistencePort.getItemIdsByCartId(anyLong())).thenReturn(List.of(2L));
        when(stockPersistencePort.getCategoriesNameByItemId(anyLong())).thenReturn(List.of("category"));
        when(stockPersistencePort.getCategoriesNameByItemId(cartDetails.getItemId())).thenReturn(List.of("prueba"));
        doNothing().when(cartDetailsPersistencePort).addProductToCart(cartDetails);

        cartDetailsUseCases.addProduct(cartDetails);

        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartId, cartDetails.getItemId());
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
        Long cartId = 1L;
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(cartId, cartDetails.getItemId())).thenReturn(Optional.empty());
        when(stockPersistencePort.existsById(anyLong())).thenReturn(true);
        when(stockPersistencePort.hasStock(anyLong(), anyInt())).thenReturn(true);
        when(cartDetailsPersistencePort.getItemIdsByCartId(anyLong())).thenReturn(List.of(2L, 3L, 4L));
        when(stockPersistencePort.getCategoriesNameByItemId(anyLong())).thenReturn(List.of("category"));

        // Act
        assertThrows(LimitItemPerCategoryException.class, () -> cartDetailsUseCases.addProduct(cartDetails));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartId, cartDetails.getItemId());
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

        when(cartDetailsPersistencePort.getCartDetails(cartDetails.getCartId(), cartDetails.getItemId())).thenReturn(Optional.of(cartDetails));
        doNothing().when(cartDetailsPersistencePort).deleteItemFromCart(cartDetails);

        // Act
        cartDetailsUseCases.deleteItem(cartDetails.getItemId(), cartDetails.getCartId());

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartDetails.getCartId(), cartDetails.getItemId());
        verify(cartDetailsPersistencePort, times(1)).deleteItemFromCart(cartDetails);
    }

    @Test
    @DisplayName("Delete item from cart should throw NoItemFoundException")
    void deleteItemFromCartShouldThrowNoItemFoundException() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();

        when(cartDetailsPersistencePort.getCartDetails(anyLong(),anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoItemFoundException.class, () -> cartDetailsUseCases.deleteItem(cartDetails.getItemId(), cartDetails.getCartId()));

        // Assert
        verify(cartDetailsPersistencePort, times(1)).getCartDetails(cartDetails.getCartId(), cartDetails.getItemId());
    }
}