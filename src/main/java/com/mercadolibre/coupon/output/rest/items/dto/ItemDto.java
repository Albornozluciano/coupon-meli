package com.mercadolibre.coupon.output.rest.items.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemDto(@JsonProperty("id") String id, @JsonProperty("price") Float price) implements Serializable {

}
