package com.mercadolibre.coupon.domain.usecase;

import java.util.List;
import java.util.Map;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.dto.item.GetItemsSpecification;
import com.mercadolibre.coupon.domain.entity.coupon.Coupon;
import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.error.CustomException;
import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import com.mercadolibre.coupon.domain.service.item.ItemService;
import com.mercadolibre.coupon.domain.utils.ItemUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@AllArgsConstructor
@Slf4j
public class CalculateCouponUseCase {

  private final ItemService itemService;

  private final CalculationCouponService calculationCouponService;

  /**
   * Retrieves the item details, groups them by their prices, calculates the optimal set of items that maximize the coupon amount, and
   * finally returns the resulting coupon.
   *
   * @param calculateCouponRequestDto Contains the itemIds and the maximum coupon amount.
   * @return Coupon object containing the list of itemIds that maximize the coupon value and their total price.
   * @throws CustomException if an error occurs while retrieving item details
   */
  public Coupon dispatch(final CalculateCouponRequestDto calculateCouponRequestDto) {
    log.debug("Get calculated coupon with calculateCouponRequestDto: {}", calculateCouponRequestDto);
    final List<Item> items = itemService.getItems(new GetItemsSpecification(getDistinctItemIds(calculateCouponRequestDto)));
    final Map<String, Float> itemsByPrice = ItemUtils.groupItemsByPrice(calculateCouponRequestDto, items);
    final List<String> itemIds = calculationCouponService.calculate(itemsByPrice, calculateCouponRequestDto.amount());
    return new Coupon(itemIds, this.calculateItemsAmount(itemIds, itemsByPrice));
  }

  private List<String> getDistinctItemIds(final CalculateCouponRequestDto calculateCouponRequestDto) {
    return calculateCouponRequestDto.itemIds().stream().distinct().toList();
  }

  private Float calculateItemsAmount(final List<String> itemIds, final Map<String, Float> itemsByPrice) {
    return itemIds.stream().map(itemsByPrice::get).reduce(0f, Float::sum);
  }

}
