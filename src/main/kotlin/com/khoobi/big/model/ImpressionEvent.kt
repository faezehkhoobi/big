package com.khoobi.big.model

class ImpressionEvent(
        val requestId: String, // a unique id for the request
        val adId: String,
        val adTitle: String,
        val advertiserCost: Double,
        val appId: String,
        val appTitle: String,
        val impressionTime: Long)
