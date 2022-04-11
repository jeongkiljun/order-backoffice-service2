package com.kurly.backoffice.order.domain.hello

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("local")
@SpringBootTest
@Rollback(false)
class PointRepositoryTest {

    @Autowired
    lateinit var pointRepository: PointRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("포인트 적립")
    fun name() {
        val savedMember = memberRepository.save(Member("kil", "test@example.com"))

        val pointHistory = PointHistory(100, PointApplyType.WELCOME)
        val point = Point(1000, savedMember, pointHistory)
        val savedPoint = pointRepository.save(point)

        val foundPoint = pointRepository.findById(savedPoint.id).get()
        println("foundPoint = $foundPoint")
    }
}
