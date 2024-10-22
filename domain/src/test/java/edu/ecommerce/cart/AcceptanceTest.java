package edu.ecommerce.cart;

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
import edu.ecommerce.cart.domain.CartDomain;
import edu.ecommerce.cart.domain.exception.CartNotFoundException;
import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.ObtainCart;

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
    var requestCart = new CartDomain(); // the cart is hard coded
    var carts = requestCart.getCarts();
    assertThat(carts)
        .hasSize(1)
        .extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get carts when asked for carts from stub")
  public void getCartsFromMockedStub(@Mock ObtainCart obtainCart) {
    // Stub
    var cart =
        Cart.builder()
            .code(1L)
            .description(
                "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
            .build();
    Mockito.lenient().when(obtainCart.getAllCarts()).thenReturn(List.of(cart));
    // hexagon
    var requestCart = new CartDomain(obtainCart);
    var carts = requestCart.getCarts();
    assertThat(carts)
        .hasSize(1)
        .extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get cart when asked for cart by id from stub")
  public void getCartByIdFromMockedStub(@Mock ObtainCart obtainCart) {
    // Given
    // Stub
    var code = 1L;
    var description =
        "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    var expectedCart = Cart.builder().code(code).description(description).build();
    Mockito.lenient()
        .when(obtainCart.getCartByCode(code))
        .thenReturn(Optional.of(expectedCart));
    // When
    var requestCart = new CartDomain(obtainCart);
    var actualCart = requestCart.getCartByCode(code);
    assertThat(actualCart).isNotNull().isEqualTo(expectedCart);
  }

  @Test
  @DisplayName("should throw exception when asked for cart by id that does not exists from stub")
  public void getExceptionWhenAskedCartByIdThatDoesNotExist(@Mock ObtainCart obtainCart) {
    // Given
    // Stub
    var code = -1000L;
    Mockito.lenient().when(obtainCart.getCartByCode(code)).thenReturn(Optional.empty());
    // When
    var requestCart = new CartDomain(obtainCart);
    // Then
    assertThatThrownBy(() -> requestCart.getCartByCode(code))
        .isInstanceOf(CartNotFoundException.class)
        .hasMessageContaining("Cart with code " + code + " does not exist");
  }
}
