package com.mercadolibre.coupon.domain.service.coupon.config;

import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import com.mercadolibre.coupon.domain.service.coupon.implementation.FastSequentialCalculationCouponService;
import com.mercadolibre.coupon.domain.service.coupon.implementation.OptimizedCalculationCouponService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Selects the implementation of the coupon calculation service based on the configured property.
 * - **FastSequentialCalculationCouponService**: (By Default) Uses a fast, sequential algorithm designed to provide results that aim to maximize the
 * coupon value. This implementation is highly scalable, capable of handling a large number of products and high maximum amounts
 * efficiently. However, it does not evaluate all possible combinations, so it may not always return the optimal solution.
 * - **OptimizedCalculationCouponService**: Employs an algorithm focused on an intensive search for the best possible combination to
 * maximize the coupon value. This approach prioritizes accuracy and optimal results but sacrifices scalability due to its high
 * computational complexity.
 */
@Configuration
public class ConfigCalculationService {

  private final boolean useFastAlgorithm;

  public ConfigCalculationService(@Value("${business.use-fast-algorithm}") boolean useFastAlgorithm) {
    this.useFastAlgorithm = useFastAlgorithm;
  }

  @Bean
  public CalculationCouponService calculationCouponService() {
    return useFastAlgorithm ? new FastSequentialCalculationCouponService() : new OptimizedCalculationCouponService();
  }
}
