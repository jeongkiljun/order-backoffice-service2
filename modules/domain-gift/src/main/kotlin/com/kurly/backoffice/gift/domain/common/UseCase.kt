package com.kurly.backoffice.gift.domain.common

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class UseCase(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
