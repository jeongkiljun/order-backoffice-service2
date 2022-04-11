package com.kurly.backoffice.order.application.hello

import com.kurly.backoffice.order.domain.hello.Member

data class MemberSearchResult(
    val totalCount: Int,
    val members: List<MemberDto> = mutableListOf()
) {

    companion object {
        fun of(members: List<Member>): MemberSearchResult {
            return MemberSearchResult(
                totalCount = members.size,
                members = members.map(MemberDto.Companion::of)
            )
        }
    }

    data class MemberDto(
        val memberId: Long,
        val username: String,
        val email: String,
    ) {
        companion object {
            fun of(member: Member): MemberDto {
                return MemberDto(
                    memberId = member.id,
                    username = member.username,
                    email = member.email,
                )
            }
        }
    }
}
