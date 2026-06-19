package com.sd.laborator

import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient


@RabbitClient                           // marcheaza interfata ca publisher RabbitMQ
interface NumberProducerClient {

    @Binding("numbers-queue")           // numele cozii in care se publica
    fun send(numbers: List<Int>)        // trimite lista de intregi serialzata ca JSON
}