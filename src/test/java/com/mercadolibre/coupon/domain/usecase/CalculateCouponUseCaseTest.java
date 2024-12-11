package com.mercadolibre.coupon.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;

import com.mercadolibre.coupon.domain.dto.coupon.CalculateCouponRequestDto;
import com.mercadolibre.coupon.domain.dto.item.GetItemsSpecification;
import com.mercadolibre.coupon.domain.entity.coupon.Coupon;
import com.mercadolibre.coupon.domain.entity.item.Item;
import com.mercadolibre.coupon.domain.service.coupon.CalculationCouponService;
import com.mercadolibre.coupon.domain.service.item.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculateCouponUseCaseTest {

  private static final String ITEM_1 = "MLA123";

  private static final String ITEM_2 = "MLA234";

  private static final String ITEM_3 = "MLA345";

  private static final String ITEM_4 = "MLA456";

  private static final float PRICE_ITEM_2 = 111f;

  private static final float PRICE_ITEM_1 = 1f;

  private static final float MAX_AMOUNT = 10f;

  @Mock
  private ItemService itemService;

  @Mock
  private CalculationCouponService calculationCouponService;

  @InjectMocks
  private CalculateCouponUseCase calculateCouponUseCase;

  @Test
  void dispatchCalculateCouponUseCase() {
    final GetItemsSpecification getItemsSpecification = new GetItemsSpecification(List.of(ITEM_1, ITEM_2, ITEM_3, ITEM_4));
    final List<Item> itemDetails = List.of(
        new Item(ITEM_1, PRICE_ITEM_1),
        new Item(ITEM_2, PRICE_ITEM_2),
        new Item(ITEM_3, null),
        new Item(ITEM_4, MAX_AMOUNT)
    );
    when(itemService.getItems(getItemsSpecification)).thenReturn(itemDetails);
    when(calculationCouponService.calculate(buildLinkedMap(), MAX_AMOUNT)).thenReturn(List.of(ITEM_4));

    final Coupon coupon = calculateCouponUseCase.dispatch(new CalculateCouponRequestDto(
        List.of(ITEM_1, ITEM_1, ITEM_2, ITEM_3, ITEM_4), MAX_AMOUNT));

    assertEquals(List.of(ITEM_4), coupon.itemIds());
    assertEquals(MAX_AMOUNT, coupon.amount());
  }

  private LinkedHashMap<String, Float> buildLinkedMap() {
    final LinkedHashMap<String, Float> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put(ITEM_1, PRICE_ITEM_1);
    linkedHashMap.put(ITEM_4, MAX_AMOUNT);
    return linkedHashMap;
  }
}