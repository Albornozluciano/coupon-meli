package com.mercadolibre.coupon.input.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.entity.coupon.Coupon;
import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.domain.mapper.CouponMapper;
import com.mercadolibre.coupon.domain.usecase.CalculateCouponUseCase;
import com.mercadolibre.coupon.input.validator.RequestValidator;
import com.mercadolibre.coupon.input.validator.ResponseValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

  private static final String ITEM_ID = "MLA123";

  private static final float AMOUNT = 1f;

  @Mock
  private CalculateCouponUseCase calculateCouponUseCase;

  @Mock
  private RequestValidator requestValidator;

  @Mock
  private ResponseValidator responseValidator;

  @Mock
  private CouponMapper couponMapper;

  @InjectMocks
  private CouponController couponController;

  @Test
  void calculateCoupon() throws CustomException {
    final CalculateCouponRequestDto requestDto = new CalculateCouponRequestDto(List.of(ITEM_ID), AMOUNT);
    when(this.calculateCouponUseCase.dispatch(requestDto)).thenReturn(new Coupon(List.of(ITEM_ID), AMOUNT));

    ResponseEntity<CalculateCouponDto> calculateCouponDtoResponseEntity = couponController.calculateCoupon(requestDto);

    assertEquals(HttpStatus.OK.value(), calculateCouponDtoResponseEntity.getStatusCode().value());
  }

  @Test
  void calculateCouponError() throws CustomException {
    final CalculateCouponRequestDto requestDto = new CalculateCouponRequestDto(List.of(ITEM_ID), AMOUNT);
    doThrow(new CustomException(ApiError.buildInternalServerError())).when(this.requestValidator).validate(requestDto);

    assertThrows(CustomException.class, () -> this.couponController.calculateCoupon(requestDto));
  }

}