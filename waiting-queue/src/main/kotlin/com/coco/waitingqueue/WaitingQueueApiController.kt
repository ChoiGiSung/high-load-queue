package com.coco.waitingqueue

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/waiting-queue")
class WaitingQueueApiController(
    private val configProperties: ConfigProperties,
    private val waitingQueueTemplate: RedisTemplate<String, String>
) {

    @PostMapping
    fun joinWaitingQueue(
        @RequestBody waitingQueueJoinRequest: WaitingQueueJoinRequest
    ) {
        val now = Instant.now()
        val key = configProperties.redisProperties().waitingQueueKey
        val userId = waitingQueueJoinRequest.userId.toString()
        waitingQueueTemplate.opsForZSet().add(key, userId, now.toEpochMilli().toDouble())

    }

    @GetMapping("/rank")
    fun getWaitingQueueRank(
        @RequestParam userId: Long
    ): WaitingQueueResponse {

        val rank = waitingQueueTemplate.opsForZSet()
            .rank(configProperties.redisProperties().waitingQueueKey, userId.toString())
        return WaitingQueueResponse(userId, rank)
    }


    data class WaitingQueueJoinRequest(
        val userId: Long
    )

    data class WaitingQueueResponse(
        val userId: Long,
        val rank: Long? // 없으면 null
    )
}