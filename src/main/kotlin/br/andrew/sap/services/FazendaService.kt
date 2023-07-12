package br.andrew.sap.services

import br.andrew.sap.model.Fazenda
import br.andrew.sap.model.envrioments.SapEnvrioment
import br.andrew.sap.services.abstracts.EntitiesService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class FazendaService(restTemplate: RestTemplate,
                     env: SapEnvrioment,
                     authService : AuthService) : EntitiesService<Fazenda>(env,restTemplate, authService) {

    override fun path(): String {
        return "/b1s/v1/AGRI_UDO_UNPF"
    }

}