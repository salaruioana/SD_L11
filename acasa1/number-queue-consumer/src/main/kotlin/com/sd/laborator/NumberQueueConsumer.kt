package com.sd.laborator

import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import jakarta.inject.Inject
import org.slf4j.LoggerFactory

@RabbitListener
class NumberQueueConsumer {

    private val LOG = LoggerFactory.getLogger(NumberQueueConsumer::class.java)

    @Inject
    lateinit var eratosteneHttpClient: EratosteneHttpClient

    // Aceasta metoda este apelata automat de Micronaut
    // cand soseste un mesaj in coada "numbers-queue"
    @Queue("numbers-queue")
    fun receive(numbers: List<Int>) {
        LOG.info("Numere primite din coada: $numbers")

        if (numbers.isEmpty()) {
            LOG.warn("Lista primita este goala, se ignora mesajul.")
            return
        }

        // Limita superioara = maximul din lista + 1 (siguranta)
        // Functia eratostene va calcula ciurul pana la aceasta valoare
        val maxNumber = (numbers.maxOrNull() ?: 0) + 1

        val request = EratosteneRequest(
            number = maxNumber,
            list = numbers
        )

        LOG.info("Se trimite cerere catre eratostene: number=$maxNumber, list=$numbers")

        val response = eratosteneHttpClient.checkPrimes(request)

        LOG.info("Raspuns primit de la eratostene: ${response.message}")
        LOG.info("Numere prime din lista: ${response.primes}")
    }
}