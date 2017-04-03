package com.tairan.hzhyq.gradlelibrary

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import org.gradle.execution.TaskGraphExecuter

public class MyPlugin implements Plugin<Project>,TaskExecutionListener {
    @Override
    void apply(Project project) {
        project.extensions.create('pluginExtension', PluginExtension)

        project.extensions.create('custorm',CustormExtension)
        project.task('myTask',type: MyTask)

    }

    @Override
    void beforeExecute(Task task) {
        if(task.name == 'assembleRelease'){
            println "${task.name} start to execute"
        }
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        if(task.name == 'assembleRelease'){
            println "${task.name} end"
        }
    }
}
class CustormExtension{
    def one ="one"
    def two = "two"
}