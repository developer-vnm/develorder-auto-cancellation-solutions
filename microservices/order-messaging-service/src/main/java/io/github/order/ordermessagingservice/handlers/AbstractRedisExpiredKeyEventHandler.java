package io.github.order.ordermessagingservice.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

@Slf4j
public abstract class AbstractRedisExpiredKeyEventHandler implements RedisExpiredKeyEventHandler {

    @Override
    public void onMessage(final Message message) {

        if (!predicate(message)) {
            log.debug("[redis] skipping message={}", message.toString());
        }

        handle(message);
    }

    protected abstract boolean predicate(final Message message);

    protected abstract void handle(final Message message);
}