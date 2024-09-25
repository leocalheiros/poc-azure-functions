package com.leoteste.infra.mapper

import com.leoteste.application.dto.*

class UpdateProductPriceMapper {
    fun map(requestDto: UpdateProductPriceRequestDto): Pair<String, UpdateProductPriceResponseDto> {
        val responseDto = UpdateProductPriceResponseDto(
            sku = requestDto.productCode,
            precoDe = requestDto.price,
            precoPor = requestDto.price
        )

        return Pair(requestDto.documentNumber, responseDto)
    }
}