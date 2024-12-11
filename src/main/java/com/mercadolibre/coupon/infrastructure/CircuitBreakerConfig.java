package com.mercadolibre.coupon.infrastructure;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {

  @Value("${cb.open-state.default-duration:10000}")
  private int defaultDurationOpenState;

  @Value("${cb.half-open-state.number-of-calls:2}")
  private int defaultPermittedNumberOfCallsInHalfOpenState;

  @Value("${cb.failure-rate-threshold:50}")
  private int defaultFailureRateThreshold;

  @Value("${cb.default-sliding-window:2}")
  private int defaultSlidingWindow;

  private static final String ITEMS_CB = "ItemsCB";

  @Bean
  public CircuitBreaker itemsCircuitBreaker() {
    io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig =
        io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
            .failureRateThreshold(defaultFailureRateThreshold)
            .waitDurationInOpenState(Duration.ofMillis(defaultDurationOpenState))
            .permittedNumberOfCallsInHalfOpenState(defaultPermittedNumberOfCallsInHalfOpenState)
            .slidingWindowSize(defaultSlidingWindow)
            .recordExceptions(Exception.class).build();

    CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
    return circuitBreakerRegistry.circuitBreaker(ITEMS_CB);
  }

}
