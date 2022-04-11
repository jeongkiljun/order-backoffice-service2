package com.kurly.backoffice.order.infra.feign.orderproductagent.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PartnerDealProductsRequestV1(
    @field:JsonProperty(value = "memberNo")
    val memberNo: Long?,
    @field:JsonProperty(value = "dealProducts", required = true)
    val dealProducts: List<DealProductCodeAndQuantityRequest>,
    @field:JsonProperty(value = "address")
    val address: AddressRequest?
) {
    data class DealProductCodeAndQuantityRequest(
        @field:JsonProperty(value = "dealProductNo") val dealProductNo: Long,
        @field:JsonProperty(value = "quantity") val quantity: Int = 1
    )

    data class AddressRequest(
        @field:JsonProperty(value = "primaryAddress") val primaryAddress: String?,
        @field:JsonProperty(value = "secondaryAddress") val secondaryAddress: String?
    )
}
