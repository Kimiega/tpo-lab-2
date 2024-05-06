plugins {
    id("java")
}

group = "com.kimiega"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:2.22.0")
    implementation("org.apache.commons:commons-csv:1.10.0")

}

tasks.test {
    useJUnitPlatform()
}