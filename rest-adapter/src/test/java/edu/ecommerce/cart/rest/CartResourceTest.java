package edu.ecommerce.cart.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import edu.ecommerce.cart.domain.exception.CartNotFoundException;
import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.domain.port.RequestCart;
import edu.ecommerce.cart.rest.generated.model.CartInfo;
import edu.ecommerce.cart.rest.generated.model.ProblemDetail;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CartRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CartResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/carts";
  @LocalServerPort private int port;
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private RequestCart requestCart;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give carts when asked for carts with the support of domain stub")
  public void obtainCartsFromDomainStub() {
    // Given
    var cart = Cart.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestCart.getCarts()).thenReturn(List.of(cart));
    // When
    var url = LOCALHOST + port + API_URI;
    var responseEntity = restTemplate.getForEntity(url, CartInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getCarts())
        .isNotEmpty()
        .extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName(
      "should give the cart when asked for an cart by code with the support of domain stub")
  public void obtainCartByCodeFromDomainStub() {
    // Given
    var code = 1L;
    var description = "Johnny Johnny Yes Papa !!";
    var cart = Cart.builder().code(code).description(description).build();
    Mockito.lenient().when(requestCart.getCartByCode(code)).thenReturn(cart);
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, Cart.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(cart);
  }

  @Test
  @DisplayName(
      "should give exception when asked for an cart by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnCartByCodeFromDomainStub() {
    // Given
    var code = -1000L;
    Mockito.lenient()
        .when(requestCart.getCartByCode(code))
        .thenThrow(new CartNotFoundException(code));
    // When
    var url = LOCALHOST + port + API_URI + "/" + code;
    var responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
    // Then
    var expectedProblemDetail =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .detail("Cart with code -1000 does not exist")
            .instance("/api/v1/carts/-1000")
            .title("Cart not found")
            .build();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody())
        .usingRecursiveComparison()
        .ignoringFields("timestamp")
        .isEqualTo(expectedProblemDetail);
    assertThat(responseEntity.getBody().getTimestamp())
        .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
  }
}
