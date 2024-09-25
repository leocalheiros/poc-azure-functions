package com.leoteste.application.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.leoteste.application.dto.UpdateProductPriceRequestDto
import com.leoteste.infra.mapper.UpdateProductPriceMapper
import com.leoteste.infra.services.ServiceBusSenderService
import kotlinx.coroutines.runBlocking

class UpdateProductPriceHandler(private val updateProductPriceMapper: UpdateProductPriceMapper,
                                private val serviceBusSenderService: ServiceBusSenderService,
                                private val objectMapper: ObjectMapper,
) {

    fun handle(messageContent: String) = runBlocking {
        try {
            val dto: UpdateProductPriceRequestDto = objectMapper.readValue(messageContent)
            val isValidResult = UpdateProductPriceValidator.validate(dto);

            if(!isValidResult){
                println("Validation failed");
                return@runBlocking;
            }

            val (documentNumber, updateDto) = updateProductPriceMapper.map(dto)
            serviceBusSenderService.sendMessage("tmp-db1-leonardo-marketplaceupdateprice", documentNumber, updateDto)
        } catch (e: Exception) {
            println("Erro de desserialização: ${e.message}")
        }
    }
}