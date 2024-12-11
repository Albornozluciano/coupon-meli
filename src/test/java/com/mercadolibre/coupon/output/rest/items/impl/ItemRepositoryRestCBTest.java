package com.mercadolibre.coupon.output.rest.items.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.mercadolibre.coupon.domain.error.ItemRestClientUnknownError;
import com.mercadolibre.coupon.output.rest.items.client.ItemRestClient;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryRestCBTest {

  @Mock
  private ItemRestClient itemRestClient;

  @Mock
  private CircuitBreaker itemsCircuitBreaker;

  @InjectMocks
  private ItemRepositoryRestCB itemRepositoryRestCB;

  @Test
  void getItem() {
    final GetItemRequest getItemRequest = new GetItemRequest("MLA123");
    when(itemsCircuitBreaker.executeSupplier(any())).thenAnswer(
        invocation -> ((java.util.function.Supplier<?>) invocation.getArgument(0)).get());
    when(itemRestClient.getItem(any())).thenReturn(Optional.of(new ItemDto("MLA123", 1f)));

    final Optional<ItemDto> item = itemRepositoryRestCB.getItem(getItemRequest);

    assertTrue(item.isPresent());
  }

  @Test
  void getItemError() {
    final GetItemRequest getItemRequest = new GetItemRequest("MLA123");
    doThrow(new ItemRestClientUnknownError(new Exception())).when(itemRestClient).getItem(any());
    when(itemsCircuitBreaker.executeSupplier(any())).thenAnswer(
        invocation -> ((java.util.function.Supplier<?>) invocation.getArgument(0)).get());
    final Optional<ItemDto> item = itemRepositoryRestCB.getItem(getItemRequest);

    assertTrue(item.isEmpty());
  }
}