package com.kurly.backoffice.order.application.hello

interface MemberFindUseCase {

    fun findMember(memberId: Long): MemberFindResult

    fun searchMember(searchQuery: MemberSearchQuery): MemberSearchResult
}
