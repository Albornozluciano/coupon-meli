package com.mercadolibre.coupon.domain.entity.coupon;

import java.util.List;

public record Coupon(List<String> itemIds, float amount) {
}
