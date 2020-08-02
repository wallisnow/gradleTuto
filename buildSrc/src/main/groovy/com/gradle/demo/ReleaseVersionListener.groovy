package com.gradle.demo

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener

class ReleaseVersionListener implements TaskExecutionGraphListener {

    final static String releaseTaskPath = ':release'
    final static String buildTaskPath = ':build'

    @Override
    void graphPopulated(TaskExecutionGraph taskExecutionGraph) {

        if (taskExecutionGraph.hasTask(buildTaskPath)) {
            println "doing build ... ..."
        }

        if (taskExecutionGraph.hasTask(releaseTaskPath)) {
            List<Task> allTasks = taskExecutionGraph.allTasks
            Task releaseTask = allTasks.find {
                it.path == releaseTaskPath
            }

            Project project = releaseTask.project
            if (!project.ext.myNewTag.release){
                project.ext.myNewTag.release = true
                Properties properties = new Properties()
                project.ext.myTagFile.withInputStream {
                    stream -> properties.load(stream)
                }
                println properties.prefix.toString()

                properties.setProperty('prefix', 'releaseVersionListener-prefix')
                properties.setProperty('postfix', 'releaseVersionListener-postfix')
                properties.setProperty('release', 'true')

                println ".... ....... ..........."
                println properties.prefix.toString()

                //doesn't work
                project.ext.myTagFile.withWriter {
                    properties.store(it, null)
                }
            }
        }
    }
}

