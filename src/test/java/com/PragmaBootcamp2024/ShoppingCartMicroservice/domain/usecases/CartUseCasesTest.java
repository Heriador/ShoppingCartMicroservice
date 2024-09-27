package com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.usecases;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.api.ICartDetailsServicePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.exceptions.NoItemFoundException;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Cart;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.CartDetails;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.Item;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.model.PaginationCustom;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.IAuthenticationPersistencePort;
import com.PragmaBootcamp2024.ShoppingCartMicroservice.domain.spi.ICartPersistencePort;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartUseCasesTest {

    @Mock
    private ICartPersistencePort cartPersistencePort;

    @Mock
    private IAuthenticationPersistencePort authenticationPersistencePort;

    @Mock
    private ICartDetailsServicePort cartDetailsServicePort;

    @InjectMocks
    private CartUseCases cartUseCases;

    @Test
    @DisplayName("Add product to existing cart should pass")
    void addProductToExistingCartShouldPass() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId(1L);


        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(1L)).thenReturn(Optional.of(cart));
        doNothing().when(cartDetailsServicePort).addProduct(cartDetails);
        doNothing().when(cartPersistencePort).saveCart(cart);

        // Act
        cartUseCases.addProduct(cartDetails);

        // Assert
        verify(authenticationPersistencePort, times(1)).getAuthenticatedUserId();
        verify(cartPersistencePort, times(1)).existsCart(1L);
        verify(cartDetailsServicePort, times(1)).addProduct(cartDetails);
        verify(cartPersistencePort, times(1)).saveCart(cart);
    }

    @Test
    @DisplayName("Add product to new cart should pass")
    void addProductToNewCartShouldPass() {
        // Arrange
        CartDetails cartDetails = CartDetailsFactory.getCartDetails();
        Cart cart = new Cart();
        cart.setId(cartDetails.getItemId());
        cart.setUserId(1L);

        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(anyLong())).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Cart cart1 = invocation.getArgument(0);
            cart1.setId(1L);
            return null;
        }).when(cartPersistencePort).saveCart(any(Cart.class));
        doNothing().when(cartDetailsServicePort).addProduct(any(CartDetails.class));

        // Act
        cartUseCases.addProduct(cartDetails);

        // Assert
        verify(authenticationPersistencePort, times(1)).getAuthenticatedUserId();
        verify(cartPersistencePort, times(1)).existsCart(1L);
        verify(cartPersistencePort, times(2)).saveCart(any(Cart.class));
        verify(cartDetailsServicePort, times(1)).addProduct(cartDetails);
    }

    @Test
    @DisplayName("Delete item from cart should pass")
    void deleteItemFromCartShouldPass() {
        // Arrange
        Cart cart = CartFactory.getCart();

        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(1L)).thenReturn(Optional.of(cart));
        doNothing().when(cartDetailsServicePort).deleteItem(anyLong(), any(Cart.class));
        doNothing().when(cartPersistencePort).saveCart(cart);

        // Act
        cartUseCases.deleteItem(1L);

        // Assert
        verify(authenticationPersistencePort, times(1)).getAuthenticatedUserId();
        verify(cartPersistencePort, times(1)).existsCart(1L);
        verify(cartDetailsServicePort, times(1)).deleteItem(1L, cart);
        verify(cartPersistencePort, times(1)).saveCart(cart);
    }

    @Test
    @DisplayName("Delete item from cart should throw exception")
    void deleteItemFromCartShouldThrowException() {
        // Arrange

        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoItemFoundException.class, () -> cartUseCases.deleteItem(1L));
    }

    @Test
    @DisplayName("Get cart should pass")
    void getCartShouldPass() {
        // Arrange
        Cart cart = CartFactory.getCart();
        PaginationUtil paginationUtil = new PaginationUtil();
        paginationUtil.setPage(0);
        paginationUtil.setSize(5);

        PaginationCustom<Item> expected = ItemFactory.getPaginationCustom();

        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(any())).thenReturn(Optional.of(cart));
        when(cartDetailsServicePort.getCart(anyList(), any())).thenReturn(expected);

        // Act
        PaginationCustom<Item> paginationCustom = cartUseCases.getCart(paginationUtil);

        // Assert

        assertEquals(expected, paginationCustom);

        verify(authenticationPersistencePort, times(1)).getAuthenticatedUserId();
        verify(cartPersistencePort, times(1)).existsCart(1L);
        verify(cartDetailsServicePort, times(1)).getCart(anyList(), any(PaginationUtil.class));
    }

    @Test
    @DisplayName("Get cart should throw NoItemFoundException")
    void getCartShouldThrowNoItemFoundException() {
        // Arrange
        PaginationUtil paginationUtil = new PaginationUtil();
        paginationUtil.setPage(0);
        paginationUtil.setSize(5);

        when(authenticationPersistencePort.getAuthenticatedUserId()).thenReturn(1L);
        when(cartPersistencePort.existsCart(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoItemFoundException.class, () -> cartUseCases.getCart(paginationUtil));

        verify(authenticationPersistencePort, times(1)).getAuthenticatedUserId();
        verify(cartPersistencePort, times(1)).existsCart(1L);
    }

}