package com.mercadolibre.coupon.input.validator;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.error.CustomException;

public interface RequestValidator {

  void validate(final CalculateCouponRequestDto calculateCouponRequestDto) throws CustomException;
}
