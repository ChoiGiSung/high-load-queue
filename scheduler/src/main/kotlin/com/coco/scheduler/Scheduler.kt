package com.coco.scheduler

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val configProperties: ConfigProperties,
    private val queueTemplate: RedisTemplate<String, String>
) {
    private val joiningQueueSize = 10


    @Scheduled(fixedDelay = 5000)
    fun schedule() {
        val joiningQueueKey = configProperties.redisProperties().joiningQueueKey
        val joiningSize = queueTemplate.opsForZSet().size(joiningQueueKey) ?: 0
        if (joiningSize >= joiningQueueSize) {
            return
        }

        val waitingQueueKey = configProperties.redisProperties().waitingQueueKey
        val nextUserId = queueTemplate.opsForZSet().range(waitingQueueKey, 0, 0)?.firstOrNull() ?: return
        queueTemplate.opsForZSet().remove(waitingQueueKey, nextUserId)
        queueTemplate.opsForZSet().add(joiningQueueKey, nextUserId, System.currentTimeMillis().toDouble())
    }
}