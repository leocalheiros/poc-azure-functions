package com.leoteste.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProductPriceRequestDto @JsonCreator constructor(
    @JsonProperty("document_number") val documentNumber: String,
    @JsonProperty("ean") val ean: String,
    @JsonProperty("product_code") val productCode: String,
    @JsonProperty("price") val price: Double
)