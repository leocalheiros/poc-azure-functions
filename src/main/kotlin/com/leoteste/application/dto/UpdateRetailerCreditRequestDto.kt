package com.leoteste.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRetailerCreditRequestDto @JsonCreator constructor(
    @JsonProperty("document_number") val documentNumber: String,
    @JsonProperty("document_number_branch") val documentNumberBranch: String,
    @JsonProperty("retailer_document_number") val retailerDocumentNumber: String,
    @JsonProperty("credit_value") val creditValue: Int
)