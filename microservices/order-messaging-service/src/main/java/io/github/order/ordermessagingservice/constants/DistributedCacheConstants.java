package io.github.order.ordermessagingservice.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistributedCacheConstants {

    public static final String ORDER_EXPIRATION_CACHE = "ORDER_EXPIRATION";

    public static final String REDIS_EXPIRE_KEY_CHANNEL_PATTERN = "__keyevent@*__:expired";
}