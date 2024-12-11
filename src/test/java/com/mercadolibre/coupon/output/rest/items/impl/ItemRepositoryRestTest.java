package com.mercadolibre.coupon.output.rest.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import com.mercadolibre.coupon.output.rest.items.mapper.ItemMapper;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryRestTest {

  @Mock
  private ItemRepositoryRestCB itemRepositoryRestCB;

  @Mock
  private ItemMapper itemMapper;

  @InjectMocks
  private ItemRepositoryRest itemRepositoryRest;

  @Test
  void getItem() {
    final ItemDto itemDto = new ItemDto("MLA123", 1f);
    when(this.itemRepositoryRestCB.getItem(new GetItemRequest("MLA123"))).thenReturn(Optional.of(itemDto));
    when(this.itemMapper.fromItemDto(itemDto)).thenReturn(new Item("MLA123", 1f));

    final Item item = this.itemRepositoryRest.getItem("MLA123");

    assertNotNull(item);
    assertEquals("MLA123", item.id());
    assertEquals(1f, item.price());
  }

  @Test
  void getItemNull() {
    when(this.itemRepositoryRestCB.getItem(new GetItemRequest("MLA123"))).thenReturn(Optional.empty());

    final Item item = this.itemRepositoryRest.getItem("MLA123");

    assertNull(item);
  }
}