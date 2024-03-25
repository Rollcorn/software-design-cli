plugins {
    application
}

group = "ru.itmo"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("ru.itmo.Main")
}

tasks.register("buildExecutable", Copy::class) {
    dependsOn("installDist")
    from("build/install/software-design-cli")
    into("executable")
}
