package com.kurly.backoffice.order.domain.hello

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "point_history")
class PointHistory(
    appliedPoint: Long,
    applied_type: PointApplyType,
) {

    @Id
    @GeneratedValue
    @Column(name = "point_history_id")
    val id: Long = 0

    @Column(name = "applied_point")
    val appliedPoint = appliedPoint

    @Column(name = "applied_type")
    @Enumerated(STRING)
    val applied_type = applied_type

    @Column(name = "applied_date")
    val applied_date: LocalDateTime = LocalDateTime.now()

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "point_id")
    lateinit var point: Point
}