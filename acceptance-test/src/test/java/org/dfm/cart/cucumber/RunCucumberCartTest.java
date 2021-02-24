package org.dfm.cart.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", strict = true, plugin = {
    "json:target/cucumber/cart.json", "json:target/cucumber/cart.xml"}, tags =
    "@Cart", glue = "classpath:org.dfm.cart.cucumber")
public class RunCucumberCartTest {

}
