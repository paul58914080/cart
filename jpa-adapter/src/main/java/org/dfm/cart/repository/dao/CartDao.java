package org.dfm.cart.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.dfm.cart.repository.entity.CartEntity;

@Repository
public interface CartDao extends JpaRepository<CartEntity, Long> {

  Optional<CartEntity> findByCode(Long code);
}
