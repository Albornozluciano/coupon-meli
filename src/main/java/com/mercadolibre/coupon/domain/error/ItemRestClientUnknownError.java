package com.mercadolibre.coupon.domain.error;

import java.io.Serial;
import java.io.Serializable;

public class ItemRestClientUnknownError extends RuntimeException implements Serializable {

  @Serial
  private static final long serialVersionUID = -8693669213669948708L;

  public ItemRestClientUnknownError(final Exception ex) {
    super(ex);
  }
}
