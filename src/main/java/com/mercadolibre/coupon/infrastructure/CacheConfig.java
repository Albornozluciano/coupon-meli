package com.mercadolibre.coupon.infrastructure;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {

  private static final String ITEM_CACHE = "itemCache";

  @Value("${cache.default.entry.ttl:60}")
  private int defaultCacheEntryTtl;

  @Value("${cache.items.entry.ttl:10}")
  private int defaultItemsCacheEntryTtl;

  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMillis(defaultCacheEntryTtl))
        .disableCachingNullValues()
        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return builder -> builder
        .withCacheConfiguration(ITEM_CACHE,
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMillis(defaultItemsCacheEntryTtl)));
  }
}
