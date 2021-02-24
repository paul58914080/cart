package org.dfm.cart.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.dfm.cart.CartE2EApplication;
import org.dfm.cart.domain.model.Cart;
import org.dfm.cart.domain.model.CartInfo;
import org.dfm.cart.repository.dao.CartDao;
import org.dfm.cart.repository.entity.CartEntity;
import org.dfm.cart.rest.exception.CartExceptionResponse;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CartE2EApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
public class CartStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/carts";
  @LocalServerPort
  private int port;
  private ResponseEntity responseEntity;

  public CartStepDef(TestRestTemplate restTemplate, CartDao cartDao) {

    DataTableType(
        (Map<String, String> row) -> Cart.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description")).build());
    DataTableType(
        (Map<String, String> row) -> CartEntity.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description"))
            .build());

    Before((HookNoArgsBody) cartDao::deleteAll);
    After((HookNoArgsBody) cartDao::deleteAll);

    Given("the following carts exists in the library", (DataTable dataTable) -> {
      List<CartEntity> poems = dataTable.asList(CartEntity.class);
      cartDao.saveAll(poems);
    });

    When("user requests for all carts", () -> {
      String url = LOCALHOST + port + API_URI;
      responseEntity = restTemplate.getForEntity(url, CartInfo.class);
    });

    When("user requests for carts by code {string}", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, Cart.class);
    });

    When("user requests for carts by id {string} that does not exists", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, CartExceptionResponse.class);
    });

    Then("the user gets an exception {string}", (String exception) -> {
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      assertThat(body).isInstanceOf(CartExceptionResponse.class);
      assertThat(((CartExceptionResponse) body).getMessage()).isEqualTo(exception);
    });

    Then("the user gets the following carts", (DataTable dataTable) -> {
      List<Cart> expectedCarts = dataTable.asList(Cart.class);
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      if (body instanceof CartInfo) {
        assertThat(((CartInfo) body).getCarts()).isNotEmpty().extracting("description")
            .containsAll(expectedCarts.stream().map(Cart::getDescription)
                .collect(Collectors.toList()));
      } else if (body instanceof Cart) {
        assertThat(body).isNotNull().extracting("description")
            .isEqualTo(expectedCarts.get(0).getDescription());
      }
    });
  }
}


