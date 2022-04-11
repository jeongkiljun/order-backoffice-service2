package com.kurly.backoffice.order.infra.database.hello

import com.kurly.backoffice.order.domain.hello.Member
import com.kurly.backoffice.order.domain.hello.MemberCustomRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepositoryImpl : MemberCustomRepository {

    @PersistenceContext(unitName = "order")
    lateinit var em: EntityManager

    override fun findMembersByEmailCustom(email: String): List<Member> {
        return em.createQuery("select m from Member m where m.email = :email", Member::class.java)
            .setParameter("email", email)
            .resultList
        return emptyList()
    }
}
