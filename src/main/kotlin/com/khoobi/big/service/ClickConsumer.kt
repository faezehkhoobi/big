package com.khoobi.big.service

import com.khoobi.big.model.ClickEvent
import com.khoobi.big.model.dao.ClickAckMapping
import com.khoobi.big.repository.AdRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class ClickConsumer(@Autowired
                    private val clickService: ClickService) {
    private val logger = LoggerFactory.getLogger(javaClass)


    @KafkaListener(topics = ["\${kafka.topics.clickTopic}"], groupId = "click")
    fun clickListener(consumerRecord: ConsumerRecord<String, ClickEvent>, ack: Acknowledgment) {
        val clickEvent = consumerRecord.value()
        logger.info("click event received {}", clickEvent.toString())
        clickService.clickEventHandler(clickEvent, ack)
    }



}
