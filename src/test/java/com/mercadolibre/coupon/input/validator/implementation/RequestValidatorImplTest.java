package com.mercadolibre.coupon.input.validator.implementation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.error.CustomException;
import org.junit.jupiter.api.Test;

class RequestValidatorImplTest {

  RequestValidatorImpl requestValidator = new RequestValidatorImpl();

  @Test
  void validateSuccessfully() throws CustomException {
    requestValidator.validate(new CalculateCouponRequestDto(List.of("MLA123"), 1f));
  }

  @Test
  void validateEmptyItems() throws CustomException {
    assertThrows(CustomException.class, () -> requestValidator.validate(new CalculateCouponRequestDto(List.of(), 1f)));
  }

  @Test
  void validateNullAmount() throws CustomException {
    assertThrows(CustomException.class, () -> requestValidator.validate(new CalculateCouponRequestDto(List.of("MLA123"), null)));
  }

  @Test
  void validateNegativeAmount() throws CustomException {
    assertThrows(CustomException.class, () -> requestValidator.validate(new CalculateCouponRequestDto(List.of("MLA123"), -1f)));
  }
}