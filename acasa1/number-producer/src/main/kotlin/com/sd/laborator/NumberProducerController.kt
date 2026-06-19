package com.sd.laborator

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject
import org.slf4j.LoggerFactory

@Controller("/produce")
class NumberProducerController {

    private val LOG = LoggerFactory.getLogger(NumberProducerController::class.java)

    @Inject
    lateinit var fileReaderService: FileReaderService

    @Inject
    lateinit var producerClient: NumberProducerClient

    @Post
    @ExecuteOn(TaskExecutors.BLOCKING)  // ruleaza pe un thread blocking, nu pe Netty
    fun produce(): HttpResponse<String> {
        val numbers = fileReaderService.readNumbers()
        LOG.info("Numere citite din fisier: $numbers")

        producerClient.send(numbers)
        LOG.info("Numerele au fost publicate in coada 'numbers-queue'")

        return HttpResponse.ok("Publicate ${numbers.size} numere in coada: $numbers")
    }
}