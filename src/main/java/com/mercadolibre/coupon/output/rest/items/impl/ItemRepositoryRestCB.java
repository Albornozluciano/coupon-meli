package com.mercadolibre.coupon.output.rest.items.impl;

import java.util.Optional;

import com.mercadolibre.coupon.domain.error.ItemRestClientUnknownError;
import com.mercadolibre.coupon.output.rest.items.client.ItemRestClient;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class ItemRepositoryRestCB {

  private final ItemRestClient itemRestClient;

  private final CircuitBreaker itemsCircuitBreaker;

  public Optional<ItemDto> getItem(final GetItemRequest itemRequest) {
    try {
      return itemsCircuitBreaker.executeSupplier(() -> itemRestClient.getItem(itemRequest));
    } catch (ItemRestClientUnknownError error) {
      log.warn("Unknown Error in items api", error);
      return Optional.empty();
    }
  }
}
