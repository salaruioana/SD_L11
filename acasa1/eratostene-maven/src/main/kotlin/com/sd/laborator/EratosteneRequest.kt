package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class EratosteneRequest {
    private lateinit var number: Integer
    //cererea primeste in plus o lista de numere care vor fi verificate
    private var list: List<Int> = emptyList()

    fun getNumber(): Int {
        return number.toInt()
    }
    fun getList():List<Int>{
        return list
    }
}