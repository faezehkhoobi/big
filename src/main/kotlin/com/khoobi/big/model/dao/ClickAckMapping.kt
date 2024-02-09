package com.khoobi.big.model.dao

import com.khoobi.big.model.ClickEvent
import org.springframework.kafka.support.Acknowledgment
import java.time.Instant

class ClickAckMapping(
        val clickEvent: ClickEvent,
        val ack: Acknowledgment,
        val insertTime: Instant)

