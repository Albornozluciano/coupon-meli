package com.mercadolibre.coupon.domain.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.entity.item.Item;

public class ItemUtils {

  private ItemUtils() {

  }

  public static Map<String, Float> groupItemsByPrice(final CalculateCouponRequestDto calculateCouponRequestDto,
      final List<Item> items) {
    return items.stream()
        .filter(item -> item.price() != null && item.price() <= calculateCouponRequestDto.amount())
        .collect(Collectors.toMap(
            Item::id,
            Item::price,
            (x, y) -> x,
            LinkedHashMap::new
        ));
  }
}