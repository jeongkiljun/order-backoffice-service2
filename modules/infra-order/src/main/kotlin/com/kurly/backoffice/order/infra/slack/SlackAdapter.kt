package com.kurly.backoffice.order.infra.slack

import com.kurly.backoffice.order.domain.message.SlackPort
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.concurrent.Future

@Primary
@Component
class SlackAdapter(private val slackBot: SlackBot) : SlackPort {

    /**
     * 기본 채널로 메시지를 발송합니다.
     *
     * @param message 전달하는 메시지
     *
     * @return 발송결과
     */
    override fun sendMessage(message: String?): Future<*>? {
        return slackBot.postMessage(message)
    }

    /**
     * 주어진 채널로 메시지를 발송합니다.
     *
     * NOTE: 채널에 봇이 초대되어 있어야합니다.
     *
     * @param channel 채널명
     * @param message 전달하는 메시지
     *
     * @return 발송결과
     */
    override fun sendMessage(channel: String, message: String?): Future<*>? {
        return slackBot.postMessage(channel, message)
    }
}
