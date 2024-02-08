package com.khoobi.big

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BigApplication

fun main(args: Array<String>) {
	runApplication<BigApplication>(*args)
}
