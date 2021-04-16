package com.gradle.plugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin implements Plugin<Project> {
    private static final def VERSIONS_FILENAME = 'version.properties'

    @Override
    void apply(Project project) {
        configureAndroidBase(project)

        project.task('hello') {
            doLast {
                println 'Hello'
            }
        }

        project.afterEvaluate {
            final File versionsFile = getVersionsPropertiesFile(project)

            if (!versionsFile.exists()) {
                versionsFile.createNewFile()
            }

            Properties versionsProperties = loadProperties(versionsFile)
//
//            version = new SemanticVersion(project, versionParts[0], versionParts[1], versionParts[2],
//                    isRelease)

            String versionPrefix = "${project.name}"
            final def currentVersionCode = versionsProperties.getProperty("${versionPrefix}_code", "100000")
            final def currentBuildNumber = versionsProperties.getProperty("${versionPrefix}_build", 1.toString())
            int buildNumber = currentBuildNumber.toInteger()
            int major = currentVersionCode.toInteger() / 100000
            major += 1
            int version = major * 100000



            buildNumber += 1
            String newBuildNumberStr = String.format("%03d", buildNumber)
            String newVersionNumberStr = String.format("%03d", version)

            versionsProperties.setProperty("${versionPrefix}_code", newVersionNumberStr)
//            versionsProperties.setProperty("${versionPrefix}_code", "${version.versionCode}")
            versionsProperties.setProperty("${versionPrefix}_build", newBuildNumberStr)

            versionsProperties.store(versionsFile.newWriter(), null)
        }
    }


    static File getVersionsPropertiesFile(Project project) {
        return project.rootProject.file(VERSIONS_FILENAME)
    }

    static Properties loadProperties(File file) {
        Properties properties = new Properties()
        properties.load(file.newDataInputStream())

        return properties
    }


    private def getVersionParts(int[] parts) {
        if (parts == null || parts.length == 0) {
            throw new GradleException("Must provide at least one version to semantic version")
        }

        def major = parts[0]
        def minor = parts[1] ?: 0
        def patch = parts[2] ?: 0

        return [major, minor, patch]
    }

    private void configureAndroidBase(Project project) {
        project.android {
            compileSdkVersion 30
            buildToolsVersion "30.0.3"

            defaultConfig {
                minSdkVersion 23
                targetSdkVersion 30
            }
        }
    }
}
