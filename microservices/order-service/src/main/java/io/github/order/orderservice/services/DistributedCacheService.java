package io.github.order.orderservice.services;

public interface DistributedCacheService {

    void set(String name, String key, Object value);
}