package com.tairan.hzhyq.gradlelibrary

import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.util.Zip4jConstants
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskState


public class MyPlugin implements Plugin<Project>,TaskExecutionListener {
    def Project rootProject
    @Override
    void apply(Project project) {
        rootProject = project
        project.gradle.addListener(this)
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
            task.project.tasks.create('genChannelPackage'){
                    println '----------------------------generate channel packages-------------------------'
                    def channels = getChannels()
                    channels.each { channel ->
                        createNewPackage(channel)
                    }
                    File file = new File('E:\\androidWorkspace\\GradlePluginDemo\\outputapks\\new')
                    assert file.exists()
                    file.deleteDir()
                    println '----------------------------generate end-------------------------------'
            }
            println '---------------------------end-----------------------'
        }
    }
    def createNewPackage(name) {
        println "channel name : $name"
        def outputPathm = 'E:\\androidWorkspace\\GradlePluginDemo\\outputapks'
        try {
            println 'zip start'
            FileTree tree = rootProject.fileTree("$outputPathm\\new")
            if (!tree.dir.exists()) {
                ZipFile zipFile = new ZipFile("E:\\androidWorkspace\\GradlePluginDemo\\outputapks\\app-debug.apk")
                zipFile.extractAll("$outputPathm\\new")
            }
            File parent = tree.dir.listFiles(new FileFilter() {
                @Override
                boolean accept(File pathname) {
                    return pathname.name == "META-INF"
                }
            }).first()
            def parentPath = parent.getAbsolutePath()
            File file = new File("$parentPath\\pwchannel-$name")
            file.setText(file.name)
            ZipFile zipFile1 = new ZipFile("E:\\androidWorkspace\\GradlePluginDemo\\outputapks\\app-channel_$name-release.apk")
            ZipParameters zipParameters = new ZipParameters()
            zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE)
            zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL)
            tree.dir.listFiles().each { ele ->
                if (ele.isDirectory()) {
                    zipFile1.addFolder(ele, zipParameters)
                } else {
                    zipFile1.addFile(ele, zipParameters)
                }
            }
            file.delete()
            println 'zip end '
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def getChannels() {
        def pro = new File('channels.properties')
        def properties = new Properties()
        pro.withInputStream { stream ->
            properties.load(stream)
        }
        return properties.values()
    }
}

