package com.kurly.backoffice.order.api.hello

import com.kurly.backoffice.order.application.hello.MemberRegistrationCommand

data class MemberRegistrationRequest(
    val username: String,
    val email: String,
) {
    fun toCommand(): MemberRegistrationCommand =
        MemberRegistrationCommand(
            username = username,
            emailAddress = email
        )
}
