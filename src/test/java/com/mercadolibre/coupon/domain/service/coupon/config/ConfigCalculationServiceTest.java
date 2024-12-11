package com.mercadolibre.coupon.domain.service.coupon.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import com.mercadolibre.coupon.domain.service.coupon.implementation.FastSequentialCalculationCouponService;
import com.mercadolibre.coupon.domain.service.coupon.implementation.OptimizedCalculationCouponService;
import org.junit.jupiter.api.Test;

class ConfigCalculationServiceTest {

  @Test
  void calculationCouponServiceConfiguration() {
    ConfigCalculationService configCalculationService = new ConfigCalculationService(true);
    CalculationCouponService calculationCouponService = configCalculationService.calculationCouponService();
    assertTrue(calculationCouponService instanceof FastSequentialCalculationCouponService);

    configCalculationService = new ConfigCalculationService(false);
    calculationCouponService = configCalculationService.calculationCouponService();
    assertTrue(calculationCouponService instanceof OptimizedCalculationCouponService);
  }
}