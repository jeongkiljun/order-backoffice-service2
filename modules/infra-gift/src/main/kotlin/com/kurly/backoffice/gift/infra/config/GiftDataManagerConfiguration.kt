package com.kurly.backoffice.gift.infra.config

import org.hibernate.jpa.HibernatePersistenceProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    // DataSource 이용하는 도메인의 Entity 가 위치한 패키지
    basePackages = ["com.kurly.backoffice.gift.domain"],
    entityManagerFactoryRef = "giftEntityManagerFactory",
    transactionManagerRef = "giftTransactionManager",
)
class GiftDataManagerConfiguration {

    @Bean
    fun giftEntityManagerFactory(
        @Qualifier("giftDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.persistenceProvider = HibernatePersistenceProvider()
        factory.persistenceUnitName = "gift"
        // DataSource 이용하는 도메인의 Entity 가 위치한 패키지
        factory.setPackagesToScan("com.kurly.backoffice.gift.domain")

        // TODO: yml 에서 가져와야 하는거 아닌가?
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setShowSql(true)
        vendorAdapter.setGenerateDdl(false)
        factory.jpaVendorAdapter = vendorAdapter
        return factory
    }

    @Bean
    fun giftTransactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }

    @Bean
    fun giftExceptionTranslationPostProcessor(): PersistenceExceptionTranslationPostProcessor =
        PersistenceExceptionTranslationPostProcessor()
}
