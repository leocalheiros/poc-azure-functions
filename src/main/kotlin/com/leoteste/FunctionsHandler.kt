package com.leoteste

import com.fasterxml.jackson.databind.ObjectMapper
import com.leoteste.application.usecases.UpdateRetailerCreditHandler
import com.leoteste.application.usecases.UpdateRetailerCreditValidator
import com.leoteste.infra.mapper.UpdateRetailerCreditMapper
import com.leoteste.infra.services.ServiceBusSenderService
import com.microsoft.azure.functions.ExecutionContext
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger

class FunctionsHandler {

    private val serviceBusSenderService = ServiceBusSenderService()
    private val updateRetailerCreditMapper = UpdateRetailerCreditMapper()
    private val objectMapper = ObjectMapper()
    private val handler = UpdateRetailerCreditHandler(updateRetailerCreditMapper, serviceBusSenderService, objectMapper)

    @FunctionName("ServiceBusQueueTrigger")
    fun run(
        @ServiceBusQueueTrigger(
            name = "message",
            queueName = "nome_da_Fila",
            connection = "AzureWebJobsStorage"
        ) message: String?,
        context: ExecutionContext
    ) {
        context.logger.info("Kotlin Service Bus Queue trigger function executed.")
        context.logger.info(message)

        message?.let {
            handler.handle(it)
        } ?: context.logger.warning("Received null message")
    }
}
