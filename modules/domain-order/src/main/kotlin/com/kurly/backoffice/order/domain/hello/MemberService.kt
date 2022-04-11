package com.kurly.backoffice.order.domain.hello

import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository
) {

    fun existsEmail(member: Member): Boolean {
        return memberRepository.existsByEmail(member.email)
    }

    fun existsEmail(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }
}
