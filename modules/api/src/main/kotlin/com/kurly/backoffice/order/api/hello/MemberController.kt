package com.kurly.backoffice.order.api.hello

import com.kurly.backoffice.order.application.hello.MemberFindResult
import com.kurly.backoffice.order.application.hello.MemberFindUseCase
import com.kurly.backoffice.order.application.hello.MemberRegistrationUseCase
import com.kurly.backoffice.order.application.hello.MemberSearchQuery
import com.kurly.backoffice.order.application.hello.MemberSearchResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    val memberRegistrationUseCase: MemberRegistrationUseCase,
    val memberFindUseCase: MemberFindUseCase,
) {

    @PostMapping("/api/v1/member")
    fun registerMember(
        @RequestBody request: MemberRegistrationRequest
    ): String {
        memberRegistrationUseCase.registerMember(request.toCommand())
        return "ok"
    }

    @PostMapping("/api/v1/admin")
    fun registerAdmin(
        @RequestBody request: MemberRegistrationRequest
    ): String {
        memberRegistrationUseCase.registerAdmin(request.toCommand())
        return "ok"
    }

    @GetMapping("/api/v1/members/{memberId}")
    fun findMember(@PathVariable("memberId") memberId: Long): MemberFindResult {
        return memberFindUseCase.findMember(memberId)
    }

    @GetMapping("/api/v1/members")
    fun searchMembers(@RequestParam("email") email: String): MemberSearchResult {
        val searchQuery = MemberSearchQuery(email)
        return memberFindUseCase.searchMember(searchQuery)
    }
}
