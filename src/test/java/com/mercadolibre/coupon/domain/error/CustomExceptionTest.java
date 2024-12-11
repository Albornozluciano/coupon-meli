package com.mercadolibre.coupon.domain.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomExceptionTest {

  @Test
  void createCustomException() {
    final ApiError apiError = ApiError.buildInternalServerError();

    final CustomException customException = new CustomException(apiError);

    assertEquals(apiError, customException.getApiError());
  }

}