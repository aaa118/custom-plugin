package com.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BasePlugin implements Plugin<Project> {

    Utils utils

    @Override
    void apply(Project project) {
//        utils = new Utils(project)

//        project.extensions.create('commonDependencies', CommonDependenciesExtension
//                .CommonDependencies, project)


    }
}