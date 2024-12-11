package com.mercadolibre.coupon.input.validator.implementation;

import static com.mercadolibre.coupon.domain.error.ValidationError.ITEMS_NOT_FOUND;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.input.validator.ResponseValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ResponseValidatorImpl implements ResponseValidator {

  @Override
  public void validate(final CalculateCouponDto calculateCouponDto) throws CustomException {
    if (CollectionUtils.isEmpty(calculateCouponDto.itemIds())) {
      throw new CustomException(
          new ApiError(ITEMS_NOT_FOUND, "No items available that satisfy the condition of maximizing the provided amount."));
    }
  }
}
