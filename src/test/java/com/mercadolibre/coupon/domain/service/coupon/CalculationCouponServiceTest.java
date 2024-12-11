package com.mercadolibre.coupon.domain.service.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mercadolibre.coupon.domain.service.coupon.implementation.FastSequentialCalculationCouponService;
import com.mercadolibre.coupon.domain.service.coupon.implementation.OptimizedCalculationCouponService;
import org.junit.jupiter.api.Test;

class CalculationCouponServiceTest {

  @Test
  void calculateFastCalculationServiceWhenOnlyOneCheapItem() {
    final CalculationCouponService calculationCouponService = new FastSequentialCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of("MLA123", 1f), 10f);

    assertEquals(List.of("MLA123"), calculate);
  }

  @Test
  void calculateFastCalculationServiceWithoutItems() {
    final CalculationCouponService calculationCouponService = new FastSequentialCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of(), 10f);

    assertEquals(List.of(), calculate);
  }

  @Test
  void calculateFastCalculationServiceWhenOnlyOneExpensiveItem() {
    final CalculationCouponService calculationCouponService = new FastSequentialCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of("MLA123", 100f), 10f);

    assertEquals(List.of(), calculate);
  }

  @Test
  void calculateFastCalculationServiceWhenMultipleItems() {
    final CalculationCouponService calculationCouponService = new FastSequentialCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(buildMapWithExerciseData(), 500f);

    assertEquals(List.of("MLA1", "MLA2", "MLA4", "MLA5"), calculate);
  }

  @Test
  void calculateFastCalculationServiceWhenItemsPriceEqualsToMax() {
    final CalculationCouponService calculationCouponService = new FastSequentialCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(buildMapWithExerciseData(), 310f);

    assertEquals(List.of("MLA1", "MLA2"), calculate);
  }

  @Test
  void calculateOptimizedCalculationServiceWhenOnlyOneCheapItem() {
    final CalculationCouponService calculationCouponService = new OptimizedCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of("MLA123", 1f), 10f);

    assertEquals(List.of("MLA123"), calculate);
  }

  @Test
  void calculateOptimizedCalculationServiceWhenOnlyOneExpensiveItem() {
    final CalculationCouponService calculationCouponService = new OptimizedCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of("MLA123", 100f), 10f);

    assertEquals(List.of(), calculate);
  }

  @Test
  void calculateOptimizedCalculationServiceWithoutItems() {
    final CalculationCouponService calculationCouponService = new OptimizedCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(Map.of(), 10f);

    assertEquals(List.of(), calculate);
  }

  @Test
  void calculateOptimizedCalculationServiceWhenMultipleItems() {
    final CalculationCouponService calculationCouponService = new OptimizedCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(buildMapWithExerciseData(), 500f);

    assertEquals(Stream.of("MLA5", "MLA4", "MLA3", "MLA1").sorted().collect(Collectors.toList()),
        calculate.stream().sorted().collect(Collectors.toList()));
  }

  @Test
  void calculateOptimizedCalculationServiceWhenItemsPriceEqualsToMax() {
    final CalculationCouponService calculationCouponService = new OptimizedCalculationCouponService();

    final List<String> calculate = calculationCouponService.calculate(buildMapWithExerciseData(), 220f);

    assertEquals(List.of("MLA3"), calculate);
  }

  private Map<String, Float> buildMapWithExerciseData() {
    final Map<String, Float> map = new LinkedHashMap<>();
    map.put("MLA1", 100f);
    map.put("MLA2", 210f);
    map.put("MLA3", 220f);
    map.put("MLA4", 80f);
    map.put("MLA5", 90f);
    return map;
  }
}