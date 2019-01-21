package com.itquasar.multiverse.proton


import spock.lang.Specification

class AppSpec extends Specification {

    static String projectDir = "/tmp/proton-test-" + UUID.randomUUID()

    def "app have a lifecicle"() {

        given:
        def app = new Application(projectDir)

        expect:
        app.getStatus() == Application.Status.STOPPED
        app.init()
        app.getStatus() == Application.Status.INITIALIZED
        app.start()
        app.getStatus() == Application.Status.STARTED
        app.stop()
        app.getStatus() == Application.Status.STOPPED
    }

    def "app should receive project directory as parameter, or use user.dir if none provided"() {

        given:
        def args = ['/tmp/foo'].toArray(new String[0])
        def appArgs = new ApplicationArgs(args)

        def noArgs = new ApplicationArgs()

        when:
        def dir = appArgs.projectDirectory
        def dirNoArgs = noArgs.projectDirectory

        then:
        dir == new File('/tmp/foo')
        dirNoArgs == new File(System.getProperty("user.dir"))
    }
}
