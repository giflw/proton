package com.itquasr.multiverse.proton

import spock.lang.Specification

import java.nio.file.Path

class ProjectSpec extends Specification {

    def "init project"() {

        given:
        def project = new Project(
                Path.of(
                        Utils.TMP_DIR + "/" + UUID.randomUUID()
                )
        )

        when:
        project.init()

        then:
        Project.Folder.API.getPath(project.getProjectDir()).toFile().exists() == true
        Project.Folder.ASSETS.getPath(project.getProjectDir()).toFile().exists() == true
        Project.Folder.PREVIEW.getPath(project.getProjectDir()).toFile().exists() == true
        Project.Folder.SRC.getPath(project.getProjectDir()).toFile().exists() == true
        Project.Folder.TEMPLATES.getPath(project.getProjectDir()).toFile().exists() == true
    }
}
