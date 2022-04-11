package com.kurly.backoffice.order.infra.feign.orderproductagent

import com.kurly.backoffice.order.infra.feign.orderproductagent.model.PartnerDealProductsRequestV1
import com.kurly.backoffice.order.infra.feign.orderproductagent.model.PartnerDealProductsResponseV1
import com.kurly.cloud.api.common.domain.ApiResponseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    value = "order-product-agent",
    url = "\${clients.order-product-agent}",
    configuration = [OrderProductAgentConfiguration::class]
)
interface OrderProductAgentClient {

    @PostMapping("/api/v1/partner-products")
    fun getDiscountDealProducts(
        @RequestBody request: PartnerDealProductsRequestV1
    ): ApiResponseModel<PartnerDealProductsResponseV1>
}
