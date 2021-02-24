package org.dfm.cart.domain;

import java.util.Optional;
import org.dfm.cart.domain.exception.CartNotFoundException;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.domain.port.RequestCart;

public class CartDomain implements RequestCart {

  private ObtainCart obtainCart;

  public CartDomain() {
    this(new ObtainCart() {
    });
  }

  public CartDomain(ObtainCart obtainCart) {
    this.obtainCart = obtainCart;
  }

  @Override
  public CartInfo getCarts() {
    return CartInfo.builder().carts(obtainCart.getAllCarts()).build();
  }

  @Override
  public Cart getCartByCode(Long code) {
    Optional<Cart> cart = obtainCart.getCartByCode(code);
    return cart.orElseThrow(() -> new CartNotFoundException(code));
  }
}
