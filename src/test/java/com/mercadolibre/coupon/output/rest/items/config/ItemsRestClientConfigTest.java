package com.mercadolibre.coupon.output.rest.items.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class ItemsRestClientConfigTest {

  @Test
  void testCreateRestClient() {
    final ItemsRestClientConfig config = new ItemsRestClientConfig("http://base", 100);

    final RestClient restClient = config.itemsRestClient();

    assertNotNull(restClient);
  }

}