﻿package com.leoteste.application.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.leoteste.application.dto.UpdateRetailerCreditRequestDto
import com.leoteste.infra.mapper.UpdateRetailerCreditMapper
import com.leoteste.infra.services.ServiceBusSenderService
import kotlinx.coroutines.runBlocking

class UpdateRetailerCreditHandler(
    private val updateRetailerCreditMapper: UpdateRetailerCreditMapper,
    private val serviceBusSenderService: ServiceBusSenderService,
    private val objectMapper: ObjectMapper,
    private val validator: UpdateRetailerCreditValidator
) {

    fun handle(messageContent: String) = runBlocking {
        try {
            val dto: UpdateRetailerCreditRequestDto = objectMapper.readValue(messageContent)
            val validationResult = validator.validateRequest().validate(dto);

            if(!validationResult.isValid){
                println("Validation failed");
                return@runBlocking;
            }

            val (documentNumber, updateDto) = updateRetailerCreditMapper.map(dto)
            serviceBusSenderService.sendMessage("tmp-db1-leonardo-marketplaceupdateprice", documentNumber, updateDto)
        } catch (e: Exception) {
            println("Erro de desserialização: ${e.message}")
        }
    }
}