package com.tairan.hzhyq.gradlelibrary

import org.gradle.api.Project
import org.gradle.api.Plugin

public class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('pluginExtension', PluginExtension)

        project.extensions.create('custorm',CustormExtension)
        project.task('myTask',type: MyTask)
    }
}
class CustormExtension{
    def one ="one"
    def two = "two"
}