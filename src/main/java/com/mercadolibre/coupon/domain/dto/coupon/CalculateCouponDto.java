package com.mercadolibre.coupon.domain.dto.coupon;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CalculateCouponDto(@JsonProperty("item_ids") List<String> itemIds, @JsonProperty("amount") float amount)
    implements Serializable {

  @Serial
  private static final long serialVersionUID = 1;

}
