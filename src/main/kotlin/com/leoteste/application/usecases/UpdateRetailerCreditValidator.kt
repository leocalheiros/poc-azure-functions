package com.leoteste.application.usecases

import com.leoteste.application.dto.UpdateRetailerCreditRequestDto

class UpdateRetailerCreditValidator {
    companion object {
        fun validate(dto: UpdateRetailerCreditRequestDto): Boolean {
            if (dto.documentNumber.isBlank()) {
                println("Document number must not be empty.")
                return false
            }

            if (dto.retailerDocumentNumber.isBlank()) {
                println("Retailer document number must not be empty.")
                return false
            }

            if (dto.creditValue < 1) {
                println("Credit value must be at least 1.")
                return false
            }

            return true
        }
    }
}