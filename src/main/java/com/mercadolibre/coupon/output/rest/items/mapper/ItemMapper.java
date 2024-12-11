package com.mercadolibre.coupon.output.rest.items.mapper;

import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

  Item fromItemDto(final ItemDto itemDto);
}
