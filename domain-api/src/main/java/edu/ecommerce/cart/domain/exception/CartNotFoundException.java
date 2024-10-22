package edu.ecommerce.cart.domain.exception;

public class CartNotFoundException extends RuntimeException {

  public CartNotFoundException(Long id) {
    super("Cart with code " + id + " does not exist");
  }
}
