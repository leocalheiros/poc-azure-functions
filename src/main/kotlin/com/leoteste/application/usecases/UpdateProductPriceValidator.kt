package com.leoteste.application.usecases

import com.leoteste.application.dto.UpdateProductPriceRequestDto

class UpdateProductPriceValidator {
    companion object {
        fun validate(dto: UpdateProductPriceRequestDto): Boolean {
            if (dto.price <= 0.01) {
                return false
            }

            if (dto.productCode.isBlank() && dto.ean.isBlank()) {
                return false
            }

            return true
        }
    }
}