package com.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        configureAndroidBase(project)

        project.task('hello') {
            doLast {
                println 'Hello'
            }
        }
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
