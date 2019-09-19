plugins {
    java
}

group = "com.newrelic.logging"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile("io.dropwizard:dropwizard-core:1.3.14")
    compile("io.dropwizard:dropwizard-logging:1.3.14")
    compile(project(":dropwizard"))
    compile("com.newrelic.agent.java:newrelic-api:5.6.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes(mapOf("Main-Class" to "com.newrelic.testapps.dropwizard.Main"))
    }
}

val execTask by tasks.register("start", JavaExec::class) {
    dependsOn("jar")
    classpath = sourceSets.main.get().runtimeClasspath
    jvmArgs = listOf(
            "-javaagent:${rootProject.projectDir}/lib/newrelic.jar"
    )
    main = "com.newrelic.testapps.dropwizard.Main"
    args = listOf(
            "server",
            "$projectDir/test.yml"
    )
}