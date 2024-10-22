package edu.ecommerce.cart.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import edu.ecommerce.cart.repository.entity.CartEntity;

@Repository
public interface CartDao
    extends JpaRepository<CartEntity, Long>, RevisionRepository<CartEntity, Long, Long> {

  Optional<CartEntity> findByCode(Long code);
}
