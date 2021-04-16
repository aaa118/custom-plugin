package com.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
//        def showDevicesTask = target.tasks.create("showDevices") << {
//            def adbExe = target.android.getAdbExe().toString()
//            println "${adbExe} devices".execute().text
//        }
//        showDevicesTask.group = "blogplugin"
//        showDevicesTask.description = "Runs adb devices command"
        project.task('hello') {
            doLast {
                println 'Hello from the GreetingPlugin'
            }
        }
    }
}
//apply plugin: CustomPlugin

// Apply the plugin
//apply plugin: 'custom'
