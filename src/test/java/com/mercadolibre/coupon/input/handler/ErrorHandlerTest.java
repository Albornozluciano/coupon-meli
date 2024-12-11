package com.mercadolibre.coupon.input.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.domain.error.ValidationError;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {

  private ErrorHandler errorHandler;

  @Mock
  private HttpServletResponse response;

  @BeforeEach
  void setUp() {
    errorHandler = new ErrorHandler();
  }

  @Test
  void handleThrowableShouldReturnInternalServerError() {
    Throwable throwable = new Throwable("Test error");

    ApiError result = errorHandler.handleThrowable(throwable);

    assertNotNull(result);
    assertEquals("Unknown Internal Server error.", result.getDetail());
    assertEquals("/error/internalServerError", result.getType());
    assertEquals("Unknown Internal Server error.", result.getTitle());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), result.getStatus());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getCode());
  }

  @Test
  void handleCustomExceptionShouldReturnApiErrorFromException() throws Exception {
    ApiError apiError = new ApiError(ValidationError.REQUIRED_PARAM, "Custom error message");
    CustomException customException = new CustomException(apiError);

    ApiError result = errorHandler.handleCustomException(customException, response);

    assertNotNull(result);
    assertEquals(apiError.getCode(), result.getCode());
    assertEquals(apiError.getDetail(), result.getDetail());
  }
}