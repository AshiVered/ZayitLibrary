plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.maven.publish)
}

group = "io.github.kdroidfilter.seforimlibrary"

kotlin {
    jvmToolchain(21)
    jvm()

    sourceSets {
        commonMain.dependencies {
        }
        jvmMain.dependencies {
            implementation(libs.lucene.core)
            implementation(libs.lucene.analysis.common)
        }
        commonTest.dependencies { implementation(kotlin("test")) }
    }
}

// Publishable coordinates so the composite build can substitute
mavenPublishing {
    publishToMavenCentral()
    coordinates("io.github.kdroidfilter.seforimlibrary", "analysis", "1.0.0")
    pom {
        name = "SeforimLibrary Analysis"
        description = "Hebrew analyzers and token filters for Lucene"
        url = "https://github.com/kdroidfilter/SeforimApp"
        licenses { license { name = "MIT"; url = "https://opensource.org/licenses/MIT" } }
        developers { developer { id = "kdroidfilter"; name = "KDroidFilter" } }
        scm { url = "https://github.com/kdroidfilter/SeforimApp" }
    }
    if (project.hasProperty("signing.keyId")) signAllPublications()
}

