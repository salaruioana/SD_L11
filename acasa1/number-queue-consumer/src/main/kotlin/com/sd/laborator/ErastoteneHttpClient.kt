package com.sd.laborator

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

// Client HTTP declarativ Micronaut
// Micronaut genereaza automat implementarea la compilare
@Client("\${eratostene.url}")           // URL citit din application.yml
interface EratosteneHttpClient {

    @Post("/", processes = [MediaType.APPLICATION_JSON])
    fun checkPrimes(@Body request: EratosteneRequest): EratosteneResponse
}