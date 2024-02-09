package com.khoobi.big.service

import com.khoobi.big.model.AdEvent
import com.khoobi.big.model.ImpressionEvent
import com.khoobi.big.repository.AdRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
class ImpressionService(@Autowired
                        private val adRepository: AdRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)
    fun impressionEventHandler(impressionEvent: ImpressionEvent, ack: Acknowledgment) {
        val adEvent = AdEvent(
                impressionEvent.requestId, impressionEvent.adId, impressionEvent.adTitle, impressionEvent.advertiserCost, impressionEvent.appId, impressionEvent.appTitle, impressionEvent.impressionTime, 0)
        adRepository.save(adEvent)
        ack.acknowledge()
        logger.info("impression event updated {}", impressionEvent.toString())
    }

}
