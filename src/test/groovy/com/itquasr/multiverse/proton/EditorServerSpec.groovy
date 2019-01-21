package com.itquasr.multiverse.proton

import spock.lang.Specification


class EditorServerSpec extends Specification {

    def "start/serve from classpath/stop"() {
        given:
        def server = new EditorServer(null)

        when:
        server.start()

        then:
        "proton" == new URL("http://localhost:7000/ping-pong").getText()

        cleanup:
        server.stop()
    }

}