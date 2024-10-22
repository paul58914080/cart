package edu.ecommerce.cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.ObtainCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CartJpaTest {

  @Autowired private ObtainCart obtainCart;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given carts exist in database when asked should return all carts from database")
  public void shouldGiveMeCartsWhenAskedGivenCartExistsInDatabase() {
    // Given from @Sql
    // When
    var carts = obtainCart.getAllCarts();
    // Then
    assertThat(carts).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no carts exists in database when asked should return empty")
  public void shouldGiveNoCartWhenAskedGivenCartsDoNotExistInDatabase() {
    // When
    var carts = obtainCart.getAllCarts();
    // Then
    assertThat(carts).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given carts exists in database when asked for cart by id should return the cart")
  public void shouldGiveTheCartWhenAskedByIdGivenThatCartByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    var cart = obtainCart.getCartByCode(1L);
    // Then
    assertThat(cart)
        .isNotNull()
        .isNotEmpty()
        .get()
        .isEqualTo(Cart.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName(
      "given carts exists in database when asked for cart by id that does not exist should give empty")
  public void shouldGiveNoCartWhenAskedByIdGivenThatCartByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    var cart = obtainCart.getCartByCode(-1000L);
    // Then
    assertThat(cart).isEmpty();
  }
}
