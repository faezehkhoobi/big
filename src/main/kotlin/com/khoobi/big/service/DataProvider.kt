package com.khoobi.big.service

import com.khoobi.big.model.ClickEvent
import com.khoobi.big.model.ImpressionEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.random.Random

@Service
class DataProvider(
        @Value("\${kafka.topics.impressionTopic}") val impressionTopic: String,
        @Value("\${kafka.topics.clickTopic}") val clickTopic: String,
        @Autowired
        private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(initialDelay = 2000, fixedDelay = 300000)
    fun provideData() {
        val now = Instant.now()
        val id = String() + now.epochSecond + Random.nextLong(1000, 9999);
        val impressionEvent = ImpressionEvent(id + "", id + Random.nextLong(10), "good Title " + Random.nextInt(1000, 9999), Random.nextDouble(), "app " + Random.nextLong(1000), "app title" + Random.nextLong(1000), now.toEpochMilli())
        log.info("provider send data of impression event {}", impressionEvent.toString())
        val impressionEventMessage: Message<ImpressionEvent> = MessageBuilder
                .withPayload(impressionEvent)
                .setHeader(KafkaHeaders.TOPIC, impressionTopic)
                .build()
        kafkaTemplate.send(impressionEventMessage)
//        if (Random.nextInt(3) < 2) {
            provideDataForClick(impressionEvent)
//        }
    }

    fun provideDataForClick(impressionEvent: ImpressionEvent) {
        val clickEvent = ClickEvent(impressionEvent.requestId, Instant.now().plusSeconds(Random.nextLong(9999)).toEpochMilli())
        log.info("provider send data of click event {}", clickEvent.toString())
        val clickEventMessage: Message<ClickEvent> = MessageBuilder
                .withPayload(clickEvent)
                .setHeader(KafkaHeaders.TOPIC, clickTopic)
                .build()
        kafkaTemplate.send(clickEventMessage)
    }
}
