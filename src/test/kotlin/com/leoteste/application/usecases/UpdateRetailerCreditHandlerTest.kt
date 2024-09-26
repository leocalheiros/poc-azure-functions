package com.leoteste.application.usecases

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.leoteste.application.dto.InformacoesAdicionaisItems
import com.leoteste.application.dto.RequestParamsItems
import com.leoteste.application.dto.UpdateRetailerCreditRequestDto
import com.leoteste.application.dto.UpdateRetailerCreditResponseDto
import com.leoteste.infra.mapper.UpdateRetailerCreditMapper
import com.leoteste.infra.services.ServiceBusSenderService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.kotlin.*
import org.mockito.Mockito.*
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UpdateRetailerCreditHandlerTest {
    private val updateRetailerCreditMapper: UpdateRetailerCreditMapper = mock()
    private val serviceBusSenderService: ServiceBusSenderService = mock()
    private val objectMapper = ObjectMapper()
    private val handler = UpdateRetailerCreditHandler(updateRetailerCreditMapper, serviceBusSenderService, objectMapper)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    fun `should send message to service bus when dto is valid`(): Unit = runBlocking {
        // Given
        val messageContent = """{"document_number":"1234","document_number_branch":"1234","retailer_document_number":"1234","credit_value":1}"""
        val dto = UpdateRetailerCreditRequestDto(
            documentNumber = "1234",
            documentNumberBranch = "1234",
            retailerDocumentNumber = "1234",
            creditValue = 1
        )
        val mappedResult = Pair(
            dto.documentNumber,
            UpdateRetailerCreditResponseDto(
                requestParams = listOf(RequestParamsItems(retailerDocumentNumber = dto.retailerDocumentNumber)),
                informacoesAdicionais = listOf(
                    InformacoesAdicionaisItems(valor = dto.creditValue)
                )
            )
        )

        // Mocking
        `when`(updateRetailerCreditMapper.map(dto)).thenReturn(mappedResult)

        // When
        handler.handle(messageContent)

        // Then
        verify(serviceBusSenderService).sendMessage(
            "tmp-db1-leonardo-marketplacenotifyneworderqueue", dto.documentNumber,
            mappedResult.second
        )

    }
}