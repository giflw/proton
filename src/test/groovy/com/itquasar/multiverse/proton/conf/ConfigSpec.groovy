package com.itquasar.multiverse.proton.conf

import com.itquasar.multiverse.proton.Utils
import spock.lang.Specification

import java.nio.file.Paths

class ConfigSpec extends Specification {

    def "load default config if no file config located"() {

        given:
        def projectDir = Paths.get(Utils.TMP_DIR)

        when:
        def config = Config.load(projectDir)

        then:
        config.editor.port == 7001
        config.preview.port == 7002
    }


    def "load default if no config string given"() {

        when:
        def config = Config.load("")

        then:
        config.editor.port == 7001
        config.preview.port == 7002
    }

    def "parse yaml"() {

        given:
        def yaml = "editor: {port: 4567}\npreview: {port: 5678}\n"

        when:
        def config = Config.load(yaml)

        then:
        config.editor.port == 4567
        config.preview.port == 5678
    }

}