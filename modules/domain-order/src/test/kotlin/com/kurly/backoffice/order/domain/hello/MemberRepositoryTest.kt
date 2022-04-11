package com.kurly.backoffice.order.domain.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("회원 등록")
    fun saveMember() {
        val member = Member("memberA", "test@kurly.com")
        val savedMember = memberRepository.save(member)

        val foundMember = memberRepository.findById(savedMember.id)
            .orElseThrow(::RuntimeException)
        assertThat(savedMember.id).isEqualTo(foundMember.id)
    }
}
