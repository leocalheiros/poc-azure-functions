package com.leoteste.infra.services

import com.azure.messaging.servicebus.ServiceBusClientBuilder
import com.azure.messaging.servicebus.ServiceBusMessage
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ServiceBusSenderService {

    fun <T> sendMessage(queueName: String, documentNumber: String, message: T) {

        val connectionString = System.getenv("AzureWebJobsStorage")

        val serviceBusClient = ServiceBusClientBuilder()
            .connectionString(connectionString)
            .sender()
            .queueName(queueName)
            .buildClient()

        val objectMapper = jacksonObjectMapper()

        val busMessage = ServiceBusMessage(objectMapper.writeValueAsString(message)).apply {
            to = documentNumber
        }

        serviceBusClient.sendMessage(busMessage)
        serviceBusClient.close()
    }
}