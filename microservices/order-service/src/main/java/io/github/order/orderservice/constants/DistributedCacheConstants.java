package io.github.order.orderservice.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistributedCacheConstants {

    public static final long DEFAULT_TTL = 300L;
    public static final long ORDER_EXPIRATION_TTL = 10L;

    public static final String ORDER_EXPIRATION_CACHE = "ORDER_EXPIRATION";
}