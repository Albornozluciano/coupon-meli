package com.mercadolibre.coupon.domain.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {

  @Test
  void buildInternalServerError() {
    final ApiError apiError = ApiError.buildInternalServerError();

    assertEquals("/error/internalServerError", apiError.getType());
    assertEquals("Unknown Internal Server error.", apiError.getTitle());
    assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), apiError.getStatus());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getCode());
    assertEquals("Unknown Internal Server error.", apiError.getDetail());
  }

  @Test
  void createApiErrorFromValidationError() {
    final String expectedDetail = "Detail";

    final ApiError apiError = new ApiError(ValidationError.INVALID_PARAM_VALUE, expectedDetail);

    assertEquals(ValidationError.INVALID_PARAM_VALUE.getType(), apiError.getType());
    assertEquals(ValidationError.INVALID_PARAM_VALUE.getTitle(), apiError.getTitle());
    assertEquals(ValidationError.INVALID_PARAM_VALUE.getStatus().getReasonPhrase(), apiError.getStatus());
    assertEquals(ValidationError.INVALID_PARAM_VALUE.getStatus().value(), apiError.getCode());
    assertEquals(expectedDetail, apiError.getDetail());
  }

}