package org.dfm.cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.port.ObtainCart;
import org.dfm.cart.repository.dao.CartDao;
import org.dfm.cart.repository.entity.CartEntity;

public class CartRepository implements ObtainCart {

  private CartDao cartDao;

  public CartRepository(CartDao cartDao) {
    this.cartDao = cartDao;
  }

  @Override
  public List<Cart> getAllCarts() {
    return cartDao.findAll().stream().map(CartEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Cart> getCartByCode(Long code) {
    Optional<CartEntity> cartEntity = cartDao.findByCode(code);
    return cartEntity.map(CartEntity::toModel);
  }
}
