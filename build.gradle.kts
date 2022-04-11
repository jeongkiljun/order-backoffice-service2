import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"

    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jmailen.kotlinter") version "3.9.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("org.springframework.boot") version "2.3.12.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
//    id("com.gorylenko.gradle-git-properties") version "2.3.1"
}

// gitProperties {
//    dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
//    dateFormatTimeZone = "Asia/Seoul"
// }

object Version {
    const val kurlyApiBase = "3.7.0"
    const val kotest = "4.4.3"
    const val kotlinLoggingJvmVersion = "2.1.21"
}

// 하위 모듈에 공통으로 적용할 값들을 설정
allprojects {
    repositories {
        mavenLocal()
        maven(url = "https://repo.infra.kurlycorp.kr/repository/kurly-maven/")
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> { useJUnitPlatform() }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    group = "com.kurly.backoffice"
    version = "1.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenLocal()
        maven(url = "https://repo.infra.kurlycorp.kr/repository/kurly-maven/")
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("io.github.microutils:kotlin-logging-jvm:${Version.kotlinLoggingJvmVersion}")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        kapt("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("io.kotest:kotest-runner-junit5:${Version.kotest}")
        testImplementation("io.kotest:kotest-assertions-core:${Version.kotest}")
        testImplementation("io.kotest:kotest-extensions-spring-jvm:${Version.kotest}")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-starter-parent:Hoxton.SR12")
        }
    }
}

project(":api") {
    apply(plugin = "org.asciidoctor.jvm.convert")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = true
    jar.enabled = false

    springBoot {
        buildInfo()
    }

    val asciidoctorExt: Configuration by configurations.creating

    dependencies {
        asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
        implementation(project(":application"))
        implementation("com.kurly.cloud:api-base:${Version.kurlyApiBase}") {
            exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-openfeign")
            exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-zipkin")
            exclude(group = "org.springframework.kafka", module = "spring-kafka")
        }
    }

    val snippetsDir by extra { file("$buildDir/generated-snippets") }

    tasks {
        asciidoctor {
            configurations(asciidoctorExt.name)
            dependsOn(test)
            inputs.dir(snippetsDir)
            sources {
                include("**/index.adoc")
            }
            attributes.put("project-version", project.version)
            attributes.put("source-highlighter", "prettify")
        }

        bootJar {
            dependsOn(asciidoctor)
            from("${asciidoctor.get().outputDir}") {
                into("static/docs")
            }

            mainClassName = "com.kurly.backoffice.Application"
        }
    }
}

project(":application") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":domain-order"))
        implementation(project(":domain-gift"))
        implementation(project(":infra-order"))
        implementation(project(":infra-gift"))
        implementation("javax.transaction:javax.transaction-api")
    }
}

project(":domain-order") {
    apply(plugin = "kotlin-jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        testRuntimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("javax.persistence.Entity")
    }
}

project(":domain-gift") {
    apply(plugin = "kotlin-jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        testRuntimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("javax.persistence.Entity")
    }
}

project(":infra-order") {
    apply(plugin = "kotlin-jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":domain-order"))
        api("com.kurly.cloud:api-base-domain:${Version.kurlyApiBase}")
        api("com.kurly.cloud:api-base-util:${Version.kurlyApiBase}")
        api("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.slack.api:slack-api-client:1.16.0")
        api("org.springframework.cloud:spring-cloud-starter-openfeign")
        runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
        runtimeOnly("mysql:mysql-connector-java")
    }
}

project(":infra-gift") {
    apply(plugin = "kotlin-jpa")

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    bootJar.enabled = false
    jar.enabled = true

    dependencies {
        implementation(project(":domain-gift"))
        api("com.kurly.cloud:api-base-domain:${Version.kurlyApiBase}")
        api("com.kurly.cloud:api-base-util:${Version.kurlyApiBase}")
        api("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.slack.api:slack-api-client:1.16.0")
        api("org.springframework.cloud:spring-cloud-starter-openfeign")
        runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

        // TODO 테스트를 위해서 적용 함
        runtimeOnly("com.h2database:h2")
    }
}
