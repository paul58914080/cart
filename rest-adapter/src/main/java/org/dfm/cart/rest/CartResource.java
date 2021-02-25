package org.dfm.cart.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;
import org.dfm.cart.domain.port.RequestCart;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/carts")
public class CartResource {

  private RequestCart requestCart;

  public CartResource(RequestCart requestCart) {
    this.requestCart = requestCart;
  }

  @GetMapping
  public ResponseEntity<CartInfo> getCarts() {
    return ResponseEntity.ok(requestCart.getCarts());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Cart> getCartByCode(@PathVariable Long code) {
    return ResponseEntity.ok(requestCart.getCartByCode(code));
  }
}
