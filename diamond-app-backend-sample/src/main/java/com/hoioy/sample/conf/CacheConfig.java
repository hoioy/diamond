package com.hoioy.sample.conf;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hoioy.diamond.common.cache.BaseCacheKeyGenerator;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.common.util.CommonCaffeineUtil;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// 缓存配置
// Redis配置、Redis缓存配置、其他缓存配置
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Value("${spring.cache.redis.time-to-live:2H}")
    private Duration timeToLive;

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public BaseCacheKeyGenerator baseCacheKeyGenerator() {
        return new BaseCacheKeyGenerator();
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "caffeine")
    public CaffeineCacheManager caffeineCacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean
    // 在配置文件中查找 spring.cache.type 的值，如果能找到并且值等于redis就注入
    @ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.registerModule((new SimpleModule()));
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(om);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        return redisCacheManager;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    //监听Redis事件
    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new Receiver(new CountDownLatch(1)), "receiveMessage");
    }

    @Bean
    public CommonCacheUtil commonCacheUtil() {
        return new CommonCacheUtil();
    }

    @Bean
    public CommonRedisUtil commonRedisUtil() {
        return new CommonRedisUtil();
    }

    @Bean
    public CommonCaffeineUtil commonCaffeineUtil() {
        return new CommonCaffeineUtil();
    }

    public class Receiver {
        private CountDownLatch latch;

        public Receiver(CountDownLatch latch) {
            this.latch = latch;
        }

        public void receiveMessage(String message) {
            logger.debug(message);
            latch.countDown();
        }
    }
}
