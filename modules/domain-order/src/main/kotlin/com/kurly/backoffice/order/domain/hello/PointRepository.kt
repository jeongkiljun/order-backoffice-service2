package com.kurly.backoffice.order.domain.hello

import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long>
