package com.itquasr.multiverse.proton

import spock.lang.Specification

class AppSpec extends Specification {

    def 'app have a lifecicle'(){
        def app = new Application()

        when:
        app.isRunning() == false
        app.start()

        then:
        app.isRunning() == true
        app.stop()
        app.isRunning() == false
    }
}
