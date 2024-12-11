package com.mercadolibre.coupon.domain.error;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * An Api Error structure based on RFC-7807: https://datatracker.ietf.org/doc/html/rfc7807
 */
@Getter
public class ApiError implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String type = "/error/";

  private String title = "Unknown error";

  private String status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

  private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

  private String detail = "Unknown error";

  private ApiError() {
  }

  public ApiError(final ValidationError knownError, final String detailMessage) {
    this.type = knownError.getType();
    this.title = knownError.getTitle();
    this.status = knownError.getStatus().getReasonPhrase();
    this.code = knownError.getStatus().value();
    this.detail = detailMessage;
  }

  public static ApiError buildInternalServerError() {
    final ApiError apiError = new ApiError();
    apiError.type = "/error/internalServerError";
    apiError.title = "Unknown Internal Server error.";
    apiError.status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    apiError.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    apiError.detail = "Unknown Internal Server error.";
    return apiError;
  }

}