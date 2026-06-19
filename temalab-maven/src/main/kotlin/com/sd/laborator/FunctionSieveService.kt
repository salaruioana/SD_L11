package com.sd.laborator

import jakarta.inject.Singleton

@Singleton
class FunctionSieveService {

    private fun computeRecursive(n: Int, terms: MutableList<Double>) {
        if (n == 0) {
            terms.add(1.0) // a0 = 1
            return
        }
        computeRecursive(n - 1, terms)
        val prev = terms[n - 1]
        terms.add(prev + 2.0 * prev / n) // an = an-1 + 2*an-1/n
    }

    fun computeTerms(n: Int): List<Double> {
        val terms = mutableListOf<Double>()
        computeRecursive(n, terms)
        return terms
    }
}