plugins {
    java
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "application")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        implementation("org.projectlombok:lombok:1.18.22")
        annotationProcessor("org.projectlombok:lombok:1.18.22")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
