package com.khoobi.big.model

import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.Table

@Table("ad_event")
class AdEvent(
        @Id
        @field:Column(("request_id"))
        val requestId: String,
        @Column("ad_id")
        val adId: String,
        @Column("ad_title")
        val adTitle: String,
        @Column("advertiser_cost")
        val advertiserCost: Double,
        @Column("app_id")
        val appId: String,
        @Column("app_title")
        val appTitle: String,
        @Column("impression_time")
        val impressionTime: Long,
        @Column("click_time")
        var clickTime: Long)
