package edu.ecommerce.cart.cucumber;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import edu.ecommerce.cart.boot.CartApplication;

@SpringBootTest(classes = CartApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
@ActiveProfiles("test")
public class SpringCucumberTestConfig {}
