pluginManagement {
    repositories {
        maven(url = "https://repo.infra.kurlycorp.kr/repository/kurly-gradle/")
        gradlePluginPortal()
    }
}

// 프로젝트의 논리적인 경로를 물리적인 경로와 다르게 설정하기 위함
fun includeProject(name: String, projectPath: String? = null) {
    include(name)
    projectPath?.run {
        project(name).projectDir = File(this)
    }
}

rootProject.name = "order-backoffice-service"
includeProject(":api", "modules/api")
includeProject(":application", "modules/application")
includeProject(":domain-order", "modules/domain-order")
includeProject(":domain-gift", "modules/domain-gift")
includeProject(":infra-order", "modules/infra-order")
includeProject(":infra-gift", "modules/infra-gift")
includeProject(":consumer", "modules/consumer")
