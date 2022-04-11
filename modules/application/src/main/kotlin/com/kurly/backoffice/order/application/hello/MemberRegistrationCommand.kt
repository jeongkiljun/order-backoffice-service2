package com.kurly.backoffice.order.application.hello

import com.kurly.backoffice.order.domain.hello.Member


data class MemberRegistrationCommand(
    val username: String,
    val emailAddress: String,
) {
    fun toDomain(): Member {
        return Member(
            username = username,
            email = emailAddress,
        )
    }
}
