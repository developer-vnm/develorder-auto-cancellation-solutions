package io.github.order.ordermessagingservice.services.impl;

import io.github.order.ordermessagingservice.constants.DistributedCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderExpirationMessagingServiceImpl implements MessageListener {

    @Override
    public void onMessage(final Message message, final byte[] pattern) {

        var channel = new String(message.getChannel());

        if (!channel.startsWith(DistributedCacheConstants.REDIS_KEY_EVENT_CHANNEL_PREFIX)
                || !channel.endsWith(DistributedCacheConstants.REDIS_EXPIRED_KEY_EVENT_CHANNEL_SUFFIX)) {
            log.info("[redis] ignoring message listener channel={}", channel);
            return;
        }

        var key = new String(message.getBody());

        if (!key.startsWith(DistributedCacheConstants.ORDER_EXPIRATION_CACHE)) {
            log.info("[redis] ignoring message listener key={}", key);
            return;
        }

        var orderId = key.split("::")[1];

        log.info("[redis] expired order order_id={}", orderId);
    }
}