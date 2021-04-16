package com.gradle.plugin

import org.gradle.api.Project

class CommonDependenciesExtension {
    private static final def supportVersion = "1.0.0"
    public final def constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"
    public final def appcompat = "androidx.appcompat:appcompat:1.2.0"
    public final def junit = "junit:junit:4.13"


    static class CommonDependencies {
        final def constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"
        private final def utils

        CommonDependencies(Project project) {
            this.project = project
//            this.utils = new Utils(project)
        }
    }
}
