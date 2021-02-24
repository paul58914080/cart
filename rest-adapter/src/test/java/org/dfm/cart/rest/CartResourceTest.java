package org.dfm.cart.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.dfm.cart.domain.exception.CartNotFoundException;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;
import org.dfm.cart.domain.port.RequestCart;
import org.dfm.cart.rest.exception.CartExceptionResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CartRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CartResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/carts";
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RequestCart requestCart;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give carts when asked for carts with the support of domain stub")
  public void obtainCartsFromDomainStub() {
    // Given
    Cart cart = Cart.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestCart.getCarts())
        .thenReturn(CartInfo.builder().carts(List.of(cart)).build());
    // When
    String url = LOCALHOST + port + API_URI;
    ResponseEntity<CartInfo> responseEntity = restTemplate.getForEntity(url, CartInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getCarts()).isNotEmpty().extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName("should give the cart when asked for an cart by code with the support of domain stub")
  public void obtainCartByCodeFromDomainStub() {
    // Given
    Long code = 1L;
    String description = "Johnny Johnny Yes Papa !!";
    Cart cart = Cart.builder().code(code).description(description).build();
    Mockito.lenient().when(requestCart.getCartByCode(code)).thenReturn(cart);
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<Cart> responseEntity = restTemplate.getForEntity(url, Cart.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(cart);
  }

  @Test
  @DisplayName("should give exception when asked for an cart by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnCartByCodeFromDomainStub() {
    // Given
    Long code = -1000L;
    Mockito.lenient().when(requestCart.getCartByCode(code)).thenThrow(new
        CartNotFoundException(code));
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<CartExceptionResponse> responseEntity = restTemplate
        .getForEntity(url, CartExceptionResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(CartExceptionResponse.builder()
        .message("Cart with code " + code + " does not exist").path(API_URI + "/" + code)
        .build());
  }
}
