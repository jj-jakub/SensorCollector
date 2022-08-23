plugins {
    kotlin("jvm")
}

kotlin {
    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}