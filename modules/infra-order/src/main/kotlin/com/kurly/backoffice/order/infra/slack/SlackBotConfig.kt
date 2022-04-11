package com.kurly.backoffice.order.infra.slack

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackBotConfig {
    @Value(value = "\${slack.notice.channel}")
    private val defaultChannel: String? = null

    @Value(value = "\${slack.bot.token}")
    private val token: String? = null

    @Bean
    fun slackBot(): SlackBot {
        return SlackBot(
            defaultChannel = defaultChannel!!,
            token = token!!
        )
    }
}
