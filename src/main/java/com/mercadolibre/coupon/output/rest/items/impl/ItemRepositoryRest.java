package com.mercadolibre.coupon.output.rest.items.impl;

import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.repository.ItemRepository;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import com.mercadolibre.coupon.output.rest.items.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class ItemRepositoryRest implements ItemRepository {

  private final ItemRepositoryRestCB itemRepositoryRestCB;

  private final ItemMapper itemMapper;

  public Item getItem(String id) {
    return itemMapper.fromItemDto(itemRepositoryRestCB.getItem(new GetItemRequest(id)).orElse(null));
  }
}
