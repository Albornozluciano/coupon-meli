package com.mercadolibre.coupon.domain.entity.item;

import java.io.Serial;
import java.io.Serializable;

public record Item(String id, Float price) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

}
