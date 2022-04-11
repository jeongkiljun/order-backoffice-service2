package com.kurly.backoffice.order.domain.message

import java.util.concurrent.Future

interface SlackPort {

    /**
     * 설정으로 주입된 기본 채널에 메시지를 발송합니다.
     *
     * @param message 보낼 메시지
     *
     * @return 메시지 발송 결과
     */
    fun sendMessage(message: String?): Future<*>?

    /**
     * 설정 외 채널에 메시지를 발송합니다.
     *
     *
     * NOTE: 채널에 슬랙봇이 존재해야 합니다.
     *
     *
     * @param channel 채널명
     * @param message 메시지
     *
     * @return 메시지 발송결과
     */
    fun sendMessage(
        channel: String,
        message: String?
    ): Future<*>?
}
