package com.mercadolibre.coupon.domain.service.coupon;

import java.util.List;
import java.util.Map;

public interface CalculationCouponService {

  List<String> calculate(final Map<String, Float> items, final Float amount);

}
