package com.sd.laborator

import io.micronaut.core.annotation.Introspected


@Introspected
data class EratosteneResponse(
    val message: String?,
    val primes: List<Int>?
)