package com.mercadolibre.coupon.input.validator;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.error.CustomException;

public interface ResponseValidator {

  void validate(final CalculateCouponDto calculateCouponDto) throws CustomException;

}
