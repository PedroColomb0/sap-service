package br.andrew.sap.model.documents

import br.andrew.sap.services.ItemsService
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Product(val itemCode : String, val quantity : String, var unitPrice : String, val usage : Int = 9){

    var accountCode : String? = null
    fun Duplicate(): Product {
        return Product(itemCode,quantity,unitPrice,usage).also {
            it.taxCode = taxCode
            it.accountCode = accountCode;
        }
    }

    fun aplicaBase(itemService: ItemsService) {
        this.U_preco_base = itemService.getPriceBase(this)
    }

    var LineNum : Int? = null
    var taxCode : String? = null
    var discountPercent : Double? = null
    var U_preco_base: Double? = null
    var U_preco_negociado: Double? = null

}