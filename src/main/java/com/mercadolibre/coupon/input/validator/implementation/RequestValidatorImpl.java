package com.mercadolibre.coupon.input.validator.implementation;

import static com.mercadolibre.coupon.domain.error.ValidationError.INVALID_PARAM_VALUE;
import static com.mercadolibre.coupon.domain.error.ValidationError.REQUIRED_PARAM;

import java.util.Objects;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.input.validator.RequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RequestValidatorImpl implements RequestValidator {

  @Override
  public void validate(final CalculateCouponRequestDto calculateCouponRequestDto) throws CustomException {
    if (CollectionUtils.isEmpty(calculateCouponRequestDto.itemIds())) {
      throw new CustomException(new ApiError(REQUIRED_PARAM, "Invalid param. Item ids are required."));
    }

    if (Objects.isNull(calculateCouponRequestDto.amount())) {
      throw new CustomException(new ApiError(REQUIRED_PARAM, "Invalid param. Amount is required."));
    }

    if (calculateCouponRequestDto.amount() <= 0) {
      throw new CustomException(new ApiError(INVALID_PARAM_VALUE, "Invalid param. Amount should not be negative or zero."));
    }
  }
}
