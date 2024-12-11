package com.mercadolibre.coupon.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.entity.coupon.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class CouponMapperTest {

  private CouponMapper couponMapper = new CouponMapperImpl();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    couponMapper = new CouponMapperImpl();
  }

  @Test
  void fromCoupon() {
    final Coupon coupon = new Coupon(List.of("MLA123"), 10f);

    final CalculateCouponDto couponMapped = couponMapper.fromCoupon(coupon);

    assertEquals(coupon.itemIds(), couponMapped.itemIds());
    assertEquals(coupon.amount(), couponMapped.amount());
  }

  @Test
  void fromCouponWhenNull() {
    assertNull(couponMapper.fromCoupon(null));
  }
}