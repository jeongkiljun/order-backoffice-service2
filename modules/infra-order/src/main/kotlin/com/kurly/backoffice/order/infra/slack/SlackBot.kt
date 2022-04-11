package com.kurly.backoffice.order.infra.slack

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.SlackApiException
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.Future

class SlackBot(val defaultChannel: String, val token: String) {
    private var client: MethodsClient? = null
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        this.client = Slack.getInstance().methods(token)
    }

    fun postMessage(message: String?): Future<*>? {
        return this.postMessage(defaultChannel, message)
    }

    /**
     * 슬랙 메시지 전송.
     */
    fun postMessage(
        channel: String,
        message: String?
    ): Future<*>? {
        return executorService.submit { sendText(channel, message) }
    }

    private fun sendText(channel: String, message: String?) {
        try {
            client!!.chatPostMessage { req -> req.channel(channel).text(message) }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: SlackApiException) {
            e.printStackTrace()
        }
    }
}
