package com.mercadolibre.coupon.output.rest.items.client;

import java.net.URI;
import java.util.Optional;

import com.mercadolibre.coupon.domain.error.ItemRestClientUnknownError;
import com.mercadolibre.coupon.output.rest.items.model.GetItemRequest;
import com.mercadolibre.coupon.output.rest.items.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRestClient {

  private static final String ITEM_CACHE = "itemCache";

  private static final String ITEMS_URI = "items/";

  private static final String ITEM_ID_PATH_PARAM = "/{itemId}";

  private final RestClient itemsRestClient;

  /**
   * Retrieves an item's details based on the provided request and caches the result. This method attempts to fetch the item details from an
   * external service. If the request fails due to a client-side error, an empty value is returned. For other types of errors, an
   * ItemRestClientUnknownError exception is thrown.
   *
   * @param itemRequest The request object containing the itemId.
   * @return An Optional containing the ItemDto if the item is successfully retrieved, or an empty Optional if a client-side error occurs.
   * @throws ItemRestClientUnknownError if an unexpected error occurs while fetching the item's details.
   */
  @Cacheable(cacheNames = ITEM_CACHE)
  public Optional<ItemDto> getItem(final GetItemRequest itemRequest) {
    log.info("Get item for itemRequest: {}", itemRequest);
    try {
      return Optional.of(getItemDto(itemRequest));
    } catch (final RestClientException error) {
      log.error("Error getting item for itemRequest: {}. Error: {}", itemRequest, error.getMessage());
      if (this.isClientError(error)) {
        return Optional.empty();
      }
      throw new ItemRestClientUnknownError(error);
    }
  }

  private ItemDto getItemDto(final GetItemRequest itemRequest) {
    return itemsRestClient.get()
        .uri(buildItemUri(itemRequest.itemId()))
        .retrieve()
        .body(ItemDto.class);
  }

  private URI buildItemUri(final String itemId) {
    return UriComponentsBuilder.fromUriString(ITEMS_URI)
        .path(ITEM_ID_PATH_PARAM)
        .buildAndExpand(itemId)
        .toUri();
  }

  private boolean isClientError(final RestClientException error) {
    return error instanceof final HttpClientErrorException httpClientErrorException && httpClientErrorException.getStatusCode()
        .is4xxClientError();
  }

}
