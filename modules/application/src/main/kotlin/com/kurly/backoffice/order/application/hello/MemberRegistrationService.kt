package com.kurly.backoffice.order.application.hello

import com.kurly.backoffice.order.domain.common.UseCase
import com.kurly.backoffice.order.domain.hello.Member
import com.kurly.backoffice.order.domain.hello.MemberRepository
import com.kurly.backoffice.order.domain.hello.MemberService
import com.kurly.backoffice.order.domain.hello.MemberType.ADMIN
import com.kurly.backoffice.order.domain.hello.Point
import com.kurly.backoffice.order.domain.hello.PointApplyType.WELCOME
import com.kurly.backoffice.order.domain.hello.PointHistory
import com.kurly.backoffice.order.domain.hello.PointRepository
import com.kurly.backoffice.order.domain.message.SlackPort
import com.kurly.backoffice.order.infra.feign.orderproductagent.OrderProductAgentClient
import com.kurly.backoffice.order.infra.feign.orderproductagent.model.PartnerDealProductsRequestV1
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@UseCase
class MemberRegistrationService(
    val memberService: MemberService,
    val memberRepository: MemberRepository,
    val pointRepository: PointRepository,
    val slackPort: SlackPort,
    val orderProductAgentClient: OrderProductAgentClient,
) : MemberRegistrationUseCase {

    /**
     * 신규 회원 가입
     *  - 이메일 중복 검사
     *  - 신규회원 가입 포인트 지급
     *  - 슬랙 메시지 발송
     *  - 쓸데없이 feign을 통해서 상품번호 조회
     */
    @Transactional
    override fun registerMember(command: MemberRegistrationCommand) {
        val member = command.toDomain()
        if (memberService.existsEmail(member)) {
            throw RuntimeException("이미 사용중인 이메일 입니다.")
        }
        val savedMember = memberRepository.save(member)
        applyWelcomePoint(savedMember.id)
        slackPort.sendMessage("백오피스 테스트 메시지 (by 깆쥰)")
        feignSample()
    }

    // TODO: BusinessException 던져서 500 에러로 처리 해야 함
    private fun applyWelcomePoint(memberId: Long) {
        val member = memberRepository.findById(memberId)
            .orElseThrow { throw RuntimeException("존재하지 않는 회원입니다.") }

        val applyPoint = 100L
        val point = Point(
            applyPoint,
            member,
            PointHistory(applyPoint, WELCOME)
        )
        pointRepository.save(point)
    }

    /**
     * TODO: Feign 테스트를 위해서 임시로 추가해본 기능 입니다.
     */
    private fun feignSample() {
        val request = PartnerDealProductsRequestV1(
            memberNo = null,
            dealProducts = listOf(
                PartnerDealProductsRequestV1.DealProductCodeAndQuantityRequest(
                    dealProductNo = 10007760L,
                    quantity = 1
                )
            ),
            address = null
        )
        val result = orderProductAgentClient.getDiscountDealProducts(request)
        println("##############")
        println(result)
        println("##############")
    }

    /**
     * 관리자 회원 가입
     */
    @Transactional
    override fun registerAdmin(command: MemberRegistrationCommand) {
        if (memberService.existsEmail(command.emailAddress)) {
            throw RuntimeException("이미 사용중인 이메일 입니다.")
        }
        memberRepository.save(
            Member(
                username = command.username,
                email = command.emailAddress,
                memberType = ADMIN,
            )
        )
    }
}
