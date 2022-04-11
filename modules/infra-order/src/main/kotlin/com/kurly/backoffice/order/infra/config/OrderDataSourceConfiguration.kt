package com.kurly.backoffice.order.infra.config

import com.kurly.backoffice.order.infra.config.OrderDataSourceConfiguration.DataSourceType.READER
import com.kurly.backoffice.order.infra.config.OrderDataSourceConfiguration.DataSourceType.WRITER
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class OrderDataSourceConfiguration {

    enum class DataSourceType {
        WRITER,
        READER
    }

    internal class RoutingDataSource : AbstractRoutingDataSource() {
        override fun determineCurrentLookupKey(): Any = when {
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> READER
            else -> WRITER
        }
    }

    @Bean(name = ["writerDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.order.writer")
    fun writerDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = ["readerDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.order.reader")
    fun readerDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()
        .apply { isReadOnly = true }

    @Bean(name = ["routingDataSource"])
    @ConditionalOnBean(name = ["writerDataSource", "readerDataSource"])
    fun routingDataSource(
        @Qualifier("writerDataSource") writerDataSource: DataSource,
        @Qualifier("readerDataSource") readerDataSource: DataSource,
    ): DataSource {
//        val routingDataSource = RoutingDataSource()
        val routingDataSource = RoutingDataSource()
        val dataSources: Map<Any, Any> = mapOf(WRITER to writerDataSource, READER to readerDataSource)
        routingDataSource.setTargetDataSources(dataSources)
        routingDataSource.setDefaultTargetDataSource(writerDataSource)
        return routingDataSource
    }

    @Primary
    @Bean(name = ["orderDataSource"])
    @ConditionalOnBean(name = ["routingDataSource"])
    fun orderDataSource(routingDataSource: DataSource) = LazyConnectionDataSourceProxy(routingDataSource)
}
