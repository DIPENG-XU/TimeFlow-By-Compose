import com.android.build.api.dsl.ApplicationExtension

plugins {
    base
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
}

project(":app").afterEvaluate {
    val appProject = project(":app")
    val versionName = appProject.extensions
        .getByType(ApplicationExtension::class.java)
        .defaultConfig.versionName ?: "unknown"

    rootProject.tasks.register<Copy>("copyReleaseArtifacts") {
        group = "build"
        description = "Copy release APK and AAB to timeflow/release/"
        dependsOn(":app:assembleTimeflowRelease", ":app:bundleTimeflowRelease")

        from(appProject.layout.buildDirectory.file("outputs/apk/timeflow/release/app-timeflow-release.apk")) {
            rename { "timeflow-$versionName.apk" }
        }
        from(appProject.layout.buildDirectory.file("outputs/bundle/timeflowRelease/app-timeflow-release.aab")) {
            rename { "timeflow-$versionName.aab" }
        }
        into(rootProject.layout.projectDirectory.dir("timeflow/release"))
    }

    rootProject.tasks.register("releasePackage") {
        group = "build"
        description = "Clean, build release APK/AAB, and copy outputs to timeflow/release/"
        dependsOn("clean", "copyReleaseArtifacts")
    }

    rootProject.tasks.named("copyReleaseArtifacts").configure {
        mustRunAfter("clean")
    }
}

gradle.projectsEvaluated {
    val appProject = project(":app")
    listOf("assembleTimeflowRelease", "bundleTimeflowRelease").forEach { taskName ->
        appProject.tasks.named(taskName).configure {
            mustRunAfter(rootProject.tasks.named("clean"))
        }
    }
}