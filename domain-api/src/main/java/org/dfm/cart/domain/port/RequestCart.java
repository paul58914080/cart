package org.dfm.cart.domain.port;

import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;

public interface RequestCart {

  CartInfo getCarts();
  Cart getCartByCode(Long code);
}
