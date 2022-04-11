package com.kurly.backoffice.order.application.hello

interface MemberRegistrationUseCase {

    fun registerMember(command: MemberRegistrationCommand)

    fun registerAdmin(command: MemberRegistrationCommand)
}
