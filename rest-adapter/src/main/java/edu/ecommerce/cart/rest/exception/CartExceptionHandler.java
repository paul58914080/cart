package edu.ecommerce.cart.rest.exception;

import edu.ecommerce.cart.domain.exception.CartNotFoundException;
import edu.ecommerce.cart.rest.generated.model.ProblemDetail;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(basePackages = {"edu.ecommerce.cart"})
public class CartExceptionHandler {

  @ExceptionHandler(value = CartNotFoundException.class)
  public final ResponseEntity<ProblemDetail> handleCartNotFoundException(
      final Exception exception, final WebRequest request) {
    var problem =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .title("Cart not found")
            .detail(exception.getMessage())
            .instance(((ServletWebRequest) request).getRequest().getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }
}
