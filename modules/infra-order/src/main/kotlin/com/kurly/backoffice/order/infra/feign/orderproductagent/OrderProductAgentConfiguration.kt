package com.kurly.backoffice.order.infra.feign.orderproductagent

import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class OrderProductAgentConfiguration {
    @Bean
    fun decoder(): ErrorDecoder = OrderProductErrorDecoder()
}
