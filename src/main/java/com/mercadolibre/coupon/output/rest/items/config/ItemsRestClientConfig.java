package com.mercadolibre.coupon.output.rest.items.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Getter
public class ItemsRestClientConfig {

  private final String defaultItemsApiBaseUrl;

  private final int defaultItemsConnectTimeout;

  public ItemsRestClientConfig(@Value("${rest.items.base-url:null}") final String defaultItemsApiBaseUrl,
      @Value("${rest.items.connect-timeout:100}") final int defaultItemsConnectTimeout) {
    this.defaultItemsApiBaseUrl = defaultItemsApiBaseUrl;
    this.defaultItemsConnectTimeout = defaultItemsConnectTimeout;
  }

  @Bean
  public RestClient itemsRestClient() {
    return RestClient.builder().baseUrl(defaultItemsApiBaseUrl).requestFactory(getClientHttpRequestFactory()).build();
  }

  private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(defaultItemsConnectTimeout);
    return clientHttpRequestFactory;
  }
}
