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

    def "create config if not exists in the project directory"() {

        given:
        def projectDir = Paths.get(Utils.TMP_DIR, UUID.randomUUID().toString())
        def file = Config.configFilePath(projectDir).toFile()

        when:
        Config.createIfNotExists(projectDir)

        then:
        file.exists() == true
        file.readLines().size() > 0
    }

    def "save custom config"(){

        given:
        def projectDir = Paths.get(Utils.TMP_DIR, UUID.randomUUID().toString())
        def conf = new Config()
        conf.editor.port = 8080

        when:
        Config.save(conf, Config.configFilePath(projectDir))

        then:
        conf.editor.port == Config.load(projectDir).editor.port
    }
}