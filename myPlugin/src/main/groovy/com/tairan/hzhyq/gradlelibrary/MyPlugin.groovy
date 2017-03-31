package com.tairan.hzhyq.gradlelibrary

import org.gradle.api.Project
import org.gradle.api.Plugin

class MyPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
            project.task("showInfo"){
                println "showInfo task is executed !"
            }
    }
}