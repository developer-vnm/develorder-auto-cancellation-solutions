# Order Auto Cancellation Solutions

## Redis Messaging

### Producer

- Usage:

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCancellationServiceImpl implements OrderCancellationService {

    private final CacheManager cacheManager;

    @Override
    @Async
    public void storeScheduledOrderCancellation(final long orderId) {
        log.info("[application] prepare to store scheduled order cancellation order_id={}", orderId);
        Optional.ofNullable(cacheManager.getCache("ORDER_EXPIRATION"))
                .ifPresent(cache -> cache.put(orderId, ""));
    }
}
```

- Illustration logs:

```
2024-11-13T13:45:39.372+07:00  INFO [order-service,,] 17228 --- [order-service] [omcat-handler-0] i.g.o.o.r.OrderRestController            : [server-request] create order order_request=OrderRequestDTO[productId=1]
2024-11-13T13:45:39.375+07:00  INFO [order-service,,] 17228 --- [order-service] [omcat-handler-0] i.g.o.o.r.OrderRestController            : [server-response] created order order_response=OrderResponseDTO[orderId=1731480339373]
2024-11-13T13:45:39.376+07:00  INFO [order-service,,] 17228 --- [order-service] [         task-1] i.g.o.o.s.i.OrderCancellationServiceImpl : [application] prepare to store scheduled order cancellation order_id=1731480339373
```

### Consumer

- Configuration:

```java
@Configuration
public class RedisMessageListenerConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(final RedisConnectionFactory redisConnectionFactory) {

        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // handle expired key events
        container.addMessageListener(
                new RedisExpiredKeyEventListener(),
                new PatternTopic("__keyevent@*__:expired"));

        return container;
    }
}
```

- Usage:

```java
@Slf4j
public class RedisExpiredKeyEventListener implements MessageListener {

    @Override
    public void onMessage(final Message message, final byte[] pattern) {

        var key = new String(message.getBody());

        if (key.startsWith("ORDER_EXPIRATION")) {

            var orderId = key.split("::")[1];

            // verify and update database
            log.info("[redis] expired order order_id={}", orderId);
        }
    }
}
```

- Illustration logs:

```
2024-11-13T13:45:49.595+07:00  INFO [order-messaging-service,,] 17252 --- [order-messaging-service] [enerContainer-1] i.g.o.o.l.RedisExpiredKeyEventListener   : [redis] expired order order_id=1731480339373
```