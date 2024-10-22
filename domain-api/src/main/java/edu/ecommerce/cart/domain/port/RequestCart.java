package edu.ecommerce.cart.domain.port;

import edu.ecommerce.cart.domain.model.Cart;
import java.util.List;
import lombok.NonNull;

public interface RequestCart {

  List<Cart> getCarts();

  Cart getCartByCode(@NonNull Long code);
}
