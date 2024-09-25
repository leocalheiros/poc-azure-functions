package com.leoteste.infra.mapper

import com.leoteste.application.dto.InformacoesAdicionaisItems
import com.leoteste.application.dto.RequestParamsItems
import com.leoteste.application.dto.UpdateRetailerCreditRequestDto
import com.leoteste.application.dto.UpdateRetailerCreditResponseDto

class UpdateRetailerCreditMapper {
    fun map(requestDto: UpdateRetailerCreditRequestDto): Pair<String, UpdateRetailerCreditResponseDto> {
        return Pair(requestDto.documentNumber, UpdateRetailerCreditResponseDto(
            requestParams = listOf(RequestParamsItems(retailerDocumentNumber = requestDto.retailerDocumentNumber)),
            informacoesAdicionais = listOf(InformacoesAdicionaisItems(valor = requestDto.creditValue))
        ))
    }
}