package com.leoteste

import com.fasterxml.jackson.databind.ObjectMapper
import com.leoteste.application.usecases.UpdateProductPriceHandler
import com.leoteste.application.usecases.UpdateRetailerCreditHandler
import com.leoteste.infra.mapper.UpdateProductPriceMapper
import com.leoteste.infra.mapper.UpdateRetailerCreditMapper
import com.leoteste.infra.services.ServiceBusSenderService
import com.microsoft.azure.functions.ExecutionContext
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger

class FunctionsHandler {

    private val serviceBusSenderService = ServiceBusSenderService()
    private val objectMapper = ObjectMapper()
    private val updateRetailerCreditMapper = UpdateRetailerCreditMapper()
    private val updateProductPriceMapper = UpdateProductPriceMapper()
    private val updateRetailerCreditHandler = UpdateRetailerCreditHandler(updateRetailerCreditMapper, serviceBusSenderService, objectMapper)
    private val updateProductPriceHandler = UpdateProductPriceHandler(updateProductPriceMapper, serviceBusSenderService, objectMapper)

    @FunctionName("poc-marketplace-update-retailer-credit")
    fun runUpdateRetailerCredit(
        @ServiceBusQueueTrigger(
            name = "message",
            queueName = "tmp-db1-coreupdateretailercredit",
            connection = "AzureWebJobsStorage"
        ) message: String,
        context: ExecutionContext
    ) {
        context.logger.info("Update retailer credit function has triggered")
        context.logger.info(message)

        updateRetailerCreditHandler.handle(message);
    }

    @FunctionName("poc-marketplace-update-product-price")
    fun runUpdatePrice(
        @ServiceBusQueueTrigger(
            name = "message",
            queueName = "tmp-db1-leonardo-corequeueupdateprice",
            connection = "AzureWebJobsStorage"
        ) message: String,
        context: ExecutionContext
    ) {
        context.logger.info("Update product price function has triggered")
        context.logger.info(message)

        updateProductPriceHandler.handle(message);
    }
}
