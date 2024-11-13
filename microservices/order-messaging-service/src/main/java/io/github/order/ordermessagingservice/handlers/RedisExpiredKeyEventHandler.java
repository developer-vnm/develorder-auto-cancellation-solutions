package io.github.order.ordermessagingservice.handlers;

import org.springframework.data.redis.connection.Message;

public interface RedisExpiredKeyEventHandler {

    void onMessage(final Message message);
}