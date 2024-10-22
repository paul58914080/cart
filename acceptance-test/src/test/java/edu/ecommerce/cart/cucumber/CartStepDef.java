package edu.ecommerce.cart.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import edu.ecommerce.cart.domain.model.Cart;
import edu.ecommerce.cart.repository.dao.CartDao;
import edu.ecommerce.cart.repository.entity.CartEntity;
import edu.ecommerce.cart.rest.generated.model.CartInfo;
import edu.ecommerce.cart.rest.generated.model.ProblemDetail;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CartStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/carts";
  @LocalServerPort private int port;
  private ResponseEntity responseEntity;

  public CartStepDef(TestRestTemplate restTemplate, CartDao cartDao) {

    DataTableType(
        (Map<String, String> row) ->
            Cart.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());
    DataTableType(
        (Map<String, String> row) ->
            CartEntity.builder()
                .code(Long.parseLong(row.get("code")))
                .description(row.get("description"))
                .build());

    Before((HookNoArgsBody) cartDao::deleteAll);
    After((HookNoArgsBody) cartDao::deleteAll);

    Given(
        "the following carts exists in the library",
        (DataTable dataTable) -> {
          List<CartEntity> poems = dataTable.asList(CartEntity.class);
          cartDao.saveAll(poems);
        });

    When(
        "user requests for all carts",
        () -> {
          String url = LOCALHOST + port + API_URI;
          responseEntity = restTemplate.getForEntity(url, CartInfo.class);
        });

    When(
        "user requests for carts by code {string}",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, Cart.class);
        });

    When(
        "user requests for carts by id {string} that does not exists",
        (String code) -> {
          String url = LOCALHOST + port + API_URI + "/" + code;
          responseEntity = restTemplate.getForEntity(url, ProblemDetail.class);
        });

    Then(
        "the user gets an exception {string}",
        (String exception) -> {
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
          var actualResponse = (ProblemDetail) responseEntity.getBody();
          var expectedProblemDetail =
              ProblemDetail.builder()
                  .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
                  .status(HttpStatus.NOT_FOUND.value())
                  .detail("Cart with code 10000 does not exist")
                  .instance("/api/v1/carts/10000")
                  .title("Cart not found")
                  .build();
          assertThat(actualResponse).isNotNull();
          assertThat(actualResponse)
              .usingRecursiveComparison()
              .ignoringFields("timestamp")
              .isEqualTo(expectedProblemDetail);
          assertThat(actualResponse.getTimestamp())
              .isCloseTo(LocalDateTime.now(), within(100L, ChronoUnit.SECONDS));
        });

    Then(
        "the user gets the following carts",
        (DataTable dataTable) -> {
          List<Cart> expectedCarts = dataTable.asList(Cart.class);
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          Object body = responseEntity.getBody();
          assertThat(body).isNotNull();
          if (body instanceof CartInfo) {
            assertThat(((CartInfo) body).getCarts())
                .isNotEmpty()
                .extracting("description")
                .containsAll(
                    expectedCarts.stream().map(Cart::getDescription).collect(Collectors.toList()));
          } else if (body instanceof Cart) {
            assertThat(body)
                .isNotNull()
                .extracting("description")
                .isEqualTo(expectedCarts.get(0).getDescription());
          }
        });
  }
}
