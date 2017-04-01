package com.tairan.hzhyq.gradlelibrary

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class MyTask extends DefaultTask {

    @TaskAction
    void output() {
        println "one is ${project.custorm.one} ,two is ${project.custorm.two}"
        println "name is ${project.pluginExtension.name}, value is ${project.pluginExtension.value}"
        println project.pluginExtension.name
    }
}