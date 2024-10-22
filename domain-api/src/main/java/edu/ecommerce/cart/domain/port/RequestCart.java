package edu.ecommerce.cart.domain.port;

import java.util.List;
import lombok.NonNull;
import edu.ecommerce.cart.domain.model.Cart;

public interface RequestCart {

  List<Cart> getCarts();

  Cart getCartByCode(@NonNull Long code);
}
