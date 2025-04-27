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
        val waitingQueueKey = configProperties.redisProperties().waitingQueueKey
        val joiningQueueKey = configProperties.redisProperties().joiningQueueKey
        val userIdString = userId.toString()

        queueTemplateRank(waitingQueueKey, userIdString)?.let {
            return WaitingQueueResponse(QueueType.WAITING_QUEUE, userId, it)
        }

        queueTemplateRank(joiningQueueKey, userIdString)?.let {
            return WaitingQueueResponse(QueueType.JOINING_QUEUE, userId, it)
        }

        return WaitingQueueResponse(QueueType.UNKNOWN, userId, null)
    }

    private fun queueTemplateRank(key: String, userId: String): Long? {
        return waitingQueueTemplate.opsForZSet().rank(key, userId)
    }


    data class WaitingQueueJoinRequest(
        val userId: Long
    )

    data class WaitingQueueResponse(
        val type: QueueType,
        val userId: Long,
        val rank: Long? // 없으면 null
    )

}