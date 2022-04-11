package com.kurly.backoffice.order.infra.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.kurly.backoffice.order.infra.feign"])
class FeignConfiguration
