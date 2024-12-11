package com.mercadolibre.coupon.domain.service.coupon.implementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import org.springframework.stereotype.Service;

/**
 * Employs an algorithm focused on an intensive search for the best possible combination to * maximize the coupon value. This approach
 * prioritizes accuracy and optimal results but sacrifices scalability due to its high * computational complexity.
 */
@Service
public class OptimizedCalculationCouponService implements CalculationCouponService {

  /**
   * Calculates the optimal list of items that maximizes the max amount specified for the coupon. Time Complexity: O(2^n).
   *
   * @param items ItemIds with prices
   * @param amount Maximum amount the coupon can cover.
   * @return List of itemIds that maximize the usage of the coupon.
   */
  public List<String> calculate(final Map<String, Float> items, final Float amount) {
    // Fast check for any item with exact same price as amount, so we can avoid complex algorithm.
    final Optional<Entry<String, Float>> itemWithMaxAmount =
        items.entrySet().stream().filter(itemEntry -> Objects.equals(itemEntry.getValue(), amount)).findAny();

    if (itemWithMaxAmount.isPresent()) {
      return List.of(itemWithMaxAmount.get().getKey());
    }

    // Sort items by price ascending
    final List<Item> itemList = items.entrySet().stream().map(itemEntry -> new Item(itemEntry.getKey(), itemEntry.getValue()))
        .sorted(Comparator.comparing(Item::price)).toList();

    // Wrapper for storing the best result
    final TemporaryResults bestResult = new TemporaryResults();
    checkCombinations(itemList, new ArrayList<>(), 0, amount, 0, bestResult);
    return bestResult.result;
  }

  private void checkCombinations(final List<Item> items, final List<String> current, final int start, final float remaining,
      final float currentSum, final TemporaryResults bestResult) {
    // If we exceed the amount, stop recursion
    if (remaining < 0) {
      return;
    }

    // Update best result if this combination is better
    if (remaining == 0 || currentSum > bestResult.maxSum) {
      bestResult.result = new ArrayList<>(current);
      bestResult.maxSum = currentSum;
    }

    // Iterate checking all combinations recursively
    for (int i = start; i < items.size(); i++) {
      final Item item = items.get(i);
      current.add(item.id());
      checkCombinations(items, current, i + 1, remaining - item.price(), currentSum + item.price(), bestResult);
      current.removeLast();
    }
  }

  private static class TemporaryResults {
    List<String> result = new ArrayList<>();

    float maxSum = 0;
  }
}
