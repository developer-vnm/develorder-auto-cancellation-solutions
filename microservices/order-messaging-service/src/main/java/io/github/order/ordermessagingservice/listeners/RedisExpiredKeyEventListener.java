package io.github.order.ordermessagingservice.listeners;

import io.github.order.ordermessagingservice.handlers.RedisExpiredKeyEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisExpiredKeyEventListener implements MessageListener {

    private final List<RedisExpiredKeyEventHandler> redisExpiredKeyEventHandlers;

    @Override
    @SuppressWarnings("all")
    public void onMessage(final Message message, final byte[] pattern) {
        redisExpiredKeyEventHandlers.forEach(handler -> handler.onMessage(message));
    }
}