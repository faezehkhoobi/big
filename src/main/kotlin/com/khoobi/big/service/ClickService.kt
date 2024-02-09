package com.khoobi.big.service

import com.khoobi.big.model.ClickEvent
import com.khoobi.big.model.dao.ClickAckMapping
import com.khoobi.big.repository.AdRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.support.Acknowledgment
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ClickService(@Autowired
                   private val adRepository: AdRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)
    val clickPendingQueue = LinkedList<ClickAckMapping>()
    fun clickEventHandler(clickEvent: ClickEvent, ack: Acknowledgment) {
        val existed = adRepository.existsById(clickEvent.requestId)
        if (existed) {
            updateClick(clickEvent)
            ack.acknowledge()
        } else {
            clickPendingQueue.add(ClickAckMapping(clickEvent, ack, Instant.now()))
            logger.info("click event not found so added to pending list! {}", clickEvent.toString())
        }
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 4000)
    fun checkPendingList() {

        while (clickPendingQueue.isNotEmpty()) {
            val clickAckMapping = clickPendingQueue.peek()
            val existed = adRepository.existsById(clickAckMapping.clickEvent.requestId)
            if (existed) {
                updateClick(clickAckMapping.clickEvent)
                clickAckMapping.ack.acknowledge()
                clickPendingQueue.remove(clickAckMapping)
            } else if (clickAckMapping.insertTime.isBefore(Instant.now().plusSeconds(20))) {
                clickAckMapping.ack.acknowledge()
                clickPendingQueue.remove(clickAckMapping)
                logger.info("click event not found and expired so deleted! {}", clickAckMapping.clickEvent.toString())


            }
        }

    }

    fun updateClick(clickEvent: ClickEvent) {
        adRepository.update(clickEvent.clickTime, clickEvent.requestId)
        logger.info("click event inserted! {}", clickEvent.toString())

    }
}
