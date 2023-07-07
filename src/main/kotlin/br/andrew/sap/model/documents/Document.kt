package br.andrew.sap.model.documents

import br.andrew.sap.model.Cancelled
import br.andrew.sap.model.WarehouseDefault
import br.andrew.sap.model.uzzipay.RequestQrCode
import br.andrew.sap.services.ItemsService
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Document(val CardCode : String,
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYY-MM-dd", timezone = "UTC")
                    val DocDueDate : String?,
                    val DocumentLines : List<DocumentLines>,
                    private val BPL_IDAssignedToInvoice : String) {

    var comments: String? = null
    var docDate :String? = null
    var salesPersonCode: Int = -1
    var paymentGroupCode: String? = null
    var docEntry : Int? = null
    var docNum : String? = null
    var paymentMethod : String? = null
    var discountPercent : Double = 0.0
    var documentInstallments : List<Installment>? = null
    var journalMemo : String? = null
    var Cancelled : Cancelled? = null
    var u_pedido_update : String? = "0"

    @JsonProperty("U_id_pedido_forca")
    var u_id_pedido_forca: String? = null
    var cardName: String? = null
    var OpeningRemarks: String? = null
    var controlAccount: String? = null
    var model : Int? = null
    var docType: String? = null
    var docObjectCode : String? = null


    var documentAdditionalExpenses : List<AdditionalExpenses> = emptyList()
    var frete: Double? = null

    @JsonProperty("BPL_IDAssignedToInvoice")
    fun getBPL_IDAssignedToInvoice(): String {
        return BPL_IDAssignedToInvoice
    }

    fun productsByTax(): Map<String, List<Product>> {
        this.DocumentLines
            .filter { it is Product && it.TaxCode != null && it.TaxCode!!.isNotEmpty() }

        return this.DocumentLines
                .filter { it is Product && it.TaxCode != null && it.TaxCode!!.isNotEmpty() }
                .groupBy { if(it is Product) it.TaxCode!! } as Map<String, List<Product>>
    }

    fun aplicaBase(itemService: ItemsService){
        this.DocumentLines.forEach { it.aplicaBase(itemService) }
    }

    fun usaBrenchDefaultWarehouse(branchs : List<WarehouseDefault>){
        branchs.firstOrNull{ it.BPLID == BPL_IDAssignedToInvoice }
                ?.also { usaBrenchDefaultWarehouse(it) }

    }
    fun setDistribuicaoCusto(distCusto : List<DistribuicaoCustoByBranch>){





        distCusto.firstOrNull{it.branch == BPL_IDAssignedToInvoice}?.also { branch ->
            this.DocumentLines.forEach{
                it.setDistribuicaoCusto( branch)
            }
        }

    }
    fun usaBrenchDefaultWarehouse(default : WarehouseDefault){
        if(default.defaultWarehouseID != null)
            DocumentLines
                    .filter { it.WarehouseCode == null}
                    .forEach { it.WarehouseCode = default.defaultWarehouseID}
    }

    fun isAvista(): Boolean {
        return paymentGroupCode == "-1"
    }

    fun isCalculaDesonaerado(): Boolean {
        return u_pedido_update == "1"
    }

    fun total() : Double {
        return DocumentLines.sumOf { it.total() }.plus(totalDespesaAdicional())
    }

    fun totalNegociado() : Double {
        return DocumentLines.sumOf { it.totalNegociado() }
    }

    fun totalDespesaAdicional(): Double {
        return documentAdditionalExpenses.sumOf { it.lineTotalSys }
    }

    fun presumeDesonerado(rate : Double) : Double {
        return DocumentLines.sumOf { it.presumeDesonerado(rate) }
    }

    override fun toString(): String {
        return "Document(CardCode='$CardCode', Branch='$BPL_IDAssignedToInvoice', docEntry=$docEntry, docNum=$docNum, pedido_forca=$u_id_pedido_forca)"
    }

    fun setPix(request: RequestQrCode, chave: String) {
        if(request.docEntry() != docEntry)
            throw Exception("O qrCode nao pertence a esse documento")
        this.documentInstallments!!.find { it.InstallmentId == request.getInstallmentId() }?.also {
            it.U_QrCodePix = chave
        }
    }


}

