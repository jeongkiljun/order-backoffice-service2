package com.kurly.backoffice.order.domain.hello

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository {

    fun existsByEmail(email: String): Boolean
}
