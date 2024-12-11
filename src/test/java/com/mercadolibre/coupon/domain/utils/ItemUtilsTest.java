package com.mercadolibre.coupon.domain.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.entity.item.Item;
import org.junit.jupiter.api.Test;

class ItemUtilsTest {

  @Test
  void groupItemsByPriceShouldGroupItemsCorrectly() {
    final List<Item> items = List.of(
        new Item("MLA123", 10f),
        new Item("MLA124", 15f),
        new Item("MLA125", null),
        new Item("MLA123", 12f)
    );
    final CalculateCouponRequestDto requestDto = new CalculateCouponRequestDto(List.of("MLA123", "MLA124"), 15f);

    final Map<String, Float> result = ItemUtils.groupItemsByPrice(requestDto, items);

    assertEquals(2, result.size());
    assertEquals(10f, result.get("MLA123"));
    assertEquals(15f, result.get("MLA124"));
  }
}