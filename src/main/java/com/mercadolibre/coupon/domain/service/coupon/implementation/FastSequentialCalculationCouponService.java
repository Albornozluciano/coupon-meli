package com.mercadolibre.coupon.domain.service.coupon.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import org.springframework.stereotype.Service;

/**
 * Uses a fast, sequential algorithm designed to provide results that aim to maximize the coupon value. This implementation is highly
 * scalable, capable of handling a large number of products and high maximum amounts efficiently. However, it does not evaluate all possible
 * combinations, so it may not always return the optimal solution.
 */
@Service
public class FastSequentialCalculationCouponService implements CalculationCouponService {

  /**
   * Calculates the optimal list of items that maximizes the max amount specified for the coupon. Time Complexity: O(2^n).
   *
   * @param items ItemIds with prices
   * @param amount Maximum amount the coupon can cover.
   * @return List of itemIds that maximize the usage of the coupon.
   */
  public List<String> calculate(final Map<String, Float> items, final Float amount) {
    final List<String> result = new ArrayList<>();
    float total = 0;

    for (final Map.Entry<String, Float> itemWithPrice : items.entrySet()) {
      if (total + itemWithPrice.getValue() <= amount) {
        result.add(itemWithPrice.getKey());
        total += itemWithPrice.getValue();
      }
      if (amount.equals(total)) {
        break;
      }
    }
    return result;
  }

}
