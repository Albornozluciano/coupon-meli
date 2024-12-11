package com.mercadolibre.coupon.input.handler;

import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handler that catches any unkonwn Internal Server Error and responds an ApiError structure with a friendly default response data. It also
 * catches CustomException to log any error that it forwards in its Throwable field and it sets the response status based on the ApiError.
 */
@RestController
@ControllerAdvice
@Slf4j
public class ErrorHandler {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public ApiError handleThrowable(final Throwable ex) {
    log.error("Unknown Internal Server error. Message: " + ex.getMessage() + " - Cause: " + ex.getCause(), ex);
    return ApiError.buildInternalServerError();
  }

  @ExceptionHandler(CustomException.class)
  public ApiError handleCustomException(final CustomException ex, final HttpServletResponse response) {
    log.warn("Custom exception.", ex);
    response.setStatus(ex.getApiError().getCode());
    return ex.getApiError();
  }
}