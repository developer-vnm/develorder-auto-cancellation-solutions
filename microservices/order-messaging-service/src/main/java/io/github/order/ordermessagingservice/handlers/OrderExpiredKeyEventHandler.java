package io.github.order.ordermessagingservice.handlers;

import io.github.order.ordermessagingservice.constants.DistributedCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderExpiredKeyEventHandler extends AbstractRedisExpiredKeyEventHandler {

    @Override
    protected boolean predicate(final Message message) {
        var key = new String(message.getBody());
        return key.startsWith(DistributedCacheConstants.ORDER_EXPIRATION_CACHE);
    }

    @Override
    protected void handle(final Message message) {

        var key = new String(message.getBody());

        var orderId = key.split("::")[1];

        // update database
        log.info("[redis] expired order order_id={}", orderId);
    }
}