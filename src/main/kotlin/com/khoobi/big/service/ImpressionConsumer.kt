package com.khoobi.big.service

import com.khoobi.big.model.AdEvent
import com.khoobi.big.model.ImpressionEvent
import com.khoobi.big.repository.AdRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class ImpressionConsumer(@Autowired
                private val impressionService: ImpressionService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.impressionTopic}"], groupId = "impression")
    fun impressionListener(consumerRecord: ConsumerRecord<String, ImpressionEvent>, ack: Acknowledgment) {
        val impressionEvent = consumerRecord.value()
        logger.info("Message received {}", impressionEvent)
        impressionService.impressionEventHandler(impressionEvent, ack)
    }



}
