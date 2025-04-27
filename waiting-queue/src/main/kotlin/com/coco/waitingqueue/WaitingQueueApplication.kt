package com.coco.waitingqueue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WaitingQueueApplication

fun main(args: Array<String>) {
	runApplication<WaitingQueueApplication>(*args)
}
