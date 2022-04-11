package com.kurly.backoffice.gift.infra.config

import com.kurly.backoffice.gift.infra.config.GiftDataSourceConfiguration.DataSourceType.READER
import com.kurly.backoffice.gift.infra.config.GiftDataSourceConfiguration.DataSourceType.WRITER
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
class GiftDataSourceConfiguration {

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

    @Bean(name = ["writerGiftDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.gift.writer")
    fun writerGiftDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = ["readerGiftDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.gift.reader")
    fun readerGiftDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()
        .apply { isReadOnly = true }

    @Bean(name = ["routingGiftDataSource"])
    @ConditionalOnBean(name = ["writerGiftDataSource", "readerGiftDataSource"])
    fun routingDataSource(
        @Qualifier("writerGiftDataSource") writerGiftDataSource: DataSource,
        @Qualifier("readerGiftDataSource") readerGiftDataSource: DataSource,
    ): DataSource {
        val routingDataSource = RoutingDataSource()
        val dataSources: Map<Any, Any> = mapOf(WRITER to writerGiftDataSource, READER to readerGiftDataSource)
        routingDataSource.setTargetDataSources(dataSources)
        routingDataSource.setDefaultTargetDataSource(writerGiftDataSource)
        return routingDataSource
    }

    @Bean(name = ["giftDataSource"])
    @ConditionalOnBean(name = ["routingGiftDataSource"])
    fun giftDataSource(routingGiftDataSource: DataSource) = LazyConnectionDataSourceProxy(routingGiftDataSource)
}
