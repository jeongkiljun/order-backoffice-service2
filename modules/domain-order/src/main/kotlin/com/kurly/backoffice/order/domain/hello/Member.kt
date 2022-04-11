package com.kurly.backoffice.order.domain.hello

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member(
    username: String,
    email: String,
) {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0L

    @Column(name = "username")
    val username: String = username

    @Column(name = "email")
    val email: String = email

    @Column(name = "member_type")
    @Enumerated(value = STRING)
    var memberType: MemberType = MemberType.GENERAL
        protected set

    constructor(username: String, email: String, memberType: MemberType) : this(username, email) {
        this.memberType = memberType
    }

    override fun toString(): String {
        return "Member(id=$id, username='$username')"
    }
}
