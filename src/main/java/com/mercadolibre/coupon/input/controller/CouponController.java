package com.mercadolibre.coupon.input.controller;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponDto;
import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.error.ApiError;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.domain.mapper.CouponMapper;
import com.mercadolibre.coupon.domain.usecase.CalculateCouponUseCase;
import com.mercadolibre.coupon.input.validator.RequestValidator;
import com.mercadolibre.coupon.input.validator.ResponseValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/coupon")
public class CouponController {

  private final CalculateCouponUseCase calculateCouponUseCase;

  private final RequestValidator requestValidator;

  private final ResponseValidator responseValidator;

  private final CouponMapper couponMapper;

  public CouponController(final CalculateCouponUseCase calculateCouponUseCase, RequestValidator requestValidator,
      ResponseValidator responseValidator, final CouponMapper couponMapper) {
    this.calculateCouponUseCase = calculateCouponUseCase;
    this.requestValidator = requestValidator;
    this.responseValidator = responseValidator;
    this.couponMapper = couponMapper;
  }

  @Operation(summary = "Retrieve items eligible for a coupon",
      description = "Returns a set of item IDs for products that maximize the value of the provided coupon based on the request "
          + "parameters.", tags = {"Coupons"})
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalculateCouponDto.class))),
      @ApiResponse(responseCode = "404", description = "Coupon not available",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
      @ApiResponse(responseCode = "400", description = "Invalid parameter",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
  @PostMapping
  public ResponseEntity<CalculateCouponDto> calculateCoupon(@RequestBody final CalculateCouponRequestDto calculateCouponRequestDto)
      throws CustomException {
    this.requestValidator.validate(calculateCouponRequestDto);
    final CalculateCouponDto couponResponse = couponMapper.fromCoupon(this.calculateCouponUseCase.dispatch(calculateCouponRequestDto));
    this.responseValidator.validate(couponResponse);
    return ResponseEntity.ok(couponResponse);
  }
}
