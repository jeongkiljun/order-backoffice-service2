package com.kurly.backoffice.order.application.hello

import com.kurly.backoffice.order.domain.hello.Member

class MemberFindResult(
    val memberId: Long,
    val username: String,
    val email: String,
) {
    companion object {
        fun of(member: Member): MemberFindResult {
            return MemberFindResult(
                memberId = member.id,
                username = member.username,
                email = member.email,
            )
        }
    }
}
