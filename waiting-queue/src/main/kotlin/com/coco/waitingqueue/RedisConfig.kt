package com.coco.waitingqueue

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Instant

@Configuration
class RedisConfig {

//    @Bean
//    fun redisQueueTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, String> {
//        val template = RedisTemplate<String, String>()
//        template.connectionFactory = redisConnectionFactory
//        template.keySerializer = StringRedisSerializer()
//        template.valueSerializer = StringRedisSerializer()
//        return template
//    }
}