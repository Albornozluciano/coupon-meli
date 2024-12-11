package com.mercadolibre.coupon.domain.repository;

import com.mercadolibre.coupon.domain.entity.item.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository {

  Item getItem(final String id);
}
