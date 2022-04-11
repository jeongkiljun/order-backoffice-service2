package com.kurly.backoffice.order.application.hello

import com.kurly.backoffice.gift.domain.common.UseCase
import com.kurly.backoffice.order.domain.hello.MemberRepository
import org.springframework.transaction.annotation.Transactional

@UseCase
class MemberFindService(
    val memberRepository: MemberRepository
) : MemberFindUseCase {

    @Transactional(readOnly = true)
    override fun findMember(memberId: Long): MemberFindResult {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberEntityNotFoundException("존재하지 않는 회원입니다. {memberId=$memberId") }
        return MemberFindResult.of(member)
    }

    @Transactional(readOnly = true)
    override fun searchMember(searchQuery: MemberSearchQuery): MemberSearchResult {
        val members = memberRepository.findMembersByEmailCustom(searchQuery.email)
        return MemberSearchResult.of(members)
    }
}
