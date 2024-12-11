package com.mercadolibre.coupon.domain.mapper;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.entity.coupon.Coupon;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CouponMapper {

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "amount", source = "amount")
  @Mapping(target = "itemIds", source = "itemIds")
  public abstract CalculateCouponDto fromCoupon(final Coupon coupon);

}
