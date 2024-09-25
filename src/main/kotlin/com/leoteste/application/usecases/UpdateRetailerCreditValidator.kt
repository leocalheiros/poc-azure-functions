package com.leoteste.application.usecases

import com.leoteste.application.dto.UpdateRetailerCreditRequestDto
import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum

class UpdateRetailerCreditValidator {
    companion object {
        fun validate(dto: UpdateRetailerCreditRequestDto): ValidationResult<UpdateRetailerCreditRequestDto> {
            var validation = Validation {
                UpdateRetailerCreditRequestDto::documentNumber{
                    minLength(1)
                }
                UpdateRetailerCreditRequestDto::retailerDocumentNumber{
                    minLength(1)
                }
                UpdateRetailerCreditRequestDto::creditValue{
                    minimum(1)
                }
            }

            return validation.validate(dto);
        }
    }
}