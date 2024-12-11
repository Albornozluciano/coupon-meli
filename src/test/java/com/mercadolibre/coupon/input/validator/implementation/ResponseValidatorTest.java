package com.mercadolibre.coupon.input.validator.implementation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.error.CustomException;
import org.junit.jupiter.api.Test;

class ResponseValidatorTest {

  @Test
  void validateThrowsCustomException() {
    final ResponseValidatorImpl validator = new ResponseValidatorImpl();

    assertThrows(CustomException.class, () -> validator.validate(new CalculateCouponDto(null, 0)));
  }

  @Test
  void validateSuccessfully() throws CustomException {
    final ResponseValidatorImpl validator = new ResponseValidatorImpl();
    validator.validate(new CalculateCouponDto(List.of("MLA123"), 0));
  }

}