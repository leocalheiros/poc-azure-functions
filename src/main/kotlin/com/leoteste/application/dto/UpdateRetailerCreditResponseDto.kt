package com.leoteste.application.dto

data class UpdateRetailerCreditResponseDto(
    val requestParams: List<RequestParamsItems>,
    val informacoesAdicionais: List<InformacoesAdicionaisItems>
)

data class InformacoesAdicionaisItems(
    val valor: Double
)

data class RequestParamsItems(
    val retailerDocumentNumber: String
)
