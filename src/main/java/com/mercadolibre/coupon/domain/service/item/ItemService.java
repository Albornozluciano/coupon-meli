package com.mercadolibre.coupon.domain.service.item;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.item.GetItemsSpecification;
import com.mercadolibre.coupon.domain.entity.item.Item;

public interface ItemService {

  List<Item> getItems(final GetItemsSpecification getItemsSpecification);
}
