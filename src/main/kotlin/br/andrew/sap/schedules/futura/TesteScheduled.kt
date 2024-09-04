package br.andrew.sap.schedules.futura

import br.andrew.sap.infrastructure.odata.Condicao
import br.andrew.sap.infrastructure.odata.Filter
import br.andrew.sap.infrastructure.odata.Parameter
import br.andrew.sap.model.envrioments.SapEnvrioment
import br.andrew.sap.model.payment.HandlePaymentTermsLines
import br.andrew.sap.model.sap.DocEntry
import br.andrew.sap.model.sap.documents.base.Document
import br.andrew.sap.model.self.vendafutura.ContratoParse
import br.andrew.sap.services.AuthService
import br.andrew.sap.services.ContratoVendaFuturaService
import br.andrew.sap.services.abstracts.SqlQueriesService
import br.andrew.sap.services.bank.PaymentTermsTypesService
import br.andrew.sap.services.document.DownPaymentService
import br.andrew.sap.services.document.OrdersService
import br.andrew.sap.services.invent.BankPlusService
import br.andrew.sap.services.invent.OrigemBoletoEnum
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
@Profile("!test")
@ConditionalOnProperty(value = ["venda-futura.utilizacao"], matchIfMissing = false)
class TesteScheduled(
    protected val authService: AuthService,
    val sqlQueriesService: SqlQueriesService,
    val contratoService : ContratoVendaFuturaService,
    val paymentService : PaymentTermsTypesService,
    val adiantamentoService : DownPaymentService,
    val bankplus : BankPlusService,
    val orderService : OrdersService,
    @Value("\${venda-futura.utilizacao}") val idUtilizacao : Long,
    protected val env: SapEnvrioment) {

    val logger: Logger = LoggerFactory.getLogger(TesteScheduled::class.java)


    @Scheduled(fixedDelay = 3000)
    fun execute() {

    }
}