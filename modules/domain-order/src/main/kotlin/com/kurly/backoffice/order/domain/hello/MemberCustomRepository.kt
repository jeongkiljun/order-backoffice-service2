package com.kurly.backoffice.order.domain.hello

interface MemberCustomRepository {

    fun findMembersByEmailCustom(email: String): List<Member>
}
