package com.mercadolibre.coupon.output.rest.items.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class ItemMapperTest {

  private ItemMapper itemMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    itemMapper = new ItemMapperImpl();
  }

  @Test
  void fromItem() {
    final ItemDto itemDto = new ItemDto("MLA123", 10f);

    final Item itemMapped = itemMapper.fromItemDto(itemDto);

    assertEquals(itemDto.id(), itemMapped.id());
    assertEquals(itemDto.price(), itemMapped.price());
  }

  @Test
  void fromItemWhenNull() {
    assertNull(itemMapper.fromItemDto(null));
  }
}