package com.kurly.backoffice.order.infra.feign.orderproductagent

class OrderProductClientException : RuntimeException {
    constructor(message: String?) : super(message)
}
