package edu.ecommerce.cart.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "edu.ecommerce.cart")
public class CartApplication {

  public static void main(String[] args) {
    SpringApplication.run(CartApplication.class, args);
  }
}
