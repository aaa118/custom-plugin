package com.gradle.plugin

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
            String versionPrefix = "${project.name}"
////            version = new SemanticVersion(project, versionParts[0], versionParts[1], versionParts[2],
//                    isRelease)
//            def version = semanticVersionExt.version

//            final def currentVersionCode = versionsProperties.getProperty("${versionPrefix}_code", 100000)

//            def Properties props = new Properties()
//            def propFile = file('../version.properties')
//
//            def versionCode
//            if (propFile.canRead()){
//                props.load(new FileInputStream(propFile))
//
//                if (props!=null && props.containsKey('setttingsDemo_code')) {
//                    versionCode = props['version.code']
//
//                }
//            }

            final def currentVersionCode = versionsProperties.getProperty("${versionPrefix}_code", "100000")
//            final def currentBuildNumber = props.getProperty("setttingsDemo_build", 1.toString())
//            final def currentBuildNumber = props.getProperty("${versionPrefix}_build", 1.toString())
//            int buildNumber = currentBuildNumber.toInteger()
            int buildNumber = currentVersionCode.toInteger()

//            if (currentVersionCode != version.versionCode.toString()) {
//                buildNumber = 1
//            } else {
//            }

            buildNumber += 1
            String newBuildNumberStr = String.format("%03d", buildNumber)

            versionsProperties.setProperty("${versionPrefix}_code", newBuildNumberStr)
//            versionsProperties.setProperty("${versionPrefix}_code", "${version.versionCode}")
//            versionsProperties.setProperty("${versionPrefix}_build", newBuildNumberStr)

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
