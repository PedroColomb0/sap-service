package br.andrew.sap.services

import br.andrew.sap.infrastructure.odata.*
import br.andrew.sap.model.bank.PaymentMethod
import br.andrew.sap.model.enums.Cancelled
import br.andrew.sap.model.envrioments.SapEnvrioment
import br.andrew.sap.model.sap.InternalReconciliations
import br.andrew.sap.model.sap.partner.*
import br.andrew.sap.services.abstracts.EntitiesService
import br.andrew.sap.services.abstracts.SqlQueriesService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.RequestEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat
import java.util.*

@Service
class InternalReconciliationsService(
    env: SapEnvrioment,
    restTemplate: RestTemplate,
     authService: AuthService) :
        EntitiesService<InternalReconciliations>(env, restTemplate, authService) {
    override fun path(): String {
        return "/b1s/v1/InternalReconciliations"
    }

    override fun get(filter: Filter, order: OrderBy, page: Pageable): OData {
        throw Exception("Metodo nao suportado")
    }
}