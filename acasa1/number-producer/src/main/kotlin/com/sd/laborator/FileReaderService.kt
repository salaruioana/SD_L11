package com.sd.laborator

import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.io.File

@Singleton
class FileReaderService {

    private val LOG = LoggerFactory.getLogger(FileReaderService::class.java)

    // Citeste fisierul numbers.txt din resources si returneaza lista de intregi
    fun readNumbers(): List<Int> {
        // getResourceAsStream cauta fisierul in classpath (resources/)
        val stream = javaClass.classLoader.getResourceAsStream("numbers.txt")
            ?: throw IllegalStateException("Fisierul numbers.txt nu a fost gasit in resources!")

        val content = stream.bufferedReader().readText().trim()
        LOG.info("Continut fisier citit: $content")

        // Imparte string-ul dupa virgula, trimite spatiile, filtreaza liniile goale
        return content
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
    }
}