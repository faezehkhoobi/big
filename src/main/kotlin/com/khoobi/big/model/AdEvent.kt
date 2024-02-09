package com.khoobi.big.model

import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.Table

@Table("ad_event")
class AdEvent(
        @Id
        val requestId: String,
        val adId: String,
        val adTitle: String,
        val advertiserCost: Double,
        val appId: String,
        val appTitle: String,
        val impressionTime: Long,
        var clickTime: Long)
