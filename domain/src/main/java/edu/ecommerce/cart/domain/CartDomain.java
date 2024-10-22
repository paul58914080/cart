package edu.ecommerce.cart.domain;

import java.util.List;
import lombok.NonNull;
import edu.ecommerce.cart.domain.exception.CartNotFoundException;
import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.ObtainCart;
import edu.ecommerce.cart.domain.port.RequestCart;

public class CartDomain implements RequestCart {

  private final ObtainCart obtainCart;

  public CartDomain() {
    this(new ObtainCart() {});
  }

  public CartDomain(ObtainCart obtainCart) {
    this.obtainCart = obtainCart;
  }

  @Override
  public List<Cart> getCarts() {
    return obtainCart.getAllCarts();
  }

  @Override
  public Cart getCartByCode(@NonNull Long code) {
    var cart = obtainCart.getCartByCode(code);
    return cart.orElseThrow(() -> new CartNotFoundException(code));
  }
}
