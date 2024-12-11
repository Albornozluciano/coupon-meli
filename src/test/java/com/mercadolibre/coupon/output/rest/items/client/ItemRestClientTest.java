package com.mercadolibre.coupon.output.rest.items.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Optional;

import com.mercadolibre.coupon.domain.error.ItemRestClientUnknownError;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

@ExtendWith(MockitoExtension.class)
class ItemRestClientTest {

  @Mock
  RestClient itemsRestClient;

  @InjectMocks
  ItemRestClient itemRestClient;

  @Test
  void getItemSuccessfully() {
    final RequestHeadersUriSpec requestHeadersUriSpec = mock(RestClient.RequestHeadersUriSpec.class);
    final ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
    when(itemsRestClient.get()).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.uri(any(URI.class))).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.body(ItemDto.class)).thenReturn(new ItemDto("MLA123", 1f));

    final Optional<ItemDto> itemDto = itemRestClient.getItem(new GetItemRequest("MLA123"));

    assertTrue(itemDto.isPresent());
    assertEquals("MLA123", itemDto.get().id());
    assertEquals(1f, itemDto.get().price());
  }

  @Test
  void getItemClientError() {
    when(itemsRestClient.get()).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(400)));

    final Optional<ItemDto> itemDto = itemRestClient.getItem(new GetItemRequest("MLA123"));

    assertTrue(itemDto.isEmpty());
  }

  @Test
  void getItemUnknownError() {
    when(itemsRestClient.get()).thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(500)));

    assertThrows(ItemRestClientUnknownError.class, () -> itemRestClient.getItem(new GetItemRequest("MLA123")));
  }
}