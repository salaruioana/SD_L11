package com.sd.laborator

import io.micronaut.core.annotation.Introspected


@Introspected
data class EratosteneRequest(
    val number: Int,
    val list: List<Int>
)