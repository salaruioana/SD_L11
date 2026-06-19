package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class FunctionResponse {
	private var message: String? = null
	private var terms: List<Double>? = null

	fun getTerms(): List<Double>? {
		return terms
	}

	fun setTerms(terms: List<Double>?) {
		this.terms = terms
	}

	fun getMessage(): String? {
		return message
	}

	fun setMessage(message: String?) {
		this.message = message
	}
}


