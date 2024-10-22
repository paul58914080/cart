package edu.ecommerce.cart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.ObtainCart;
import edu.ecommerce.cart.repository.dao.CartDao;
import edu.ecommerce.cart.repository.entity.CartEntity;

public class CartRepository implements ObtainCart {

  private final CartDao cartDao;

  public CartRepository(CartDao cartDao) {
    this.cartDao = cartDao;
  }

  @Override
  public List<Cart> getAllCarts() {
    return cartDao.findAll().stream().map(CartEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Cart> getCartByCode(Long code) {
    var cartEntity = cartDao.findByCode(code);
    return cartEntity.map(CartEntity::toModel);
  }
}
