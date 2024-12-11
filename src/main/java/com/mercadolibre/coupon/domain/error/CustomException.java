package com.mercadolibre.coupon.domain.error;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;

/**
 * A Custom exception to add and forward an ApiError and a Throwable to decide what to do at the moment of the server response.
 */
@Getter
public class CustomException extends Exception implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final ApiError apiError;

  public CustomException(final ApiError apiError) {
    super();
    this.apiError = apiError;
  }

}