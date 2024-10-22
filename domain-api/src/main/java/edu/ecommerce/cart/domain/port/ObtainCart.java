package edu.ecommerce.cart.domain.port;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import edu.ecommerce.cart.domain.model.Cart;

public interface ObtainCart {

  default List<Cart> getAllCarts() {
    Cart cart =
        Cart.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build();
    return List.of(cart);
  }

  default Optional<Cart> getCartByCode(@NonNull Long code) {
    return Optional.of(
        Cart.builder()
            .code(1L)
            .description(
                "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
            .build());
  }
}
