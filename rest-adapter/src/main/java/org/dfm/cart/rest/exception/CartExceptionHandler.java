package org.dfm.cart.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.dfm.cart.domain.exception.CartNotFoundException;

@RestControllerAdvice(basePackages = {"org.dfm.cart"})
public class CartExceptionHandler {

  @ExceptionHandler(value = CartNotFoundException.class)
  public final ResponseEntity<CartExceptionResponse> handleCartNotFoundException(
      final Exception exception, final WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        CartExceptionResponse.builder().message(exception.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }
}
