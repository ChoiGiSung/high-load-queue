package com.coco.queue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QueueApplication

fun main(args: Array<String>) {
	runApplication<QueueApplication>(*args)
}
