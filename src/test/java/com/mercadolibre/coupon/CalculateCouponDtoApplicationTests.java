package com.mercadolibre.coupon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalculateCouponDtoApplicationTests {

  @Test
  void contextLoads() {
    CouponApplication.main(new String[]{});
    assertTrue(true);
  }

}
