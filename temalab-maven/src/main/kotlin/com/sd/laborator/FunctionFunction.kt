package com.sd.laborator

import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Function

@FunctionBean("function")
class FunctionFunction : FunctionInitializer(), Function<FunctionRequest, FunctionResponse> {

    @Inject
    private lateinit var functionSieveService: FunctionSieveService

    private val LOG: Logger = LoggerFactory.getLogger(FunctionFunction::class.java)

    override fun apply(msg: FunctionRequest): FunctionResponse {
        val number = msg.getNumber()
        val response = FunctionResponse()

        if (number < 0) {
            LOG.error("Parametru negativ: $number")
            response.setMessage("Se accepta doar parametri >= 0")
            return response
        }

        LOG.info("Se calculeaza termenii pentru n=$number ...")
        response.setTerms(functionSieveService.computeTerms(number))
        response.setMessage("Calcul efectuat cu succes!")
        LOG.info("Calcul incheiat!")
        return response
    }
}

fun main(args: Array<String>) {
    val function = FunctionFunction()
    function.run(args, { context -> function.apply(context.get(FunctionRequest::class.java)) })
}