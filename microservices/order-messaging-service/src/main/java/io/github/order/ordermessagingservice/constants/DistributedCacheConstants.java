package io.github.order.ordermessagingservice.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistributedCacheConstants {

    public static final String ORDER_EXPIRATION_CACHE = "ORDER_EXPIRATION";

    public static final String REDIS_KEY_EVENT_CHANNEL_PREFIX = "__keyevent";
    public static final String REDIS_EXPIRED_KEY_EVENT_CHANNEL_SUFFIX = "expired";
    public static final String REDIS_EXPIRE_KEY_CHANNEL_PATTERN = "__keyevent@*__:expired";
}