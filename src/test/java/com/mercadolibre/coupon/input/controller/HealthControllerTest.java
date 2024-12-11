package com.mercadolibre.coupon.input.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class HealthControllerTest {

  @Test
  void health() {
    assertEquals(HttpStatus.OK.value(), new HealthController().health().getStatusCode().value());
  }
}