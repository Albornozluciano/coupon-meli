package com.mercadolibre.coupon.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * An enum of known errors with their HttpStatus, type and data.
 */
@Getter
public enum ValidationError {
  REQUIRED_PARAM("/error/invalid_param/required", "Required param is missing.", HttpStatus.BAD_REQUEST),
  INVALID_PARAM_VALUE("/error/invalid_param/value", "Value param is invalid.", HttpStatus.BAD_REQUEST),
  ITEMS_NOT_FOUND("/error/items/not_found", "Required entities not found.", HttpStatus.NOT_FOUND),
  METHOD_NOT_FOUND("/error/method/not_allowed", "Endpoint method not allowed.", HttpStatus.NOT_FOUND);

  private final String type;

  private final String title;

  private final HttpStatus status;

  ValidationError(String type, String title, HttpStatus status) {
    this.type = type;
    this.title = title;
    this.status = status;
  }

}