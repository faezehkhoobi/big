package com.khoobi.big.service

import com.khoobi.big.model.AdEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class Consumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.ad}"], groupId = "ad")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<String, AdEvent>, ack: Acknowledgment) {
        logger.info("Message received {}", consumerRecord.value())
        ack.acknowledge()
    }
}
