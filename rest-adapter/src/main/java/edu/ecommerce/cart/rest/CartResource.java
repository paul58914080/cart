package edu.ecommerce.cart.rest;

import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.RequestCart;
import edu.ecommerce.cart.rest.generated.api.CartApi;
import edu.ecommerce.cart.rest.generated.model.CartInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartResource implements CartApi {

  private final RequestCart requestCart;

  public CartResource(RequestCart requestCart) {
    this.requestCart = requestCart;
  }

  public ResponseEntity<CartInfo> getCarts() {
    return ResponseEntity.ok(CartInfo.builder().carts(requestCart.getCarts()).build());
  }

  public ResponseEntity<Cart> getCartByCode(@PathVariable("code") Long code) {
    return ResponseEntity.ok(requestCart.getCartByCode(code));
  }
}
