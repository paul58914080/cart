package org.dfm.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.dfm.cart.domain.CartDomain;
import org.dfm.cart.domain.exception.CartNotFoundException;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.domain.port.RequestCart;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get carts when asked for carts from hard coded carts")
  public void getCartsFromHardCoded() {
  /*
      RequestCart    - left side port
      CartDomain     - hexagon (domain)
      ObtainCart     - right side port
   */
    RequestCart requestCart = new CartDomain(); // the cart is hard coded
    CartInfo cartInfo = requestCart.getCarts();
    assertThat(cartInfo).isNotNull();
    assertThat(cartInfo.getCarts()).isNotEmpty().extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get carts when asked for carts from stub")
  public void getCartsFromMockedStub(@Mock ObtainCart obtainCart) {
    // Stub
    Cart cart = Cart.builder().code(1L).description(
        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
        .build();
    Mockito.lenient().when(obtainCart.getAllCarts()).thenReturn(List.of(cart));
    // hexagon
    RequestCart requestCart = new CartDomain(obtainCart);
    CartInfo cartInfo = requestCart.getCarts();
    assertThat(cartInfo).isNotNull();
    assertThat(cartInfo.getCarts()).isNotEmpty().extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get cart when asked for cart by id from stub")
  public void getCartByIdFromMockedStub(@Mock ObtainCart obtainCart) {
    // Given
    // Stub
    Long code = 1L;
    String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    Cart expectedCart = Cart.builder().code(code).description(description).build();
    Mockito.lenient().when(obtainCart.getCartByCode(code))
        .thenReturn(Optional.of(expectedCart));
    // When
    RequestCart requestCart = new CartDomain(obtainCart);
    Cart actualCart = requestCart.getCartByCode(code);
    assertThat(actualCart).isNotNull().isEqualTo(expectedCart);
  }

  @Test
  @DisplayName("should throw exception when asked for cart by id that does not exists from stub")
  public void getExceptionWhenAskedCartByIdThatDoesNotExist(@Mock ObtainCart obtainCart) {
    // Given
    // Stub
    Long code = -1000L;
    Mockito.lenient().when(obtainCart.getCartByCode(code)).thenReturn(Optional.empty());
    // When
    RequestCart requestCart = new CartDomain(obtainCart);
    // Then
    assertThatThrownBy(() -> requestCart.getCartByCode(code)).isInstanceOf(
        CartNotFoundException.class)
        .hasMessageContaining("Cart with code " + code + " does not exist");
  }
}
