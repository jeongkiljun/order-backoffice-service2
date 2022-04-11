package com.kurly.backoffice.order.domain.hello

import javax.persistence.CascadeType.PERSIST
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "point")
class Point(
    point: Long
) {
    @Id
    @GeneratedValue
    @Column(name = "point_id")
    val id: Long = 0

    @Column(name = "point")
    val point = point

    @OneToOne
    @JoinColumn(name = "member_id")
    lateinit var member: Member
        protected set

    @OneToMany(mappedBy = "point", cascade = [PERSIST])
    var pointHistory: MutableList<PointHistory> = mutableListOf()
        protected set

    private fun addPointHistory(history: PointHistory) {
        pointHistory.add(history)
        history.point = this
    }

    constructor(point: Long, member: Member, pointHistory: PointHistory) : this(point) {
        this.member = member
        addPointHistory(pointHistory)
    }

    override fun toString(): String {
        return "Point(id=$id, point=$point)"
    }
}
