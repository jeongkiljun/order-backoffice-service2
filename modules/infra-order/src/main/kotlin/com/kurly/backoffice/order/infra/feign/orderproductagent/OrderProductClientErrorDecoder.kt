package com.kurly.backoffice.order.infra.feign.orderproductagent

import com.kurly.cloud.api.common.util.logging.FileBeatLogger
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class OrderProductErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String?, response: Response?): Exception {

        logger.error {
            "$methodKey 요청이 성공하지 못했습니다. status : ${response!!.status()}, body : ${response.body()}"
        }

        FileBeatLogger.info(
            mapOf(
                "action" to "상품 정보 조회",
                "http status code" to response!!.status(),
                "error message" to response.body().toString()
            )
        )

        if (response.status() == 404)
            return OrderProductClientException("상품 정보가 존재하지 않습니다.")

        return OrderProductClientException("주문 상품 Agent API에 장애가 발생하였습니다.")
    }
}
