package edu.ecommerce.cart.cucumber;

import static io.cucumber.junit.platform.engine.Constants.*;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.ConfigurationParameters;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/cart.feature")
@ConfigurationParameters({
  @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "edu.ecommerce.cart.cucumber"),
  @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@Cart"),
  @ConfigurationParameter(key = JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME, value = "long"),
  @ConfigurationParameter(key = PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true"),
  @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:target/cucumber/cucumber.json")
})
public class RunCucumberCartTest {}
